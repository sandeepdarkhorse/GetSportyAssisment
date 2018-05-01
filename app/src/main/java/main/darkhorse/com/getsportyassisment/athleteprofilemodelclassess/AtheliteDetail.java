package main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess;

import java.util.ArrayList;

/**
 * Created by sandeep on 13/2/17.
 */
public class AtheliteDetail {

    private ArrayList<LatestResults> LatestResults;
    private Achivement Achivement;
    private Bio Bio;
    private Header Header;
    private User user;

    public AtheliteDetail(ArrayList<LatestResults> latestResults, Achivement achivement, Header header, Bio bio, User user) {
        this.LatestResults = latestResults;
        this.Achivement = achivement;
        this.Header = header;
        this.Bio = bio;
        this.user = user;
    }

    public AtheliteDetail() {

    }

    public User getUserdata() {
        return user;
    }

    public void setUserdata(User userdata) {
        this.user = userdata;
    }

    public ArrayList<LatestResults> getLatestResults() {
        return LatestResults;
    }

    public void setLatestResults(ArrayList<LatestResults> latestResults) {
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
