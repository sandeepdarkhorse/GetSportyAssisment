package main.darkhorse.com.getsportyassisment.coachprofilemodelclassess;

import java.util.ArrayList;




/**
 * Created by Nikhil on 6/2/17.
 */
public class GlobalUserProfileData {
    public static String otp_four_digit;
    public static GlobalUserProfileData globalUserProfileData = new GlobalUserProfileData();
    public static ArrayList<SportEducationData> sportEducationGlobal;
    public static ArrayList<FormalEducationData> formalEducationGlobal;
    public static ArrayList<OtherCertificationData> otherCertificationGlobal;
    public static ArrayList<WorkExperienceData> workExperienceGlobal;
    public static ArrayList<PlayerExperienceData> experienceAsPlayerGlobal;


//    public static MyTournamentDataItem tournamentdetailglobal;
//
//    public static MyEventDataItem eventdetailglobal;

    public static String designation;
    public static String acamedy;
    public static String location;
    public static String description;
    public static String sport;
  //  public static ArrayList<DailyLogGroupAthlete> globalDailyLog;
//    //Diet plan data
//    public static String start_date;
//    public static String end_date;
//    public static String dietplanname;
//    public static Diet_food dietplan;
//    public static Breakfast breakfast;
//    public static Snack1 snack1;
//    public static Lunch lunch;
//    public static Snack2 snack2;
//    public static Dinner dinner;
//    public static Monday monday;
//    public static Tuesday tuesday;
//    public static Wednesday wednesday;
//    public static Thursday thursday;
//    public static Friday friday;
//    public static Saturday saturday;
//    public static Sunday sunday;
//    public static String Diet_id;

    public static GlobalUserProfileData getInstance() {
        return globalUserProfileData;
    }

//    public static void reset() {
//        Diet_id=null;
//        start_date = null;
//        end_date = null;
//
//        dietplanname = null;
//
//        dietplan = null;
//
//        breakfast = null;
//
//        snack1 = null;
//
//        lunch = null;
//
//        snack2 = null;
//
//        dinner = null;
//
//        monday = null;
//
//        tuesday = null;
//
//        wednesday = null;
//
//        thursday = null;
//
//        friday = null;
//
//        saturday = null;
//
//        sunday = null;
//
//    }

}
