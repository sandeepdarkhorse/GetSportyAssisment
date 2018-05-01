package main.darkhorse.com.getsportyassisment.UtilsFile;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by sandeepsingh on 17/4/18.
 */

public class GoogleClient {

    public static GoogleSignInOptions gso;
    public static GoogleApiClient mGoogleApiClient;
    private static GoogleClient googleclient = new GoogleClient();

    public GoogleClient() {

    }

    public static GoogleClient getInstance() {
        return googleclient;
    }

}
