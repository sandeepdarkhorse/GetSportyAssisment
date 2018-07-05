package main.darkhorse.com.getsportyassisment.model_classes;

/**
 * Created by shekhar on 5/7/18.
 */
public class Signbody {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    private String password;
    private String device_id;
    public Signbody(String email, String password, String device_id) {
        this.email = email;
        this.password = password;
        this.device_id = device_id;
    }




}
