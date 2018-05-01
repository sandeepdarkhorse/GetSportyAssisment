package main.darkhorse.com.getsportyassisment.UtilsFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sandeepsingh on 16/4/18.
 */

public class ApiClient {
//    public static final String BASE_URL_TEST = "http://192.168.1.124/laravel_api/public/api/";
//public static final String BASE_URL = "http://192.168.1.26/testingapp/";

    public static final String BASE_URL = MainUrls.base_url;
   // public static final String updateUrl=MainUrls.updateStatus;
   public static final String Googleplace_BASE_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/";


    private static Retrofit retrofit = null;
    private static Retrofit retrofitLocal = null;
    private static Retrofit retrofitStatus=null;
    private static Retrofit retrofit1=null;

    public static Retrofit getClient()
    {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .readTimeout(10, TimeUnit.SECONDS).build();



        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }



    public static Retrofit getClientLocal() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        if (retrofitLocal == null) {
            retrofitLocal = new Retrofit.Builder()
                    //.baseUrl("http://192.168.0.109/testingapp/")// for local url
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofitLocal;
    }

//    public static Retrofit getClientUpdate()
//    {
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
//                .readTimeout(10, TimeUnit.SECONDS).build();
//
//
//
//        if (retrofitStatus==null) {
//            retrofitStatus = new Retrofit.Builder()
//                    .baseUrl(updateUrl)
//                    .client(client)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build();
//        }
//        return retrofitStatus;
//    }

    public static Retrofit getGooglePlaceClient() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        if (retrofit1 == null) {
            retrofit1 = new Retrofit.Builder()
                    .baseUrl(Googleplace_BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit1;
    }

}
