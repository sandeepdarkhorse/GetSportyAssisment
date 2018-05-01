package main.darkhorse.com.getsportyassisment.profileotherid;

/**
 * Created by sandeep on 7/11/17.
 */

public class OtherProfileResponse {
    private int status;
    private OtherProfileDetail data;


    private String msg;

    public OtherProfileResponse(int status, OtherProfileDetail data, String msg) {
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

    public OtherProfileDetail getData() {
        return data;
    }

    public void setData(OtherProfileDetail data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
