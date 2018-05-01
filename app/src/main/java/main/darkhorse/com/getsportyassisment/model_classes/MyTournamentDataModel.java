package main.darkhorse.com.getsportyassisment.model_classes;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sandeepsingh on 19/4/18.
 */

public class MyTournamentDataModel implements Serializable {
    private String id;
    private String userid;
    private String name;
    private String address_1;
    private String address_2;
    private String location;
    private String pin;
    private String state;
    private String description;
    private String sport;
    private String level;
    private String age_group;
    private String gender;
    private String org_address1;
    private String org_address2;
    private String org_city;
    private String organizer_state;
    private String org_pin;
    private String tournaments_link;
    private String tournament_fees;
    private String event_fees;
    private String max_participate;
    private String start_date;
    private String end_date;
    private String event_entry_date;
    private String event_end_date;
    private String file_name;
    private String file;
    private String email_app_collection;
    private String phone_app_collection;
    private String date_created;
    private String days;
    private String open;
    private String fav;
    private String job;
    private String organiser_name;
    private String mobile;
    private String email;
    private String image;
    private String landline;
    private String publish;
    private ArrayList<TournamentCategoryData> category;

    private ArrayList<TermsCoditionItemString> terms_cond;
    private RefDate refDate;

    public MyTournamentDataModel(String id, String userid, String name, String address_1, String address_2, String location, String pin, String state, String description, String sport, String tournament_level, String age_group, String gender, String org_address1, String org_address2, String org_city, String organizer_state, String org_pin, String tournaments_link, String tournament_fees, String event_fees, String max_participate, String start_date, String end_date, String event_entry_date, String event_end_date, String file_name, String file, String email_app_collection, String phone_app_collection, String date_created, String days, String open, String fav, String job, String organiser_name, String mobile, String email, String image, String landline, String publish, ArrayList<TournamentCategoryData> category, ArrayList<TermsCoditionItemString> terms_cond, RefDate refDate) {
        this.id = id;
        this.userid = userid;
        this.name = name;
        this.address_1 = address_1;
        this.address_2 = address_2;
        this.location = location;
        this.pin = pin;
        this.state = state;
        this.description = description;
        this.sport = sport;
        this.level = tournament_level;
        this.age_group = age_group;
        this.gender = gender;
        this.org_address1 = org_address1;
        this.org_address2 = org_address2;
        this.org_city = org_city;
        this.organizer_state = organizer_state;
        this.org_pin = org_pin;
        this.tournaments_link = tournaments_link;
        this.tournament_fees = tournament_fees;
        this.event_fees = event_fees;
        this.max_participate = max_participate;
        this.start_date = start_date;
        this.end_date = end_date;
        this.event_entry_date = event_entry_date;
        this.event_end_date = event_end_date;
        this.file_name = file_name;
        this.file = file;
        this.email_app_collection = email_app_collection;
        this.phone_app_collection = phone_app_collection;
        this.date_created = date_created;
        this.days = days;
        this.open = open;
        this.fav = fav;
        this.job = job;
        this.organiser_name = organiser_name;
        this.mobile = mobile;
        this.email = email;
        this.image = image;
        this.landline = landline;
        this.publish = publish;
        this.category = category;
        this.terms_cond = terms_cond;
        this.refDate = refDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress_1() {
        return address_1;
    }

    public void setAddress_1(String address_1) {
        this.address_1 = address_1;
    }

    public String getAddress_2() {
        return address_2;
    }

    public void setAddress_2(String address_2) {
        this.address_2 = address_2;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getTournament_level() {
        return level;
    }

    public void setTournament_level(String tournament_level) {
        this.level = tournament_level;
    }

    public String getAge_group() {
        return age_group;
    }

    public void setAge_group(String age_group) {
        this.age_group = age_group;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getOrg_address1() {
        return org_address1;
    }

    public void setOrg_address1(String org_address1) {
        this.org_address1 = org_address1;
    }

    public String getOrg_address2() {
        return org_address2;
    }

    public void setOrg_address2(String org_address2) {
        this.org_address2 = org_address2;
    }

    public String getOrg_city() {
        return org_city;
    }

    public void setOrg_city(String org_city) {
        this.org_city = org_city;
    }

    public String getOrganizer_state() {
        return organizer_state;
    }

    public void setOrganizer_state(String organizer_state) {
        this.organizer_state = organizer_state;
    }

    public String getOrg_pin() {
        return org_pin;
    }

    public void setOrg_pin(String org_pin) {
        this.org_pin = org_pin;
    }

    public String getTournaments_link() {
        return tournaments_link;
    }

    public void setTournaments_link(String tournaments_link) {
        this.tournaments_link = tournaments_link;
    }

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

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getEvent_entry_date() {
        return event_entry_date;
    }

    public void setEvent_entry_date(String event_entry_date) {
        this.event_entry_date = event_entry_date;
    }

    public String getEvent_end_date() {
        return event_end_date;
    }

    public void setEvent_end_date(String event_end_date) {
        this.event_end_date = event_end_date;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getEmail_app_collection() {
        return email_app_collection;
    }

    public void setEmail_app_collection(String email_app_collection) {
        this.email_app_collection = email_app_collection;
    }

    public String getPhone_app_collection() {
        return phone_app_collection;
    }

    public void setPhone_app_collection(String phone_app_collection) {
        this.phone_app_collection = phone_app_collection;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getOrganiser_name() {
        return organiser_name;
    }

    public void setOrganiser_name(String organiser_name) {
        this.organiser_name = organiser_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public ArrayList<TournamentCategoryData> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<TournamentCategoryData> category) {
        this.category = category;
    }

    public ArrayList<TermsCoditionItemString> getTerms_cond() {
        return terms_cond;
    }

    public void setTerms_cond(ArrayList<TermsCoditionItemString> terms_cond) {
        this.terms_cond = terms_cond;
    }

    public RefDate getRefDate() {
        return refDate;
    }

    public void setRefDate(RefDate refDate) {
        this.refDate = refDate;
    }
}
