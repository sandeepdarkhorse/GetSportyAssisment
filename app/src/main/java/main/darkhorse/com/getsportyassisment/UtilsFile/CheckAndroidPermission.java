package main.darkhorse.com.getsportyassisment.UtilsFile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sandeepsingh on 20/4/18.
 */

public class CheckAndroidPermission {

    private void askForPermission(Activity activity, Context context, String permission, Integer requestCode) {
        if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {

                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);

            } else {

                ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            }
        } else {
            Toast.makeText(activity, "" + permission + " is already granted.", Toast.LENGTH_SHORT).show();
        }


    }

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    Context context;
    Activity currentactivity;

    public static boolean checkAndRequestPermissions(Context context, Activity currentactivity) {
        int camera = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        int storage = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int loc = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
        int loc2 = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);

        int internet = ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET);
        int read_sms = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_SMS);
        int recivive_sms = ContextCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS);
        int read_external_storage = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);

        int access_network = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_NETWORK_STATE);
        int cell_phone = ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE);
        int send_sms = ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS);
        int wake_lock = ContextCompat.checkSelfPermission(context, Manifest.permission.WAKE_LOCK);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (camera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (loc2 != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (loc != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }


        if (internet != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.INTERNET);
        }
        if (read_sms != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_SMS);
        }
        if (recivive_sms != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.RECEIVE_SMS);
        }
        if (read_external_storage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }

        if (access_network != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.ACCESS_NETWORK_STATE);
        }
        if (cell_phone != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE);
        }
        if (send_sms != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.SEND_SMS);
        }
        if (wake_lock != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WAKE_LOCK);
        }


        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(currentactivity, listPermissionsNeeded.toArray
                    (new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
//            return checkAndRequestPermissions( context, currentactivity);
            return false;
        }

        return true;
    }


}