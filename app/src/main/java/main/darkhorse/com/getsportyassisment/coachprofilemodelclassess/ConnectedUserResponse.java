package main.darkhorse.com.getsportyassisment.coachprofilemodelclassess;

public class ConnectedUserResponse {
    private String msg;
    private ConnectedUser data;
    private String status;

    public ConnectedUserResponse(String msg, ConnectedUser data, String status) {
        this.msg = msg;
        this.data = data;
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ConnectedUser getData() {
        return data;
    }

    public void setData(ConnectedUser data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
