package main.darkhorse.com.getsportyassisment.model_classes;

/**
 * Created by sandeepsingh on 31/7/18.
 * this file have static data when integrate the api this file can be deleted.
 */

public class DataModel {

    String school_name;
    String event_name;
    int date;
    int image;

    public DataModel(String school_name, String event_name, int date, int image) {
        this.school_name = school_name;
        this.event_name = event_name;
        this.date = date;
        this.image = image;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
