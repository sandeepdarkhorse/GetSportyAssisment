package main.darkhorse.com.getsportyassisment.custom_classes;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import de.hdodenhof.circleimageview.CircleImageView;
import main.darkhorse.com.getsportyassisment.R;
import main.darkhorse.com.getsportyassisment.UtilsFile.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by shekhar kashyap on 10/8/17.
 */
public class DateConversion {

    public static Context context;
    public static String[] profiledata = new String[2];
    private static String mdy;
    private static String dayFromDate;
    private static Date date;
    private static String datetime;
    private static Retrofit retrofit;
    private static ApiClient apiCall;
    private static int profile;
    private static   int datecompare=0;

    public static String dateconversion(String input) {

        try {

            Date myDate = new SimpleDateFormat("yyyy-MM-dd").parse(input);
            SimpleDateFormat mdyFormat = new SimpleDateFormat("dd MMMM yyyy");

            // Format the date to Strings
            mdy = mdyFormat.format(myDate);


        } catch (ParseException exp) {
            exp.printStackTrace();
        }

        return mdy;
    }

    public static String dateconversionMonth(String input) {

        try {

            Date myDate = new SimpleDateFormat("yyyy-MM-dd").parse(input);
            SimpleDateFormat mdyFormat = new SimpleDateFormat("dd MMM yyyy");

            // Format the date to Strings
            mdy = mdyFormat.format(myDate);


        } catch (ParseException exp) {
            exp.printStackTrace();
        }

        return mdy;
    }


    public static String ConversionServerformat(String input) {

        try {

            Date myDate = new SimpleDateFormat("dd MMMM yyyy").parse(input);
            SimpleDateFormat mdyFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Format the date to Strings
            mdy = mdyFormat.format(myDate);


        } catch (ParseException exp) {
            exp.printStackTrace();
        }

        return mdy;
    }


    public static String ConversionLocaldateformat(String input) {

        try {


            Date myDate = new SimpleDateFormat("dd-MM-yyyy").parse(input);
            SimpleDateFormat mdyFormat = new SimpleDateFormat("dd MMMM yyyy");

            // Format the date to Strings
            mdy = mdyFormat.format(myDate);


        } catch (ParseException exp) {
            exp.printStackTrace();
        }

        return mdy;
    }


    public static String getFullDayName(int day, int month, int year) {
        Calendar c = Calendar.getInstance();
        // date doesn't matter - it has to be a Monday
        // I new that first August 2011 is one ;-)
        c.set(year, month, day, 0, 0, 0);
//        c.add(Calendar.YEAR,year);
//        c.add(Calendar.DAY_OF_YEAR, day);
        Log.e("Tag", "Formatted date " + String.format("%tA", c));
        return String.format("%tA", c);
    }

    public static String getday(String input_date_string) {

        //  String input_date_string="2015-02-24";
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = dateformat.parse(input_date_string);
            DateFormat dayFormate = new SimpleDateFormat("EEEE");
            dayFromDate = dayFormate.format(date);
            Log.e("Tag", "----------:: " + dayFromDate);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dayFromDate;
    }


    public static int get_count_of_days(String start_date, String end_date) {


        // SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date Created_convertedDate = null, Expire_CovertedDate = null, todayWithZeroTime = null;
        try {
            Created_convertedDate = dateFormat.parse(start_date);
            Expire_CovertedDate = dateFormat.parse(end_date);

            Date today = new Date();

            todayWithZeroTime = dateFormat.parse(dateFormat.format(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int c_year = 0, c_month = 0, c_day = 0;

        if (Created_convertedDate.after(todayWithZeroTime)) {
            Calendar c_cal = Calendar.getInstance();
            c_cal.setTime(Created_convertedDate);
            c_year = c_cal.get(Calendar.YEAR);
            c_month = c_cal.get(Calendar.MONTH);
            c_day = c_cal.get(Calendar.DAY_OF_MONTH);

        } else {
            Calendar c_cal = Calendar.getInstance();
            c_cal.setTime(todayWithZeroTime);
            c_year = c_cal.get(Calendar.YEAR);
            c_month = c_cal.get(Calendar.MONTH);
            c_day = c_cal.get(Calendar.DAY_OF_MONTH);
        }


    /*Calendar today_cal = Calendar.getInstance();
    int today_year = today_cal.get(Calendar.YEAR);
    int today = today_cal.get(Calendar.MONTH);
    int today_day = today_cal.get(Calendar.DAY_OF_MONTH);
    */

        Calendar e_cal = Calendar.getInstance();
        e_cal.setTime(Expire_CovertedDate);

        int e_year = e_cal.get(Calendar.YEAR);
        int e_month = e_cal.get(Calendar.MONTH);
        int e_day = e_cal.get(Calendar.DAY_OF_MONTH);

        Calendar date1 = Calendar.getInstance();
        Calendar date2 = Calendar.getInstance();

        date1.clear();
        date1.set(c_year, c_month, c_day);
        date2.clear();
        date2.set(e_year, e_month, e_day);

        long diff = date2.getTimeInMillis() - date1.getTimeInMillis();

        int dayCount = (int) diff / (24 * 60 * 60 * 1000);


        return dayCount;
    }

    public static List<String> getWeekDayNames(Date startDate, Date endDate) {
        List<String> days = new ArrayList<String>();

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);

        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        DateFormat dayFormate = new SimpleDateFormat("EEEE");

        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            days.add(dayFormate.format(startCal.getTime()));
            return Collections.unmodifiableList(days);
        }
        // swap values
        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            startCal.setTime(endDate);
            endCal.setTime(startDate);
        }

        do {
            days.add(dayFormate.format(startCal.getTime()));
            startCal.add(Calendar.DAY_OF_MONTH, 1);

        } while (startCal.getTimeInMillis() <= endCal.getTimeInMillis());

        return Collections.unmodifiableList(days);
    }


    //From String to Date

    public static Date StringtoDate(String input) {


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(input);
            System.out.println(date);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return date;
    }


    // From Date to String

    public static String DatetoString(Date input) {

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = new Date();
            datetime = dateformat.format(input);
            System.out.println("Current Date Time : " + datetime);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return datetime;
    }

    //date of birth calucate

    public static int calculateAge(Date birthdate) {
        Calendar birth = Calendar.getInstance();
        birth.setTime(birthdate);
        Calendar today = Calendar.getInstance();
        int yearDifference = today.get(Calendar.YEAR)
                - birth.get(Calendar.YEAR);
        if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH)) {
            yearDifference--;
        } else {
            if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH)
                    && today.get(Calendar.DAY_OF_MONTH) < birth
                    .get(Calendar.DAY_OF_MONTH)) {
                yearDifference--;
            }

        }

        return yearDifference;
    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    public static Bitmap screenShot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static File saveBitmap(Bitmap bm, String fileName) {
        final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Screenshots";
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();
        File file = new File(dir, fileName);
        try {
            FileOutputStream fOut = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 90, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


//    public static String[] coachprofile_load(final String userid, final String prof_id) {
//
//
//        try {
//            retrofit = darkhorsesports.getsporty.coachprofilemodelclassess.ApiClient.getClient();
//            apiCall = retrofit.create(ApiAtheliteCall.class);
//            Call<UserProfileCoachResponse> call = apiCall.getCoachProfile("getUserProfile", userid, prof_id);
//
//            Log.d("URLTAG", call.request().url().toString());
//            call.enqueue(new Callback<UserProfileCoachResponse>() {
//                @Override
//                public void onResponse(Call<UserProfileCoachResponse> call, Response<UserProfileCoachResponse> response) {
//
//
//                    if (response.body().getData() != null) {
//
//                        // profile = (int) response.body().getData().getProfile();
//                        int profileprecentage = (int) response.body().getData().getProfile();
//
//                        String profile_url = response.body().getData().getUser().getUser_image();
//                        profiledata[0] = String.valueOf(profileprecentage);
//                        profiledata[1] = profile_url;
//                    }
//
//
//                }
//
//                @Override
//                public void onFailure(Call<UserProfileCoachResponse> call, Throwable t) {
//
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return profiledata;
//    }

    public static void showImage(Context con, String imageUrl) {
        context = con;
        Dialog dialog = new Dialog(context);
        dialog.getWindow().getAttributes().windowAnimations = R.style.ProfileImageZoomAni; //style id
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.image_zoomview);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        CircleImageView imageView = (CircleImageView) dialog.findViewById(R.id.imagezoom);

        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.with(context).load(imageUrl)
                    .placeholder(R.drawable.account_blank).error(R.drawable.account_blank)
                    .into(imageView);
            dialog.show();

        } else {
            dialog.show();
        }


    }


    public static void showImageUri(Context con, Uri imageUrl) {
        context = con;

        Dialog dialog = new Dialog(context);
        dialog.getWindow().getAttributes().windowAnimations = R.style.ProfileImageZoomAni; //style id
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.image_zoomview);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        CircleImageView imageView = (CircleImageView) dialog.findViewById(R.id.imagezoom);
        imageView.setImageURI(imageUrl);

        dialog.show();


    }


    public static String[] splitInParts(String s, int partLength) {
        int len = s.length();

        // Number of parts
        int nparts = (len + partLength - 1) / partLength;
        String parts[] = new String[nparts];

        // Break into parts
        int offset = 0;
        int i = 0;
        while (i < nparts) {
            parts[i] = s.substring(offset, Math.min(offset + partLength, len));
            offset += partLength;
            i++;
        }

        return parts;
    }
    public static int compareDatesByCalendarMethods(DateFormat df, Date newDate, Date oldDate) {

        //creating calendar instances for date comparision
        Calendar oldCal = Calendar.getInstance();
        Calendar newCal = Calendar.getInstance();

        oldCal.setTime(oldDate);
        newCal.setTime(newDate);

        //how to check if two dates are equals in java using Calendar
        if (oldCal.equals(newCal)) {
            System.out.println(df.format(oldDate) + " and " + df.format(newDate) + " are equal to each other");
            datecompare=1;


        }

        //how to check if one date comes before another using Calendar
        else if (oldCal.before(newCal)) {
            System.out.println(df.format(oldDate) + " comes before " + df.format(newDate));
            datecompare=2;

        }

        //how to check if one date comes after another using Calendar
        else if (oldCal.after(newCal)) {
            System.out.println(df.format(oldDate) + " comes after " + df.format(newDate));

            datecompare=3;
        }
        return datecompare;
    }
    public static boolean isDateInBetweenIncludingEndPoints(final Date min, final Date max, final Date date){
        return !(date.before(min) || date.after(max))|| (date.equals(min) || date.equals(max));
    }
}
