package bst.bean;

public class AppTaskBean {
    private long mTaskId;
    private String mPackageName;
    private int mVerCode;
    private String mVersionName;
    private SubmitBean mSubmitBean;

    public AppTaskBean(long mTaskId, String mPackageName, int mVerCode, String mVersionName) {
        this.mTaskId = mTaskId;
        this.mPackageName = mPackageName;
        this.mVerCode = mVerCode;
        this.mVersionName = mVersionName;
    }

    public long getTaskId() {
        return mTaskId;
    }

    public void setTaskId(long taskId) {
        this.mTaskId = taskId;
    }

    public String getPackageName() {
        return mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public int getVerCode() {
        return mVerCode;
    }

    public void setVerCode(int verCode) {
        this.mVerCode = verCode;
    }

    public String getVersionName() {
        return mVersionName;
    }

    public void setVersionName(String versionName) {
        this.mVersionName = versionName;
    }

    public SubmitBean getSubmitBean() {
        return mSubmitBean;
    }

    public void setSubmitBean(SubmitBean submitBean) {
        this.mSubmitBean = submitBean;
    }
}
