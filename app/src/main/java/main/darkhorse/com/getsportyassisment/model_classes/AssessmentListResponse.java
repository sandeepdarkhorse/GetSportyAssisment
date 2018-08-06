package main.darkhorse.com.getsportyassisment.model_classes;

import java.util.ArrayList;

/**
 * Created by shekhar on 2/8/18.
 */
public class AssessmentListResponse {
    String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<AssessmentListDataPojo> getData() {
        return data;
    }

    public void setData(ArrayList<AssessmentListDataPojo> data) {
        this.data = data;
    }

    String status;
    ArrayList<AssessmentListDataPojo> data;

}
