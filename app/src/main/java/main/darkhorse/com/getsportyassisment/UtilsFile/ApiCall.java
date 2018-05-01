package main.darkhorse.com.getsportyassisment.UtilsFile;

import main.darkhorse.com.getsportyassisment.model_classes.TournamentListingResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by sandeepsingh on 16/4/18.
 */

public interface ApiCall {

    @GET
    Call<TournamentListingResponse> tournamentlisting(@Url String url);
}
