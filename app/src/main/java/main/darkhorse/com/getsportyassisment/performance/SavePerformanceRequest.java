package main.darkhorse.com.getsportyassisment.performance;

/**
 * Created by kumargaurav on 29/03/17.
 */

public class SavePerformanceRequest {

    private String coachid;
    private String athleteid;
    private String data;
    private String status;
    private String id;
    private String modules_avg;
    private String avg;


    public SavePerformanceRequest(String coachid, String athleteid, String data, String status, String id, String modules_avg, String avg) {
        this.coachid = coachid;
        this.athleteid = athleteid;
        this.data = data;
        this.status = status;
        this.id = id;
        this.modules_avg = modules_avg;
        this.avg = avg;
    }

    public String getCoachid() {
        return coachid;
    }

    public void setCoachid(String coachid) {
        this.coachid = coachid;
    }

    public String getAthleteid() {
        return athleteid;
    }

    public void setAthleteid(String athleteid) {
        this.athleteid = athleteid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModules_avg() {
        return modules_avg;
    }

    public void setModules_avg(String modules_avg) {
        this.modules_avg = modules_avg;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }
}
