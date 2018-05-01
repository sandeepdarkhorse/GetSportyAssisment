package main.darkhorse.com.getsportyassisment.model_classes;

import java.util.ArrayList;

/**
 * Created by sandeepsingh on 20/4/18.
 */

public class AppliedTournamentListResponse {
    private String status;
    private ArrayList<AppliedTournamentApplicantResponse> data;
    private String msg;

    public AppliedTournamentListResponse(String status, ArrayList<AppliedTournamentApplicantResponse> data, String msg) {
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

    public ArrayList<AppliedTournamentApplicantResponse> getData() {
        return data;
    }

    public void setData(ArrayList<AppliedTournamentApplicantResponse> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
