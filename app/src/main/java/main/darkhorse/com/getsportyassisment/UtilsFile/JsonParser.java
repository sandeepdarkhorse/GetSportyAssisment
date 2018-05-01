package main.darkhorse.com.getsportyassisment.UtilsFile;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by kumargaurav on 23/02/17.
 */

public class JsonParser {

    public static String getString(JSONObject jsonObject, String key)
    {
        String value = "";
        try{
            value = jsonObject.getString(key);
        }
        catch (Exception e){
            Log.e("Tag",e.toString());
        }
        return value;
    }

    public static int getInt(JSONObject jsonObject, String key)
    {
        int value = 0;
        try{
            value = jsonObject.getInt(key);
        }
        catch (Exception e){
            Log.e("Tag",e.toString());
        }
        return value;
    }

    public static long getLong(JSONObject jsonObject, String key)
    {
        long value = 0;
        try{
            value = jsonObject.getLong(key);
        }
        catch (Exception e){
            Log.e("Tag",e.toString());
        }
        return value;
    }

    public static boolean getBoolean(JSONObject jsonObject, String key)
    {
        boolean value = false;
        try{
            value = jsonObject.getBoolean(key);
        }
        catch (Exception e){
            Log.e("Tag",e.toString());
        }
        return value;
    }

//    public static JSONObject getJsonObject(JSONObject jsonObject, String key)
//    {
//        JSONObject value = null;
//        try{
//            value = jsonObject.getJSONObject(key);
//        }
//        catch (Exception e){
//            Log.e("Tag",e.toString());
//        }
//        return value;
//    }
}
