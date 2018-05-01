package main.darkhorse.com.getsportyassisment.model_classes;

import java.util.ArrayList;

/**
 * Created by sandeepsingh on 16/4/18.
 */

public class TournamentListingResponse {
    String status;
    String msg;
    ArrayList<TournamentListingResponseItem> data;

    public TournamentListingResponse(String status, String msg, ArrayList<TournamentListingResponseItem> data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<TournamentListingResponseItem> getData() {
        return data;
    }

    public void setData(ArrayList<TournamentListingResponseItem> data) {
        this.data = data;
    }
}
