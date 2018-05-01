package main.darkhorse.com.getsportyassisment.model_classes;

/**
 * Created by nitin on 1/5/17.
 */

public class ApplicantJobOfferResponse {

    private String msg;
    private String status;

    public ApplicantJobOfferResponse(String msg, String status, String data) {
        this.msg = msg;
        this.status = status;

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
