package main.darkhorse.com.getsportyassisment.services;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Random;
//
//
//public class MyFirebaseMessagingService extends FirebaseMessagingService {
//
//    private static final String TAG = "MyMessage";
//    private int indicator;
//    private String msg;
//    private String title;
//    private int prof_id;
//    private int othersUserId;
//    private PendingIntent pendingIntent;
//    private Uri notificationSound;
//    private Intent intent;
//    public static final String KEY_TEXT_ACCEPT = "accept";
//    public static final String KEY_TEXT_REJECT = "reject";
//
//    @Override
//    public void onMessageReceived(RemoteMessage remoteMessage) {
//
//        //Log.e("Tag","Remote notification "+remoteMessage.getNotification().getBody());
//        Log.e("Tag","Remote data size "+remoteMessage.getData().size());
//        Log.e("Tag", "Data  "+remoteMessage.getData().toString());
//
//
//
//        if (remoteMessage.getData().size() > 0) {
//            try {
//                JSONObject body = new JSONObject(remoteMessage.getData().get("data1"));
//                sendNotification(body);
//
//            } catch (Exception e) {
//
//            }
//        }
//    }
//
//    private void sendNotification(JSONObject body) {
//
//        Log.e("Tag", "notification data "+body);
//
//        SharedPreferences sharedPreferences = getSharedPreferences("notification", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("FLAG", "notification");
//        editor.commit();
//
//        int requestID = (int) System.currentTimeMillis();
//        try {
//            indicator = body.getInt("indicator");
//            msg = body.getString("message");
//            title = body.getString("title");
//
//            if (indicator == 2)
//            {
//                intent = new Intent(this, ConnectedUsersActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                pendingIntent = PendingIntent.getActivity(this, requestID, intent, PendingIntent.FLAG_ONE_SHOT);
//                notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                        .setSmallIcon(getNotificationIcon())
//                        .setContentTitle(title)
//                        .setSound(notificationSound)
//                        .setContentText(msg)
//                        .setContentIntent(pendingIntent)
//                        .setColor(getResources().getColor(R.color.colorPrimary))
//                        .setAutoCancel(true);
//
//                Random random = new Random();
//                int unique = random.nextInt();
//                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(unique, builder.build());
//            }else if (indicator == 6) {
//                intent = new Intent(this, AthleteDashboardActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                pendingIntent = PendingIntent.getActivity(this, requestID, intent, PendingIntent.FLAG_ONE_SHOT);
//                notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                        .setSmallIcon(getNotificationIcon())
//                        .setContentTitle(title)
//                        .setSound(notificationSound)
//                        .setContentText(msg)
//                        .setContentIntent(pendingIntent)
//                        .setColor(getResources().getColor(R.color.colorPrimary))
//                        .setAutoCancel(true);
//
//                Random random = new Random();
//                int unique = random.nextInt();
//                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(unique, builder.build());
//
//            }else if (indicator==8){
//
//                JSONObject jsonObject= new JSONObject(msg);
//                JSONObject jsonObjectDietFood = jsonObject.getJSONObject("my_diet_plan").getJSONObject("diet_food");
//
//                String day="";
//                Iterator<?> keys = jsonObjectDietFood.keys();
//                while( keys.hasNext() ) {
//                    String key = (String)keys.next();
//                    if ( jsonObjectDietFood.get(key) instanceof JSONObject ) {
//                        day = key;
//                        // do whatever you want with it
//                    }
//                }
//
//                String breakfastTime = jsonObjectDietFood.getJSONObject(day).getJSONObject("breakfast").getString("time");
//                if (breakfastTime.contains("AM")){
//                    setLogNotification(Integer.parseInt(breakfastTime.substring(0,breakfastTime.indexOf(":"))),Integer.parseInt(breakfastTime.substring(breakfastTime.indexOf(":")+1, breakfastTime.indexOf("M")-1)), "Its Breakfast time", "Please have breakfast");
//                }
//                else {
//                    setLogNotification(Integer.parseInt(breakfastTime.substring(0,breakfastTime.indexOf(":")))+12,Integer.parseInt(breakfastTime.substring(breakfastTime.indexOf(":")+1, breakfastTime.indexOf("M")-1)), "Its Breakfast time", "Please have breakfast");
//
//                }
//                String snack1Time = jsonObjectDietFood.getJSONObject(day).getJSONObject("Snack1").getString("time");
//                if (breakfastTime.contains("AM")){
//                    setLogNotification(Integer.parseInt(breakfastTime.substring(0,breakfastTime.indexOf(":"))),Integer.parseInt(breakfastTime.substring(breakfastTime.indexOf(":")+1, breakfastTime.indexOf("M")-1)), "Its Snack time", "Please have snacks");
//                }
//                else {
//                    setLogNotification(Integer.parseInt(breakfastTime.substring(0,breakfastTime.indexOf(":")))+12,Integer.parseInt(breakfastTime.substring(breakfastTime.indexOf(":")+1, breakfastTime.indexOf("M")-1)), "Its Snack time", "Please have snacks");
//
//                }
//                String lunchTime = jsonObjectDietFood.getJSONObject(day).getJSONObject("lunch").getString("time");
//                if (breakfastTime.contains("AM")){
//                    setLogNotification(Integer.parseInt(breakfastTime.substring(0,breakfastTime.indexOf(":"))),Integer.parseInt(breakfastTime.substring(breakfastTime.indexOf(":")+1, breakfastTime.indexOf("M")-1)), "Its Lunch time", "Please have lunch");
//                }
//                else {
//                    setLogNotification(Integer.parseInt(breakfastTime.substring(0,breakfastTime.indexOf(":")))+12,Integer.parseInt(breakfastTime.substring(breakfastTime.indexOf(":")+1, breakfastTime.indexOf("M")-1)), "Its Lunch time", "Please have lunch");
//
//                }
//                String snack2Time = jsonObjectDietFood.getJSONObject(day).getJSONObject("Snack2").getString("time");
//                if (breakfastTime.contains("AM")){
//                    setLogNotification(Integer.parseInt(breakfastTime.substring(0,breakfastTime.indexOf(":"))),Integer.parseInt(breakfastTime.substring(breakfastTime.indexOf(":")+1, breakfastTime.indexOf("M")-1)), "Its Snack time", "Please have snacks");
//                }
//                else {
//                    setLogNotification(Integer.parseInt(breakfastTime.substring(0,breakfastTime.indexOf(":")))+12,Integer.parseInt(breakfastTime.substring(breakfastTime.indexOf(":")+1, breakfastTime.indexOf("M")-1)), "Its Snack time", "Please have snacks");
//
//                }
//                String dinnerTime = jsonObjectDietFood.getJSONObject(day).getJSONObject("dinner").getString("time");
//                if (breakfastTime.contains("AM")){
//                    setLogNotification(Integer.parseInt(breakfastTime.substring(0,breakfastTime.indexOf(":"))),Integer.parseInt(breakfastTime.substring(breakfastTime.indexOf(":")+1, breakfastTime.indexOf("M")-1)), "Its Dinner time", "Please have dinner");
//                }
//                else {
//                    setLogNotification(Integer.parseInt(breakfastTime.substring(0,breakfastTime.indexOf(":")))+12,Integer.parseInt(breakfastTime.substring(breakfastTime.indexOf(":")+1, breakfastTime.indexOf("M")-1)), "Its Dinner time", "Please have dinner");
//
//                }
//            }else if (indicator == 9) {
//                intent = new Intent(this, AthleteDashboardActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                pendingIntent = PendingIntent.getActivity(this, requestID, intent, PendingIntent.FLAG_ONE_SHOT);
//                notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                Random random = new Random();
//                int unique = random.nextInt();
//
//                String acceptLabel = getResources().getString(R.string.accept);
//                RemoteInput acceptRemoteInput = new RemoteInput.Builder(KEY_TEXT_ACCEPT)
//                        .setLabel(acceptLabel)
//                        .build();
//                String rejectLabel = getResources().getString(R.string.reject);
//                RemoteInput rejectRemoteInput = new RemoteInput.Builder(KEY_TEXT_REJECT)
//                        .setLabel(rejectLabel)
//                        .build();
//
//                Intent intentAccept = new Intent(this,SaveProfileService.class);
//                intentAccept.setAction(KEY_TEXT_ACCEPT);
//                intentAccept.putExtra("assign_id",body.getString("assign_id") );
//                intentAccept.putExtra("diet_id",body.getString("diet_id") );
//                intentAccept.putExtra("notification",unique);
//                PendingIntent pendingIntentAccept = PendingIntent.getService(this,100,intentAccept,PendingIntent.FLAG_UPDATE_CURRENT);
//
//                Intent intentReject = new Intent(this,SaveProfileService.class);
//                intentReject.setAction(KEY_TEXT_REJECT);
//                intentReject.putExtra("assign_id",body.getString("assign_id") );
//                intentAccept.putExtra("diet_id",body.getString("diet_id") );
//                intentAccept.putExtra("notification",unique);
//                PendingIntent pendingIntentReject = PendingIntent.getService(this,100,intentReject,PendingIntent.FLAG_UPDATE_CURRENT);
//
//                NotificationCompat.Action acceptAction =
//                        new NotificationCompat.Action.Builder(0,
//                                getString(R.string.accept), pendingIntentAccept)
//                                .addRemoteInput(acceptRemoteInput)
//                                .build();
//
//                NotificationCompat.Action rejectAction =
//                        new NotificationCompat.Action.Builder(0,
//                                getString(R.string.reject), pendingIntentReject)
//                                .addRemoteInput(rejectRemoteInput)
//                                .build();
//
//                JSONObject jsonObject = new JSONObject(msg);
//
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                        .setSmallIcon(getNotificationIcon())
//                        .setContentTitle(title)
//                        .setSound(notificationSound)
//                        .setContentText(jsonObject.getJSONObject("my_diet_plan").getString("name")+" from "+
//                                jsonObject.getJSONObject("my_diet_plan").getString("start_date")+" to "+
//                                jsonObject.getJSONObject("my_diet_plan").getString("end_date"))
//                        .setContentIntent(pendingIntent)
//                        .setColor(getResources().getColor(R.color.colorPrimary))
//                        .addAction(acceptAction)
//                        .addAction(rejectAction)
//                        .setAutoCancel(true);
//
//
//                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(unique, builder.build());
//            }
//            else if (indicator == 11) {
//                intent = new Intent(this, SplashScreenActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                pendingIntent = PendingIntent.getActivity(this, requestID, intent, PendingIntent.FLAG_ONE_SHOT);
//                notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                        .setSmallIcon(getNotificationIcon())
//                        .setContentTitle(title)
//                        .setSound(notificationSound)
//                        .setContentText(msg)
//                        .setContentIntent(pendingIntent)
//                        .setColor(getResources().getColor(R.color.colorPrimary))
//                        .setAutoCancel(true);
//
//                Random random = new Random();
//                int unique = random.nextInt();
//                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(unique, builder.build());
//            }
//            else if (indicator == 12) {
//                intent = new Intent(this, OpenNotification.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.putExtra("type",Integer.parseInt(body.getString("type")));
//                pendingIntent = PendingIntent.getActivity(this, requestID, intent, PendingIntent.FLAG_ONE_SHOT);
//                notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//                String CHANNEL_ID = "gs subscribe";
//
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                        .setSmallIcon(getNotificationIcon())
//                        .setContentTitle(title)
//                        .setSound(notificationSound)
//                        .setContentText(msg)
//                        .setContentIntent(pendingIntent)
//                        .setColor(getResources().getColor(R.color.colorPrimary))
//                        .setAutoCancel(true)
//                        .setChannelId(CHANNEL_ID);
//
//
//                Random random = new Random();
//                int unique = random.nextInt();
//                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                if (Build.VERSION.SDK_INT>=26){
//                int importance = NotificationManager.IMPORTANCE_HIGH;
//                NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID, importance);
//                notificationManager.createNotificationChannel(mChannel);
//                }
//                notificationManager.notify(unique, builder.build());
//            }else if(indicator == 3){
//                String jobId = body.getString("id");
//                Intent intent = new Intent(this, SearchDetailActivity.class);
//                intent.putExtra("searchStatus", 1);
//                intent.putExtra("job", "");
//                intent.putExtra("jobId", jobId);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                pendingIntent = PendingIntent.getActivity(this, requestID, intent, PendingIntent.FLAG_ONE_SHOT);
//                notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                        .setSmallIcon(getNotificationIcon())
//                        .setContentTitle(title)
//                        .setSound(notificationSound)
//                        .setContentText(msg)
//                        .setContentIntent(pendingIntent)
//                        .setColor(getResources().getColor(R.color.colorPrimary))
//                        .setAutoCancel(true);
//
//                Random random = new Random();
//                int unique = random.nextInt();
//                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(unique, builder.build());
//
//            }else if(indicator== 13){
//                String tournament_id =body.getString("tournament_id");
//                ArrayList<LiveNotificationTournament> liveNT=new ArrayList<LiveNotificationTournament>();
//                int updateCount=0;
//                if(liveNT.size()>0) {
//                    for (int i = 0; i < liveNT.size(); i++) {
//
//                        if (tournament_id.equals(liveNT.get(i).getNotification_id())) {
//                            updateCount = liveNT.get(i).getNotification_count();
//                            updateCount += updateCount;
//                            liveNT.get(i).setNotification_count(updateCount);
//                        } else {
//                            liveNT.get(i).setNotification_id(tournament_id);
//                            liveNT.get(i).setNotification_count(1);
//                        }
//
//                    }
//                }else{
//                    liveNT.get(0).setNotification_id(tournament_id);
//                    liveNT.get(0).setNotification_count(1);
//                }
//            }
//
//            else {
//                SharedPreferences profileData = getSharedPreferences("PROFILEDATA", MODE_PRIVATE);
//                String profId = profileData.getString("user_id", "");
//                if (profId.equals("1"))
//                    intent = new Intent(this, AthleteDashboardActivity.class);
//                else
//                    intent = new Intent(this, TryActivity.class);
//
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                pendingIntent = PendingIntent.getActivity(this, requestID, intent, PendingIntent.FLAG_ONE_SHOT);
//                notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//                NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
//                        .setSmallIcon(getNotificationIcon())
//                        .setContentTitle(title)
//                        .setSound(notificationSound)
//                        .setContentText(msg)
//                        .setContentIntent(pendingIntent)
//                        .setColor(getResources().getColor(R.color.colorPrimary))
//                        .setAutoCancel(true);
//
//                Random random = new Random();
//                int unique = random.nextInt();
//                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                notificationManager.notify(unique, builder.build());
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void setLogNotification(int hour, int min, String msg, String title){
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.set(Calendar.HOUR_OF_DAY, hour);
//        calendar.set(Calendar.MINUTE, min);
//        if (min<10){
//            calendar.set(Calendar.HOUR_OF_DAY,hour-1);
//            calendar.set(Calendar.MINUTE,60-min-10);
//        }
//
////        if (calendar.before(Calendar.getInstance())) {
////            calendar.add(Calendar.DATE, 1);
////        }
//
//        Intent intent = new Intent(getApplicationContext(), AlarmReciever.class);
//        intent.putExtra("msg", msg);
//        intent.putExtra("title", title);
//
//        PendingIntent pIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
//        AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent);
//
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.setTimeInMillis(System.currentTimeMillis());
//        calendar1.set(Calendar.HOUR_OF_DAY, hour);
//        calendar1.set(Calendar.MINUTE, min+20);
//        if (min+20>60){
//            calendar.set(Calendar.HOUR_OF_DAY,hour+1);
//            calendar.set(Calendar.MINUTE,min+20-60);
//        }
//
////        if (calendar.before(Calendar.getInstance())) {
////            calendar.add(Calendar.DATE, 1);
////        }
//
//        Intent intent1 = new Intent(getApplicationContext(), AlarmReciever.class);
//        intent1.putExtra("msg", msg);
//        intent1.putExtra("title", title);
//
//        PendingIntent pIntent1 = PendingIntent.getBroadcast(getApplicationContext(), 0, intent1, 0);
//        AlarmManager alarm1 = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        alarm1.setRepeating(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pIntent1);
//
//
//    }
//
//    private int getNotificationIcon() {
//        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
//        return useWhiteIcon ? R.mipmap.ic_launcher : R.mipmap.ic_launcher;
//    }
//}