package main.darkhorse.com.getsportyassisment.coachprofilemodelclassess;

/**
 * Created by Nikhil on 6/2/17.
 */
public class UserProfileCoachResponse {
    private int status;
    private CoachDetail data;
    private String msg;

    public UserProfileCoachResponse(int status, CoachDetail data, String msg) {
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

    public CoachDetail getData() {
        return data;
    }

    public void setData(CoachDetail data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
