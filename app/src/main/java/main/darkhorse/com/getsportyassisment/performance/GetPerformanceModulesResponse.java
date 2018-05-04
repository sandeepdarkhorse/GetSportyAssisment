package main.darkhorse.com.getsportyassisment.performance;

import com.google.gson.JsonElement;

/**
 * Created by kumargaurav on 27/03/17.
 */

public class GetPerformanceModulesResponse {



        private String status;
        private JsonElement data;
    private String msg;

    public GetPerformanceModulesResponse(String status, JsonElement data, String msg) {
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
