package bst;

import bst.bean.AppTaskBean;
import bst.bean.SubmitBean;
import bst.utils.ToolUtils;
import com.akdeniz.googleplaycrawler.DownloadData;
import com.akdeniz.googleplaycrawler.GooglePlayException;
import de.onyxbits.raccoon.Main;
import de.onyxbits.raccoon.cli.Play;


public class BstTestMain {
    public static void main(String[] args){
        System.out.println("user.dir=>"+System.getProperty("user.dir"));
        if(false == Play.bstCheckProfile()){
            System.err.println("PlayProfile doesn't exist, please login first...");
            try {
                Main.main(args); //start gui
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(false == Play.bstCheckProfile()){
                System.err.println("login failed,exit now...");
                return;
            }
        }


        GpTaskManager gpTaskManager = new GpTaskManager();
        while (true){
            System.out.println("begin get task ....");
            GpTaskBean gpTaskBean = gpTaskManager.getTask();
            if(gpTaskBean == null){
                System.err.println("get task return null,failed ....");
                ToolUtils.sleep(5*1000);
                continue;
            }
            for (AppTaskBean appTaskBean: gpTaskBean.getAppTaskList()){
                long taskId = appTaskBean.getTaskId();
                String packageName = appTaskBean.getPackageName();
                int localVerCode = appTaskBean.getVerCode();
                String localVer = appTaskBean.getVersionName();
                System.out.println("\n\n"+taskId+"=>"+packageName+" "+localVerCode+" "+localVer);

                //create a SubmitBean for submit
                SubmitBean submitBean = new SubmitBean();
                submitBean.setSubmitId(taskId);
                submitBean.setResCode(0);

                try {
                    String[] appVerInfoArray = Play.bstGetAppVerInfo(packageName);
                    if(appVerInfoArray != null && appVerInfoArray.length == 2){
                        submitBean.setNowVerCode(Integer.parseInt(appVerInfoArray[0]));
                        submitBean.setNowVerName(appVerInfoArray[1]);
                    }
                    boolean isNeedUpdate = Play.bstCheckApkInfo(localVer, localVerCode, appVerInfoArray);
                    if (isNeedUpdate) {
                        System.out.println(packageName + " need update,begin update...");
                        DownloadData data = Play.bstGetDownloadUrl(packageName, -1, 1);
                        if (data != null) {
                            System.out.println("downloadUrl=> " + data.getDownloadUrl());
                            submitBean.setNeedUpdate(true);
                            submitBean.setDownloadUrl(data.getDownloadUrl());
                        }
                    }
                }catch (GooglePlayException e){
                    String errMsg = "has a GooglePlayException =>"+e.getHttpStatus()+"\n"+ e.getMessage();
                    submitBean.setResCode(-1);
                    submitBean.setErrMsg(errMsg);
                    System.err.println(errMsg);
                    e.printStackTrace();
                } catch (Exception e) {
                    String errMsg = "has a Exception =>"+ e.getMessage();
                    submitBean.setResCode(-1);
                    submitBean.setErrMsg(errMsg);
                    System.err.println(errMsg);
                }

                appTaskBean.setSubmitBean(submitBean);
                ToolUtils.sleep(3*1000);
            }
            boolean bOk = gpTaskManager.submitTask(gpTaskBean);
            System.out.println("submitTask response=>"+bOk+", wait a minute for getting a new task...");
            ToolUtils.sleep(60*1000);
        }
    }
}
