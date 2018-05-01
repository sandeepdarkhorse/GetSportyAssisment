package main.darkhorse.com.getsportyassisment.model_classes;

import java.io.Serializable;

/**
 * Created by sandeepsingh on 19/4/18.
 */

public class CategoriesString implements Serializable {
    String Event;
    String event_id;

    public CategoriesString(String event, String event_id) {
        Event = event;
        this.event_id = event_id;
    }

    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }
}
