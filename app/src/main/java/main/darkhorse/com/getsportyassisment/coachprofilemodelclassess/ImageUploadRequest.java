package main.darkhorse.com.getsportyassisment.coachprofilemodelclassess;


public class ImageUploadRequest {
    private String image;

    public ImageUploadRequest(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
