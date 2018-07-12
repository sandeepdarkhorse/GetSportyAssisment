package main.darkhorse.com.getsportyassisment.model_classes;

import java.io.Serializable;

/**
 * Created by shekhar on 9/7/18.
 */
public class Video_link  implements Serializable {
    public Video_link(String videolink) {
        this.videolink = videolink;
    }

    public String getVideolink() {
        return videolink;
    }

    public void setVideolink(String videolink) {
        this.videolink = videolink;
    }

    private String videolink;




}
