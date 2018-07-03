package main.darkhorse.com.getsportyassisment.model_classes;

import java.io.Serializable;

public class PlacesSportsdetail
        implements Serializable {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDrawable_id() {
        return drawable_id;
    }

    public void setDrawable_id(int drawable_id) {
        this.drawable_id = drawable_id;
    }

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
    }

    public String getTimming() {
        return timming;
    }

    public void setTimming(String timming) {
        this.timming = timming;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getCoachingAv() {
        return coachingAv;
    }

    public void setCoachingAv(String coachingAv) {
        this.coachingAv = coachingAv;
    }

    String name;
    int drawable_id;
    String placename;
    String court;
    String timming;
    String fee;
    String coachingAv;

    public PlacesSportsdetail(String name, int drawable_id, String placename, String court, String timming, String fee, String coachingAv) {
        this.name = name;
        this.drawable_id = drawable_id;
        this.placename = placename;
        this.court = court;
        this.timming = timming;
        this.fee = fee;
        this.coachingAv = coachingAv;
    }




}
