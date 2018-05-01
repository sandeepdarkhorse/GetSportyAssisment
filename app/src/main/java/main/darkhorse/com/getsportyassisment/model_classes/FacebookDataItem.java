package main.darkhorse.com.getsportyassisment.model_classes;

/**
 * Created by sandeepsingh on 17/4/18.
 */

public class FacebookDataItem {
    private String id;
    private String email;
    private String name;
    private String image;

    public FacebookDataItem(String id, String email, String name, String image) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
