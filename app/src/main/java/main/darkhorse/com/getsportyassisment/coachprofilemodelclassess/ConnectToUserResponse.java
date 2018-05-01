package main.darkhorse.com.getsportyassisment.coachprofilemodelclassess;

/**
 * Created by Nikhil on 24/2/17.
 */
public class ConnectToUserResponse {
    private String msg;
    private int status;

    public ConnectToUserResponse(String msg, int status) {
        this.msg = msg;
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
