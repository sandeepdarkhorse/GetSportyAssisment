package main.darkhorse.com.getsportyassisment.performance;

import com.google.gson.JsonElement;

/**
 * Created by kumargaurav on 31/03/17.
 */

public class PublishPerformanceResponse {

    private int status;
    private JsonElement data;
    private String msg;

    public PublishPerformanceResponse(int status, JsonElement data, String msg) {
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
