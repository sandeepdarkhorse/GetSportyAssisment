package main.darkhorse.com.getsportyassisment.model_classes;

/**
 * Created by sandeepsingh on 17/4/18.
 */

public class FacebookDataPojo {
    private String app;
    private String email;
    private FacebookDataItem data;
    private String password;
    private String loginType;
    private String userType;
    private String device_id;

    public FacebookDataPojo(String app, String email, FacebookDataItem data, String password, String loginType, String userType, String device_id) {
        this.app = app;
        this.email = email;
        this.data = data;
        this.password = password;
        this.loginType = loginType;
        this.userType = userType;
        this.device_id = device_id;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FacebookDataItem getData() {
        return data;
    }

    public void setData(FacebookDataItem data) {
        this.data = data;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }
}
