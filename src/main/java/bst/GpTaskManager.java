package bst;

import bst.bean.AppTaskBean;
import bst.utils.FileUtil;
import bst.utils.GsonUtil;
import bst.utils.HttpRequestUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GpTaskManager {
    private static final String USER_DIR = System.getProperty("user.dir");
    private static final String TASK_FILE_PATH = USER_DIR+"/"+"task.json";
    private static final String TASK_SUBMIT_FILE_PATH = USER_DIR+"/"+"submit.json";

    private static final String GET_TASK_URL = "http://192.168.31.184:8000/getTask";
    private static final String SUBMIT_TASK_URL = "http://192.168.31.184:8000/taskSubmit";;

    public GpTaskManager(){

    }

    public static void main(String[] args) {
        System.out.println("user.dir=> "+USER_DIR);
        System.out.println("test");
    }

    private GpTaskBean getTaskFromFile(){
        String taskJson = FileUtil.readFileAll(TASK_FILE_PATH);
        GpTaskBean gpTaskBean = GsonUtil.GsonToBean(taskJson,GpTaskBean.class);
        //System.out.println(gpTaskBean.getAppTaskList().size());
        return gpTaskBean;
    }

    public void submitTaskToFile(GpTaskBean gpTaskBean){
        String prettyJson = GsonUtil.prettyJson(GsonUtil.GsonString(gpTaskBean));
        System.out.println(prettyJson);
        FileUtil.saveFileAs(prettyJson,TASK_SUBMIT_FILE_PATH);
    }

    public GpTaskBean getTask(){
        GpTaskBean gpTaskBean = null;
        try {
            String resStr = HttpRequestUtil.sendGet(GET_TASK_URL,"limit=5");
            gpTaskBean = GsonUtil.GsonToBean(resStr,GpTaskBean.class);
        } catch (IOException e) {
            e.printStackTrace();;
        }
        return gpTaskBean;
    }

    public boolean submitTask(GpTaskBean gpTaskBean){
        submitTaskToFile(gpTaskBean);
        String postJson = GsonUtil.GsonString(gpTaskBean);
        boolean bRet = false;
        try {
            String strRes = HttpRequestUtil.sendPost(SUBMIT_TASK_URL,postJson);
            bRet = true;
            System.out.println("submitTask response=>"+strRes);
        } catch (IOException e) {
            e.printStackTrace();;
        }
        return bRet;
    }


    private static GpTaskBean createGpTaskBean(){
//        GpTaskBean testBean = createGpTaskBean();
//        String prettyJson = GsonUtil.prettyJson(GsonUtil.GsonString(testBean));
//        FileUtil.saveFileAs(prettyJson,TASK_FILE_PATH);

        List<String> pkgNameList = new ArrayList<>();
        pkgNameList.add("com.google.android.gms");
        pkgNameList.add("com.bilibili.fatego"); //404
        pkgNameList.add("com.aniplex.fategrandorder.en");
        pkgNameList.add("studio.gameberry.murimrpg"); //korea
        pkgNameList.add("com.elysium.kingdom.hero.manga.impact.mmorpg.rpg.fun.game");
        pkgNameList.add("com.facebook.orca");
        pkgNameList.add("com.supercell.brawlstars");
        pkgNameList.add("com.pearlabyss.blackdesertm.gl");

        List<AppTaskBean> appTaskList = new ArrayList<AppTaskBean>();
        for(int i = 0 ;i <pkgNameList.size();i++){
            AppTaskBean appTaskBean = new AppTaskBean(i+1,pkgNameList.get(i),-1,"-1");
            appTaskList.add(appTaskBean);
        }

        GpTaskBean gpTaskBean = new GpTaskBean();
        gpTaskBean.setAppTaskList(appTaskList);
        return gpTaskBean;
    }
}
