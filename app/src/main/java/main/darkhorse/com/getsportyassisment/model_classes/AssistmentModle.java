package main.darkhorse.com.getsportyassisment.model_classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shekhar on 9/7/18.
 */
public class AssistmentModle   implements Serializable

{
    private String location;

    private String name;

    private String dob;

    private String userid;


    public ArrayList<Video_link> getVideo_link() {
        return video_link;
    }

    public void setVideo_link(ArrayList<Video_link> video_link) {
        this.video_link = video_link;
    }

    ArrayList<Video_link> video_link;


    private String gender;

    private String date;

    private String user_image;

    public String getLocation ()
    {
        return location;
    }

    public void setLocation (String location)
    {
        this.location = location;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getDob ()
    {
        return dob;
    }

    public void setDob (String dob)
    {
        this.dob = dob;
    }

    public String getUserid ()
    {
        return userid;
    }

    public void setUserid (String userid)
    {
        this.userid = userid;
    }



    public String getGender ()
    {
        return gender;
    }

    public void setGender (String gender)
    {
        this.gender = gender;
    }

    public String getDate ()
    {
        return date;
    }

    public void setDate (String date)
    {
        this.date = date;
    }

    public String getUser_image ()
    {
        return user_image;
    }

    public void setUser_image (String user_image)
    {
        this.user_image = user_image;
    }


}