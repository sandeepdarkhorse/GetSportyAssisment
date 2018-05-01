package main.darkhorse.com.getsportyassisment.performance;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by sandeepsingh on 20/4/18.
 */

public interface PerformanceApiCall {
    @Headers({"Content-type: application/json"})
    @POST("performance.php")
    Call<GetPerformanceModulesResponse> getPerformanceModuleRequest(@Query("act") String act, @Body GetPerformanceModuleRequest body);

    @Headers({"Content-type: application/json"})
    @POST("performance.php")
    Call<SavePerformanceResponse> savePerformanceRequest(@Query("act") String act, @Body SavePerformanceRequest body);

    @Headers({"Content-type: application/json"})
    @POST("performance.php")
    Call<PublishPerformanceResponse> publishPerformanceRequest(@Query("act") String act, @Body PublishPerformanceRequest body);


    @Headers({"Content-type: application/json"})
    @POST("performance.php")
    Call<ViewPerformanceResponse> viewPerformanceRequest(@Query("act") String act, @Body ViewPerformanceRequest body);

    @Headers({"Content-type: application/json"})
    @POST("performance.php")
    Call<ViewGuidelinesResponse> viewGuidelinesRequest(@Query("act") String act, @Body ViewGuidelinesRequest body);

    @Headers({"Content-type: application/json"})
    @POST("performance.php")
    Call<SuggestionResponse> suggestionRequest(@Query("act") String act, @Body SuggestionRequest body);

    @Headers({"Content-type: application/json"})
    @POST("performance.php")
    Call<StudentListingResponse> studentListing(@Query("act") String act, @Query("coach_id") String coach_id, @Query("class_id") String class_id);

}
