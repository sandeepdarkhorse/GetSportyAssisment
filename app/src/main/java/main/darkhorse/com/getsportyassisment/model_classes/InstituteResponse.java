package main.darkhorse.com.getsportyassisment.model_classes;

import java.util.ArrayList;



public class InstituteResponse {
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

    public ArrayList<InstituteDataPojoApi> getData() {
        return data;
    }

    public void setData(ArrayList<InstituteDataPojoApi> data) {
        this.data = data;
    }

    String status;
    ArrayList<InstituteDataPojoApi> data;

}
