package main.darkhorse.com.getsportyassisment.UtilsFile;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

/**
 * Created by sandeepsingh on 16/4/18.
 */

public class CommonUtils  {
    public static int TYPE_WIFI = 1;
        public static int TYPE_MOBILE = 2;
        public static int TYPE_WIMAX = 6;
        public static int TYPE_NOT_CONNECTED = 0;


        public static int getConnectivityStatus(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null != activeNetwork) {
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                    return TYPE_WIFI;

                if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_MOBILE;

                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIMAX)
                    return TYPE_WIMAX;

            }
            return TYPE_NOT_CONNECTED;
        }

        public static String getConnectivityStatusString(Context context) {
            int conn = CommonUtils.getConnectivityStatus(context);

            String status = null;
            if (conn == CommonUtils.TYPE_WIFI) {
                status = "Wifi enabled";
            } else if (conn == CommonUtils.TYPE_MOBILE) {
                status = "Mobile data enabled";
            } else if (conn == CommonUtils.TYPE_WIMAX) {
                status = "Mobile data enabled";
            } else if (conn == CommonUtils.TYPE_NOT_CONNECTED) {
                status = "Not connected to Internet";
            }
            return status;
        }

        public static void largeLog(String tag, String content) {
            if (content.length() > 4000) {
                largeLog(tag, content.substring(4000));
            } else {
                Log.e(tag,content);
            }
        }



        public static float dipToPixels(Context context, float dipValue) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
        }
}
