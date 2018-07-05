package main.darkhorse.com.getsportyassisment.athleteprofilemodelclassess;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.json.JSONObject;


import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.ConnectToUserResponse;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.ConnectedUserResponse;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.GenericResponse;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.ImageUploadRequest;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.UserProfileCoachEditRequest;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.UserProfileCoachEditResponse;
import main.darkhorse.com.getsportyassisment.coachprofilemodelclassess.UserProfileCoachResponse;
import main.darkhorse.com.getsportyassisment.model_classes.ApplicantJobOfferResponse;
import main.darkhorse.com.getsportyassisment.model_classes.DietLogResponse;
import main.darkhorse.com.getsportyassisment.model_classes.GooglePlaceApiResponse;
import main.darkhorse.com.getsportyassisment.model_classes.SendOfferData;
import main.darkhorse.com.getsportyassisment.model_classes.ShortlistCandidate;
import main.darkhorse.com.getsportyassisment.model_classes.Signbody;
import main.darkhorse.com.getsportyassisment.profileotherid.OtherProfileEditRequest;
import main.darkhorse.com.getsportyassisment.profileotherid.OtherProfileResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

/**
 * Created by sandeep on 13/2/17.
 */
public interface ApiAtheliteCall {

    @Headers({"Content-type: text/html"})
    @GET("userEdit.php")
    Call<UserProfileAtheliteResponse> getAtheliteProfile(@Query("act") String act, @Query("userid") String userId, @Query("prof_id") String prof);

//
//    @Headers({"Content-type: application/json"})
//    @POST("userEdit.php")
//    Call<UserProfileAthleteEditResponse> editAtheliteProfileData(@Query("act") String act, @Query("userid") String user, @Query("prof_id") String prof, @Body UserProfileCoachEditRequest body);
//
//
    @Headers({"Content-type: text/html"})
    @GET("userEdit.php")
    Call<UserProfileAtheliteTennisResponse> getAtheliteProfileTennis(@Query("act") String act, @Query("userid") String userId, @Query("prof_id") String prof);

//
    @Headers({"Content-type: text/html"})
    @GET("userEdit.php")
    Call<UserProfileCoachResponse> getCoachProfile(@Query("act") String act, @Query("userid") String userId, @Query("prof_id") String prof);

//
    @Headers({"Content-type: application/json"})
    @POST("userEdit.php")
    Call<UserProfileCoachEditResponse> editProfileData(@Query("act") String act, @Query("userid") String user, @Query("prof_id") String prof, @Body UserProfileCoachEditRequest body);
//
//
//    @Headers({"Content-type: application/json"})
//    @POST("userEdit.php")
//    Call<UserProfileAthleteEditResponse> editAtheliteProfileData(@Query("act") String act, @Query("userid") String user, @Query("prof_id") String prof, @Body AthleteCoachRequest body);
//
    @Headers({"Content-type: application/json"})
    @POST("userEdit.php")
    Call<GenericResponse> saveImage(@Query("act") String act, @Query("userid") String user, @Body ImageUploadRequest body);

//
//    @Headers({"Content-type: application/json"})
//    @POST("userEdit.php")
//    Call<UserProfileAthleteEditResponse> editAtheliteTennisProfileData(@Query("act") String act, @Query("userid") String user, @Query("prof_id") String prof, @Body AthleteTennisRequest body);
//
//    @Headers({"Content-type: application/json"})
//    @POST("userEdit.php")
//    Call<UserProfileAthleteEditResponse> editAtheliteFootbalProfileData(@Query("act") String act, @Query("userid") String user, @Query("prof_id") String prof, @Body AthleteFotballRequest body);
//
//
    @Headers({"Content-type: text/html"})
    @GET("userEdit.php")
    Call<UserProfileAtheliteFootballResponse> getAtheliteProfileFootball(@Query("act") String act, @Query("userid") String userId, @Query("prof_id") String prof);

//
    @GET("connect_user.php")
    Call<ConnectToUserResponse> sendConnectionResponseRequest(@Query("act") String act, @Query("id") String connectionid, @Query("req_status") String requestType, @Query("user_app") String userapptype);

//
    @GET("create_database.php")
    Call<ConnectedUserResponse> getConnectionRequest(@Query("act") String act, @Query("lite_user_id") String userId, @Query("prof_user_id") String prof);

//    @GET("connect_user.php")
//    Call<OrganizedClassesResponse> getClassInfo(@Query("act") String act, @Query("class_id") String classId, @Query("student_userid") String student_id);
//
//
//    @GET("notificationController.php")
//    Call<NotificationResponse> getAllNotification(@Query("act") String act, @Query("userid") String userId, @Query("user_app") String type);
//
//    @GET("connect_user.php")
//    Call<ProfessionalListResponse> getConnections(@Query("act") String act, @Query("userid") String userid, @Query("usertype") String userType);
//
//    @GET("Connect_user.php")
//    Call<NotificationResponse> getAllSchedulingClass(@Query("act") String act, @Query("userid") String userId);
//
//    @GET("connect_user.php")
//    Call<OrganizedClassesResponse> getOrganizedClasses(@Query("act") String act, @Query("userid") String userid);
//
//    @GET("ManageSchedule_classController.php")
//    Call<StudentJoinListResponse> getStudentList(@Query("act") String act, @Query("classid") String classid);
//
//
//    @GET("ManageSchedule_classController.php")
//    Call<UpdateStudentFeeResponse> updateStudentfee(@Query("act") String act, @Query("class_id") String classId, @Query("student_userid") String student_id, @Query("paid") String paid);
//
//    @Headers({"Content-type: application/json"})
//    @POST("create_database.php")
//    Call<GetPublishjobResponse> getPublishRequest(@Query("act") String act, @Body PublishRequest body);
//
//    @GET("create_database.php")
//    Call<AppliedJobListResponse> getappliedjobs(@Query("act") String act, @Query("user_id") String userid, @Query("id") String id);
//
//    @GET("tournamentcontroller.php")
//    Call<AppliedTournamentListResponse> getappliedtournament(@Query("act") String act, @Query("id") String id);
//
//
//    @GET("connect_user.php")
//    Call<PaidClassesResponse> get_paidclasslisting(@Query("act") String act, @Query("coach_id") String userid, @Query("flag") String flag);
//
//
//    @GET("connect_user.php")
//    Call<StudentPaidListResponse> get_studentpaidlisting(@Query("act") String act, @Query("class_id") String class_id, @Query("flag") String flag);
//
//
////    @GET("connect_user.php")
////    Call<CoachLogListResponse> getloglisting(@Query("act") String act, @Query("userid") String coach_id);
//
//    @FormUrlEncoded
//    @POST("connect_user.php")
//    Call<CoachLogListResponse> getloglisting(@Query("act") String act, @Field("data") JSONObject data);
//
//
//
//    @GET("connect_user.php")
//    Call<ActivityLogListResponse> getactivitylisting(@Query("act") String act);
//
//
//    @FormUrlEncoded
//    @POST("connect_user.php")
//    Call<UpdateStudentFeeResponse> createlog(@Query("act") String act, @Field("data") JSONObject data);
//
//
//    @FormUrlEncoded
//    @POST("connect_user.php")
//    Call<StudentPaidListResponse> get_studentloglist(@Query("act") String act, @Field("data") JSONObject data);
//
//
//    @POST("connect_user.php")
//    Call<LogUnassignLogResponse> get_studentloglist(@Query("act") String act, @Body LogUnassignRequest data);
//
//
//    @FormUrlEncoded
//    @POST("connect_user.php")
//    Call<OrganizedClassesResponse> getOrganizedClasseslog(@Query("act") String act, @Field("data") JSONObject data);
//
//
//    @GET("create_database.php")
//    Call<MyjobResponse> getMyJob(@Query("act") String act, @Query("userid") String userid, @Query("type") String type, @Query("id") String id);
//
//    @GET("create_database.php")
//    Call<MyEventResponse> getMyEvent(@Query("act") String act, @Query("userid") String userid, @Query("type") String type, @Query("id") String id);
//
//
//    @GET("create_database.php")
//    Call<MyTournamentResponse> getMyTournament(@Query("act") String act, @Query("userid") String userid, @Query("type") String type, @Query("id") String id);
//
//    @GET("create_database.php")
//    Call<OrganizedClassesResponse> ForgetPassword(@Query("act") String act, @Query("email") String user_email);
//
//
    @POST("create_database.php")
    Call<ShortlistCandidate> shortlist_candidate(@Query("act") String act, @Body ShortlistRequest data);

//
//    @POST("create_database.php")
//    Call<AlignInterViewResponse> align_candidate_interView(@Query("act") String act, @Body AlignDataRequest data);
//
//
//    @GET("connect_user.php")
//    Call<DailyLogResponse> getAthleteLogs(@Query("act") String act, @Query("coach_id") String coach_id, @Query("athlete_id") String athlete_id);
//
//    @POST("create_database.php")
//    Call<UniversalResponse> logout(@Query("act") String act, @Body LogOutRequest body);
//
//    @POST("create_database.php")
//    Call<ApplicantJobOfferResponse> send_offer(@Query("act") String act, @Body SendOfferToApplicant data);
//
//    @GET("eventcontroller.php")
//    Call<ParticipantResponse> getParticipantList(@Query("act") String act, @Query("id") String id, @Query("user_id") String user_id);
//
//    @POST("eventcontroller.php")
//    Call<AlignInterViewResponse> applyPasscode(@Query("act") String act, @Body PasscodeRequest data);
//
//
//    @GET("create_database.php")
//    Call<MyEventResponse> geteventdetail(@Query("act") String act, @Query("user_id") String userid, @Query("id") String id, @Query("type") String type);
//
////    @GET("version_controller.php")
////    Call<UniversalResponse> getappVersion(@Query("act") String act,@Query("version_key") String version_key );
//
//    @POST("version_controller.php")
//    Call<UniversalResponse> getappVersion(@Query("act") String act, @Body MyAppVersion data);
//
//    @Headers({"Content-type: application/json"})
//    @POST("ManageSchedule_classController.php")
//      Call<CreateClassResponse> create_class(@Query("act") String act, @Body CreateClassRequest body);
//
//    @Headers({"Content-type: application/json"})
//    @POST("dietPlanController.php")
//    Call<ApplicantJobOfferResponse> MyDietPlanData(@Query("act") String act, @Query("userid") String user, @Body MyDietPlanResponse body, @Query("usertype") String usertype);
//
//    @GET("dietPlanController.php")
//    Call<DietPlanListingResponse> getmydietplanlisting(@Query("act") String act, @Query("userid") String userid);
//
//    @Headers({"Content-type: application/json"})
//    @POST("dietPlanController.php")
//    Call<ApplicantJobOfferResponse> MyDietPlanUpdate(@Query("act") String act, @Query("id") String id, @Body MyDietPlanResponse body);
//
//
//    @Headers({"Content-type: application/json"})
//    @POST("ManageSchedule_classController.php")
//    Call<UpdateClassResponse> update_class(@Query("act") String act, @Body UpdateClassRequest body);
//
//
    @GET
    public Call<GooglePlaceApiResponse> GoogleApiCall(@Url String url);
//
//    @Headers({"Content-type: application/json"})
//    @POST("connect_user.php")
//    Call<ApplicantJobOfferResponse>ClassStudentAdd(@Query("act") String act, @Body ClassStudentAdd body);
//
//    @Headers({"Content-type: application/json"})
//    @POST("connect_user.php")
//    Call<ApplicantJobOfferResponse>demoStudentAdd(@Query("act") String act, @Body AddDemoStudent body);
//
//
//    @GET("connect_user.php")
//    Call<DemoClassResponse> getStudentdemorequest(@Query("act") String act, @Query("coach_id") String coach_id, @Query("class_id") String classid);
//
//
//
//    @Headers({"Content-type: application/json"})
//    @POST("dietPlanController.php")
//    Call<CreateClassResponse> assign_dietplan(@Query("act") String act, @Body DietAssignRequest body);
//
//
//    @GET("dietPlanController.php")
//    Call<StudentPaidListResponse> getdietstudentlist(@Query("act") String act, @Query("diet_id") String diet_id, @Query("coach_id") String coach_id);
//

    @GET("dietPlanController.php")
    Call<DietLogResponse> getDietLog(@Query("act") String act, @Query("userid") String userid, @Query("usertype") String usertype);

//    @GET("getListingController.php")
//    Call<ActivityEventListResponse> getEventlisting(@Query("act") String act);
//
//    @GET("connect_user.php")
//    Call<ScheduleResponse> getSchedule(@Query("act") String act, @Query("user_id") String userId);
//
//
//    @FormUrlEncoded
//    @POST("connect_user.php")// this api is used only for status and time update
//    Call<UniversalResponse> editSchedule(@Query("act") String act, @Field("id") String id, @Field("time_of_day") String time, @Field("active_status") String activeStatus); // this api is used only for status and time update
//
//
//    @FormUrlEncoded
//    @POST("create_database.php")
//    Call<StudentPaidListResponse> createtournament(@Query("act") String act, @Field("data") JSONObject data);
//
//    @Headers({"Content-type: application/json"})
//    @POST("create_database.php")
//    Call<StudentPaidListResponse> create_event(@Query("act") String act, @Body CreateEventData body);
//
//
//    @Headers({"Content-type: application/json"})
//    @POST("create_database.php")
//    Call<StudentPaidListResponse> create_tournament(@Query("act") String act, @Body CreateTournamentData body);
//
//
//
//    @POST("connect_user.php")
//    Call<CreateScheduleResponse> createSchedule(@Query("act") String act, @Body ScheduleRequest body);
//
//    @POST("connect_user.php")
//    Call<CreateScheduleResponse> updateSchedule(@Query("act") String act, @Body ScheduleData body);
//
//    @POST("connect_user.php")
//    Call<ScheduleAssignResponse> get_AssignResponse(@Query("act") String act, @Body AssignScheduledData body);
//
//    @Headers({"Content-type: application/json"})
//    @POST("connect_user.php")
//    Call<StudentPaidListResponse> remove_demorequest(@Query("act") String act, @Body RemoveDemoRequest body);
//
////    @FormUrlEncoded
////    @POST("connect_user.php")
////    Call<StudentPaidListResponse> get_Unassignlist(@Query("act") String act, @Field("data") JSONObject data);
//
//    @POST("connect_user.php")
//    Call<LogUnassignLogResponse> get_Unassignlist(@Query("act") String act, @Body ScheduleUnassignRequest data);
//
//
//
//    @GET("connect_user.php")
//    Call<OrganizedClassesResponse> getClassesForSchedule(@Query("act") String act, @Query("userid") String userid, @Query("schedule_id") String schedule_id);
//
//    @GET("ManageSchedule_classController.php")
//    Call<StudentListResponse> getListStudent(@Query("act") String act, @Query("classid") String classid);
//
//    @GET("attendance_controller.php")
//    Call<AttendanceListResponse> getStudentListFor(@Query("act") String act, @Query("class_id") String class_id, @Query("date") String date);
//
////    @POST("connect_user.php")
////    Call<ScheduleAssignResponse> get_AttendanceResponse(@Query("act") String act, @Body AttendanceData body);
//
////    @FormUrlEncoded
////    @POST("attendance_controller.php")
////    Call<ScheduleAssignResponse> get_AttendanceResponse(@Query("act") String act,@Query("classid")String classid,@Query("date")String date, @Field("data") JSONObject data);
//
//
//    @POST("attendance_controller.php")
//    Call<ScheduleAssignResponse> get_AttendanceResponse(@Query("act") String act, @Query("classid") String classid, @Query("date") String date, @Body JsonObject data);
//
//
//    @Headers({"Content-type: application/json"})
//    @POST("attendance_controller.php")
//    Call<ClassAtendanceResponse> class_atendance_calender(@Query("act") String act, @Body ClassAtendanceRequest body);
//    //http://www.getsporty.in/testingapp/attendance_controller.php?act=cancel_class
//    @POST("attendance_controller.php")
//    Call<LogUnassignLogResponse> cancelClass(@Query("act") String act, @Body CancelClass data);
//
//    @POST("ManageSchedule_classController.php")
//    Call<RescheduleDataResponse> rescheduleClass(@Query("act") String act, @Body RescheduleData data);
//
//
    @Headers({"Content-type: text/html"})
    @GET("userEdit.php")
    Call<OtherProfileResponse> getCoachProfilOther(@Query("act") String act, @Query("userid") String userId, @Query("prof_id") String prof);


    @Headers({"Content-type: application/json"})
    @POST("userEdit.php")
    Call<UserProfileCoachEditResponse> editOtherProfileData(@Query("act") String act, @Query("userid") String user, @Query("prof_id") String prof, @Body OtherProfileEditRequest body);
//
//
//    @Headers({"Content-type: text/html"})
//    @GET("getListingController.php")
//    Call<Responseprofessionallist> getprofessionallist(@Query("act") String act, @Query("prof_type") String prof_type);
//
////    @GET("create_database.php")
////    Call<String> checkuserlogin(@Query("act") String act,@Field("data") JSONObject data);
//
//
//    @FormUrlEncoded
//    @POST("create_database.php")
//    Call<JsonElement> checkuserlogin(@Query("act") String act, @Field("data") JSONObject data);
//
//    @FormUrlEncoded
//    @POST("create_database.php")
//    Call<JsonElement> ManageSignUp(@Query("act") String act, @Field("data") JSONObject data);
//
//    //http://testingapp.getsporty.in/user_access_controller.php?act=gs_login
//
//    @POST("user_access_controller.php")
//    Call<JsonElement> facebookLogin(@Query("act") String act, @Body FacebookDataPojo data);
//
//
//    @POST("user_access_controller.php")
//    Call<JsonElement> facebookSignup(@Query("act") String act, @Body FacebookSignupDataPojo data);
//
//    @POST("angularapi.php")
//    Call<CreateOrgResponse> createOrg(@Query("act") String act, @Body CreateOrg data);
//
//    @GET
//    Call<JsonElement> getsport_List(@Url String url);
//
//    @POST("create_database.php")
//    Call<ResponseforDevice> updateDeviceId(@Query("act") String act, @Body UpdateDeviceIdRequest data);
//
//
//    @GET
//    Call<JsonElement> getorg_detail(@Url String url);
//
//
//    @Headers({"Content-type: application/json"})
//    @POST("create_database.php")
//    Call<StudentPaidListResponse> createtournament(@Query("act") String act, @Body CreateTournamentModel body);
//
//    @POST("tournamentcontroller.php")
//    Call<TourSportsListResponse> getTourSports(@Query("act") String act);
//
//    @GET
//    Call<JsonElement> getorderData(@Url String url);
//
//    @Headers({"Content-type: application/json"})
//    @POST("paymentController.php")
//    Call<JsonElement> GetenerateHashkey(@Query("act") String act, @Body HashkeyPayUData body);
//
//
//    @Headers({"Content-type: application/json"})
//    @POST("success_api_mobile_controller.php")
//    Call<GetPublishjobResponse> getJobPublishRequest(@Query("act") String act, @Body JobPublishRequest body);
//
    @POST("create_database.php")
    Call<ApplicantJobOfferResponse> send_offerNew(@Query("act") String act, @Body SendOfferData data);
//
//    @GET("paymentController.php")
//    Call<TransactionData> getTransactionList(@Query("act") String act, @Query("userid") String userid);
//
//
//    @Headers({"Content-type: application/json"})
//    @POST("tournamentcontroller.php")
//    Call<JsonElement> CreateTournamentLiveUpdate(@Query("act") String act, @Body TournamentAddUpdateEvent body);
//
//
//    @GET("tournamentcontroller.php")
//    Call<TournamentLiveUpdateListResponse> GetTournamentLiveUpdatelisting(@Query("act") String act, @Query("tournament_id") String id);
//
//    @GET
//    Call<JsonElement> getTransactionDetail(@Url String url);
//
//
//    @POST("create_database.php")
//    Call<CreateJobResponse> createJob(@Query("act") String act, @Body CreateJobData body);
//

//    @GET
//    Call<EventTypeListResponse> getevent_type_list(@Url String url);


//    @GET
//    Call<JsonElement> getsportysignin(@Url String url);

    @GET
    Call<JsonElement> ForgetPassword(@Url String url);


    @POST("adminUserController.php")
    Call<JsonElement> getsportysignin(@Query("act") String act, @Body Signbody body);



}

