package main.darkhorse.com.getsportyassisment.model_classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sandeepsingh on 19/4/18.
 */

public class TournamentCategoryData implements Serializable {
    private String eventId;
    private String age;
    private String gender;
    private String fee;

    public String getReferanceDate() {
        return referanceDate;
    }

    public void setReferanceDate(String referanceDate) {
        this.referanceDate = referanceDate;
    }

    private String referanceDate;
    private String time;
    private String groupId;
    private String tournament_fees;
    private String event_fees;
    private String max_participate;
    ArrayList<CategoriesString> event;


    public String getTournament_feetype() {
        return tournament_feetype;
    }

    public void setTournament_feetype(String tournament_feetype) {
        this.tournament_feetype = tournament_feetype;
    }

    public String getTeam_sporttype() {
        return team_sporttype;
    }

    public void setTeam_sporttype(String team_sporttype) {
        this.team_sporttype = team_sporttype;
    }

    private String tournament_feetype;
    private String team_sporttype;


    public String getTournament_fees() {
        return tournament_fees;
    }

    public void setTournament_fees(String tournament_fees) {
        this.tournament_fees = tournament_fees;
    }

    public String getEvent_fees() {
        return event_fees;
    }

    public void setEvent_fees(String event_fees) {
        this.event_fees = event_fees;
    }

    public String getMax_participate() {
        return max_participate;
    }

    public void setMax_participate(String max_participate) {
        this.max_participate = max_participate;
    }


    public ArrayList<CategoriesString> getEvent() {
        return event;
    }

    public void setEvent(ArrayList<CategoriesString> event) {
        this.event = event;
    }


    public TournamentCategoryData(String eventId, String age,
                                  String gender, String fee,
                                  ArrayList<CategoriesString> event,
                                  String referanceDate, String time, String groupId, String tournament_fees,
                                  String event_fees,
                                  String max_participate, String tournament_feetype,
                                  String team_sporttype) {
        this.eventId = eventId;
        this.age = age;
        this.gender = gender;
        this.fee = fee;
        this.event = event;
        this.referanceDate = referanceDate;
        this.time = time;
        this.groupId = groupId;
        this.tournament_fees = tournament_fees;
        this.event_fees = event_fees;
        this.max_participate = max_participate;

        this.tournament_feetype = tournament_feetype;
        this.team_sporttype = team_sporttype;


    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
