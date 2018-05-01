package main.darkhorse.com.getsportyassisment.coachprofilemodelclassess;

public class ConnectedUser {

    private String id;
    private String req_status;

    public ConnectedUser(String req_status, String id) {
        this.req_status = req_status;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReq_status() {
        return req_status;
    }

    public void setReq_status(String req_status) {
        this.req_status = req_status;
    }
}
