package main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess;

import java.util.ArrayList;

/**
 * Created by sandeep on 22/2/17.
 */
public class AtheliteFootballDetail {
    private ArrayList<LatestResultsFootball> LatestResults;
    private Achivement Achivement;
    private Bio Bio;
    private Header Header;
    private User user;
    private String image;

    public AtheliteFootballDetail(ArrayList<LatestResultsFootball> latestResults, Achivement achivement, Bio bio, Header header, User user, String image) {
        LatestResults = latestResults;
        Achivement = achivement;
        Bio = bio;
        Header = header;
        this.user = user;
        this.image = image;
    }

    public AtheliteFootballDetail() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUserdata() {
        return user;
    }

    public void setUserdata(User userdata) {
        this.user = userdata;
    }

    public ArrayList<LatestResultsFootball> getLatestResults() {
        return LatestResults;
    }

    public void setLatestResults(ArrayList<LatestResultsFootball> latestResults) {
        LatestResults = latestResults;
    }

    public Achivement getAchivement() {
        return Achivement;
    }

    public void setAchivement(Achivement achivement) {
        Achivement = achivement;
    }

    public Bio getBio() {
        return Bio;
    }

    public void setBio(Bio bio) {
        Bio = bio;
    }

    public Header getHeader() {
        return Header;
    }

    public void setHeader(Header header) {
        Header = header;
    }
}
