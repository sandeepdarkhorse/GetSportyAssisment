package main.darkhorse.com.getsportyassisment.performance;

/**
 * Created by kumargaurav on 31/03/17.
 */

public class PublishPerformanceRequest {

    private String data;
    private String status;
    private String id;

    public PublishPerformanceRequest(String data, String status, String id) {
        this.data = data;
        this.status = status;
        this.id = id;
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
}
