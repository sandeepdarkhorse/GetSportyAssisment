package main.darkhorse.com.getsportyassisment.performance;

import com.google.gson.JsonElement;

/**
 * Created by kumargaurav on 31/03/17.
 */

public class ViewPerformanceResponse {

    private int status;
    private JsonElement data;
    private int next_assessment;
    private String msg;

    public ViewPerformanceResponse(int status, JsonElement data, int next_assessment, String msg) {
        this.status = status;
        this.data = data;
        this.next_assessment = next_assessment;
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }

    public int getNext_assessment() {
        return next_assessment;
    }

    public void setNext_assessment(int next_assessment) {
        this.next_assessment = next_assessment;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
