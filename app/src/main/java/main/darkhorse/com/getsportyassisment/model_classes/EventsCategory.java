package main.darkhorse.com.getsportyassisment.model_classes;

import java.util.ArrayList;

/**
 * Created by kumargaurav on 24/01/18.
 */

public class EventsCategory {
    private ArrayList<String> Male;
    private ArrayList<String> Female;
    private ArrayList<String> Both;

    public EventsCategory() {
    }

    public EventsCategory(ArrayList<String> male, ArrayList<String> female, ArrayList<String> both) {
        Male = male;
        Female = female;
        Both = both;
    }

    public ArrayList<String> getMale() {
        return Male;
    }

    public void setMale(ArrayList<String> male) {
        Male = male;
    }

    public ArrayList<String> getFemale() {
        return Female;
    }

    public void setFemale(ArrayList<String> female) {
        Female = female;
    }

    public ArrayList<String> getBoth() {
        return Both;
    }

    public void setBoth(ArrayList<String> both) {
        Both = both;
    }
}
