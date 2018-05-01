package main.darkhorse.com.getsportyassisment.performance;

import com.google.gson.JsonElement;

/**
 * Created by kumargaurav on 29/03/17.
 */

public class SavePerformanceResponse {

    private int status;
    private JsonElement data;
    private String msg;

    public SavePerformanceResponse(int status, JsonElement data, String msg) {
        this.status = status;
        this.data = data;
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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
