package main.darkhorse.com.getsportyassisment.model_classes;

import java.util.ArrayList;

public class DietLogResponse {

    private String status;
    private ArrayList<DietLog> data;
    private String msg;

    public DietLogResponse(String status, ArrayList<DietLog> data, String msg) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<DietLog> getData() {
        return data;
    }

    public void setData(ArrayList<DietLog> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
