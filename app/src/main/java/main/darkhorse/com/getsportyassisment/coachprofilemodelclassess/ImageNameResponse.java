package main.darkhorse.com.getsportyassisment.coachprofilemodelclassess;

/**
 * Created by Nikhil on 14/2/17.
 */
public class ImageNameResponse {
    private String user_image;

    public ImageNameResponse(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }
}
