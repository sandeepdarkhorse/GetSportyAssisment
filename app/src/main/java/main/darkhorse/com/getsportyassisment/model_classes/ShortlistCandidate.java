package main.darkhorse.com.getsportyassisment.model_classes;

/**
 * Created by sk singh on 20/4/17.
 */

public class ShortlistCandidate {

    private String msg;
    private int status;

    public ShortlistCandidate(String msg, int status) {
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
