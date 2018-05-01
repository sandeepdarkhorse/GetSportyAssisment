package main.darkhorse.com.getsportyassisment.coachprofilemodelclassess;

/**
 * Created by Nikhil on 13/2/17.
 */
public class GenericResponse {
    private String msg;
    private ImageNameResponse data;
    private String status;

    public GenericResponse(String msg, ImageNameResponse data, String status) {
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

    public ImageNameResponse getData() {
        return data;
    }

    public void setData(ImageNameResponse data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
