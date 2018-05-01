package main.darkhorse.com.getsportyassisment.UtilsFile;

import com.google.gson.JsonElement;

import main.darkhorse.com.getsportyassisment.model_classes.AppliedTournamentListResponse;
import main.darkhorse.com.getsportyassisment.model_classes.FacebookDataPojo;
import main.darkhorse.com.getsportyassisment.model_classes.MyTournamentResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by sandeepsingh on 17/4/18.
 */

public interface ApiAtheliteCall {


    @POST("user_access_controller.php")
    Call<JsonElement> facebookLogin(@Query("act") String act, @Body FacebookDataPojo data);

    @GET("create_database.php")
    Call<MyTournamentResponse> getMyTournament(@Query("act") String act, @Query("userid") String userid, @Query("type") String type, @Query("id") String id);
    @GET("tournamentcontroller.php")
    Call<AppliedTournamentListResponse> getappliedtournament(@Query("act") String act, @Query("id") String id);


}
