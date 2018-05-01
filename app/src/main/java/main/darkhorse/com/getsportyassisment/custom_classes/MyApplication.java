package main.darkhorse.com.getsportyassisment.custom_classes;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;


import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.brodcastreceiver.ConnectivityReceiver;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;


public class MyApplication extends Application {




    private static MyApplication mInstance;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }





    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }

    private static boolean activityVisible;

    public static boolean isActivityVisible() {
        return activityVisible;
    }

    public static void activityResumed() {
        activityVisible = true;
    }

    public static void activityPaused() {
        activityVisible = false;
    }





}