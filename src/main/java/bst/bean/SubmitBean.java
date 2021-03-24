package bst.bean;

public class SubmitBean{
    private long submitId;
    private int resCode; // 0:normal  -1:exception
    private String errMsg = "";
    private boolean needUpdate;
    private String downloadUrl;
    private int nowVerCode = -1;
    private String nowVerName = "-1";

    public long getSubmitId() {
        return submitId;
    }

    public void setSubmitId(long submitId) {
        this.submitId = submitId;
    }



    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getNowVerCode() {
        return nowVerCode;
    }

    public void setNowVerCode(int nowVerCode) {
        this.nowVerCode = nowVerCode;
    }

    public String getNowVerName() {
        return nowVerName;
    }

    public void setNowVerName(String nowVerName) {
        this.nowVerName = nowVerName;
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
