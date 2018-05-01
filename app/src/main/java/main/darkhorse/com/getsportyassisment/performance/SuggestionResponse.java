package main.darkhorse.com.getsportyassisment.performance;

/**
 * Created by kumargaurav on 10/05/17.
 */

public class SuggestionResponse {

    private int status;
    private int data;
    private String msg;

    public SuggestionResponse(int status, int data, String msg) {
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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
