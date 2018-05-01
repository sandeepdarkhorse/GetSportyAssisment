package main.darkhorse.com.getsportyassisment.model_classes;

import java.util.ArrayList;

/**
 * Created by sandeepsingh on 19/4/18.
 */

public class MyTournamentResponse {
    private String msg;
    private ArrayList<MyTournamentDataModel> data;
    private int status;

    public MyTournamentResponse(String msg, ArrayList<MyTournamentDataModel> data, int status) {
        this.msg = msg;
        this.data = data;
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<MyTournamentDataModel> getData() {
        return data;
    }

    public void setData(ArrayList<MyTournamentDataModel> data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
