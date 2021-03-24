package bst;

import bst.bean.AppTaskBean;
import bst.utils.GsonUtil;

import java.util.List;

public class GpTaskBean {

    private List<AppTaskBean> appTaskList;

    public List<AppTaskBean> getAppTaskList() {
        return appTaskList;
    }

    public void setAppTaskList(List<AppTaskBean> appTaskList) {
        this.appTaskList = appTaskList;
    }

    public static String toPrettyJson(GpTaskBean gpTaskBean){
        String prettyJson = GsonUtil.prettyJson(GsonUtil.GsonString(gpTaskBean));
        return prettyJson;
    }
}
