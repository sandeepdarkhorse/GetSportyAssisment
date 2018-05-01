package main.darkhorse.com.getsportyassisment.performance;

import java.util.ArrayList;

/**
 * Created by kumargaurav on 25/10/17.
 */

public class StudentListingResponse {

    private String status;
    private ArrayList<StudentDetails> data;
    private String msg;

    public StudentListingResponse(String status, ArrayList<StudentDetails> data, String msg) {
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

    public ArrayList<StudentDetails> getData() {
        return data;
    }

    public void setData(ArrayList<StudentDetails> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
