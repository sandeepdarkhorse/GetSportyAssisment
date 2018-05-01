package main.darkhorse.com.getsportyassisment.model_classes;

/**
 * Created by kumargaurav on 23/01/18.
 */

public class SportsData {
    private String id;
    private String sports;
    private String code;
    private String status_filter;
    private EventsCategory events_category;

    public SportsData(String id, String sports, String code, String status_filter, EventsCategory events_category) {
        this.id = id;
        this.sports = sports;
        this.code = code;
        this.status_filter = status_filter;
        this.events_category = events_category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSports() {
        return sports;
    }

    public void setSports(String sports) {
        this.sports = sports;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus_filter() {
        return status_filter;
    }

    public void setStatus_filter(String status_filter) {
        this.status_filter = status_filter;
    }

    public EventsCategory getEvents_category() {
        return events_category;
    }

    public void setEvents_category(EventsCategory events_category) {
        this.events_category = events_category;
    }
}
