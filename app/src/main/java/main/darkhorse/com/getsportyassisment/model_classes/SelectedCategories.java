package main.darkhorse.com.getsportyassisment.model_classes;

/**
 * Created by kumargaurav on 25/01/18.
 */

public class SelectedCategories {
    private String category;
    private String id;
    private String date;
    private String time;

    public SelectedCategories(String category, String id, String date, String time) {
        this.category = category;
        this.id = id;
        this.date = date;
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
