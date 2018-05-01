package main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess;


public class ShortlistRequest {
    private String userid;
    private String id;
    private String status;
    private String module;

    public ShortlistRequest(String userid, String id, String status, String module) {
        this.userid = userid;
        this.id = id;
        this.status = status;
        this.module = module;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
