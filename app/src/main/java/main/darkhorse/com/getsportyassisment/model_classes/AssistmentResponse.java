package main.darkhorse.com.getsportyassisment.model_classes;

import java.util.ArrayList;

/**
 * Created by shekhar on 9/7/18.
 */
public class AssistmentResponse {
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

    public ArrayList<AssistmentModle> getData() {
        return data;
    }

    public void setData(ArrayList<AssistmentModle> data) {
        this.data = data;
    }

    String status;
    ArrayList<AssistmentModle> data;

}
