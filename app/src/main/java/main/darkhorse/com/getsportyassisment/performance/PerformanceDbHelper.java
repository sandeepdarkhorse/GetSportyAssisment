package main.darkhorse.com.getsportyassisment.performance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


import main.darkhorse.com.getsportyassisment.UtilsFile.JsonKeys;
import main.darkhorse.com.getsportyassisment.UtilsFile.JsonParser;

import static main.darkhorse.com.getsportyassisment.performance.PerformanceDatabase.COLUMN_ANSWER;
import static main.darkhorse.com.getsportyassisment.performance.PerformanceDatabase.COLUMN_ID;
import static main.darkhorse.com.getsportyassisment.performance.PerformanceDatabase.COLUMN_MODULE;
import static main.darkhorse.com.getsportyassisment.performance.PerformanceDatabase.COLUMN_QUESTION;
import static main.darkhorse.com.getsportyassisment.performance.PerformanceDatabase.COLUMN_ROW_ID;
import static main.darkhorse.com.getsportyassisment.performance.PerformanceDatabase.COLUMN_STATUS;
import static main.darkhorse.com.getsportyassisment.performance.PerformanceDatabase.COLUMN_STUDENT_ID;
import static main.darkhorse.com.getsportyassisment.performance.PerformanceDatabase.COLUMN_SUBMODULE;
import static main.darkhorse.com.getsportyassisment.performance.PerformanceDatabase.TABLE_NAME;

/**
 * Created by  on 29/03/17.
 */

public class PerformanceDbHelper {

    private PerformanceDatabase performanceDatabase;

    public PerformanceDbHelper(Context context) {

        performanceDatabase = new PerformanceDatabase(context);
    }

    public void saveAllQuestions(String data, int studentId, int status){
        try{

            SQLiteDatabase sqLiteDatabase = performanceDatabase.getWritableDatabase();

            JSONObject jsonObjectPerformance = new JSONObject(data);

        JSONObject jsonObjectQuestion = jsonObjectPerformance.getJSONObject(JsonKeys.KEY_QUESTION);
            String performanceId = JsonParser.getString(jsonObjectPerformance, JsonKeys.PERFORMANCE_ID);

        Iterator<String> iter = jsonObjectQuestion.keys();

        while (iter.hasNext()) {

            String module = iter.next();

            JSONObject submoduleJsonObject = jsonObjectQuestion.getJSONObject(module);
            Iterator<String> iter1 = submoduleJsonObject.keys();

            while (iter1.hasNext()){

                String submodule = iter1.next();

                JSONArray jsonArray = submoduleJsonObject.getJSONArray(submodule);

                for (int i=0;i<jsonArray.length();i++){

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(COLUMN_STUDENT_ID,studentId);
                    contentValues.put(COLUMN_MODULE,module);
                    contentValues.put(COLUMN_SUBMODULE,submodule);
                    contentValues.put(COLUMN_QUESTION,jsonArray.getString(i));
                    contentValues.put(COLUMN_ANSWER,0);
                    contentValues.put(COLUMN_STATUS, status);
                    contentValues.put(COLUMN_ROW_ID, performanceId);

                    sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

                }

            }
        }
            sqLiteDatabase.close();
    } catch (JSONException e) {
        e.printStackTrace();
    }

    }

//    public void saveQuesAns(String data){
//        try{
//
//            SQLiteDatabase sqLiteDatabase = performanceDatabase.getWritableDatabase();
//
//            JSONObject jsonObjectPerformance = new JSONObject(data);
//
//            JSONObject jsonObjectQuestion = jsonObjectPerformance.getJSONObject(JsonKeys.KEY_QUESTION);
//
//            Iterator<String> iter = jsonObjectQuestion.keys();
//
//            while (iter.hasNext()) {
//
//                String module = iter.next();
//
//                JSONObject submoduleJsonObject = jsonObjectQuestion.getJSONObject(module);
//                Iterator<String> iter1 = submoduleJsonObject.keys();
//
//                while (iter1.hasNext()){
//
//                    String submodule = iter1.next();
//
//                    JSONArray jsonArray = submoduleJsonObject.getJSONArray(submodule);
//
//                    for (int i=0;i<jsonArray.length();i++){
//
//                        Iterator<String> questions = jsonArray.getJSONObject(i).keys();
//
//                        while (questions.hasNext()){
//                            String question = questions.next();
//
//                            ContentValues contentValues = new ContentValues();
//                            contentValues.put(COLUMN_ID,);
//                            contentValues.put(COLUMN_MODULE,module);
//                            contentValues.put(COLUMN_SUBMODULE,submodule);
//                            contentValues.put(COLUMN_QUESTION,question);
//                            contentValues.put(COLUMN_ANSWER, JsonParser.getInt(jsonArray.getJSONObject(i),question));
//
//                            sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
//                        }
//                    }
//
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//    }

    public String getAllData(int studentId){
        JSONObject jsonObject = new JSONObject();
        try {
            JSONArray jsonArray = new JSONArray();

            SQLiteDatabase sqLiteDatabase = performanceDatabase.getReadableDatabase();

            Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_STUDENT_ID,COLUMN_MODULE, COLUMN_SUBMODULE, COLUMN_QUESTION,
                    COLUMN_ANSWER}, COLUMN_STUDENT_ID+" = ? ", new String[]{String.valueOf(studentId)}, null, null, null);

            cursor.moveToFirst();
            do {
                JSONObject jsonObject1 = new JSONObject();

                jsonObject1.put(COLUMN_ID, cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                jsonObject1.put(COLUMN_STUDENT_ID, cursor.getInt(cursor.getColumnIndex(COLUMN_STUDENT_ID)));
                jsonObject1.put(COLUMN_MODULE, cursor.getString(cursor.getColumnIndex(COLUMN_MODULE)));
                jsonObject1.put(COLUMN_SUBMODULE, cursor.getString(cursor.getColumnIndex(COLUMN_SUBMODULE)));
                jsonObject1.put(COLUMN_QUESTION, cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION)));
                jsonObject1.put(COLUMN_ANSWER, cursor.getInt(cursor.getColumnIndex(COLUMN_ANSWER)));

                jsonArray.put(jsonObject1);


            }while (cursor.moveToNext());

            jsonObject.put(JsonKeys.KEY_DATA,jsonArray);
            sqLiteDatabase.close();

        }catch (Exception e){

        }
        return jsonObject.toString();
    }

    public ArrayList<String> getAllModules(int studentId){
        ArrayList<String> moduleArrayList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = performanceDatabase.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(true,TABLE_NAME,new String[]{COLUMN_MODULE},COLUMN_STUDENT_ID+" = ? ", new String[]{String.valueOf(studentId)}, null,null,null,null);

        cursor.moveToFirst();
        do{
            moduleArrayList.add(cursor.getString(cursor.getColumnIndex(COLUMN_MODULE)));
        }while (cursor.moveToNext());

        sqLiteDatabase.close();


        return moduleArrayList;

    }

    public ArrayList<String> getAllSubModules(String module, int studentId){
        ArrayList<String> submoduleArrayList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = performanceDatabase.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(true,TABLE_NAME,new String[]{COLUMN_SUBMODULE},COLUMN_MODULE+" = ? AND "+COLUMN_STUDENT_ID+" = ? ", new String[]{module,String.valueOf(studentId)}, null,null,null,null);

        cursor.moveToFirst();
        if(cursor.getCount() > 0)
        {
            do{

                submoduleArrayList.add(cursor.getString(cursor.getColumnIndex(COLUMN_SUBMODULE)));
            }while (cursor.moveToNext());
        }


        sqLiteDatabase.close();


        return submoduleArrayList;

    }

    public ArrayList<PerformanceAssessment> getQuestions(String module, String submodule, int studentId){

        ArrayList<PerformanceAssessment> arrayList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = performanceDatabase.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{COLUMN_ID,COLUMN_STUDENT_ID ,COLUMN_MODULE, COLUMN_SUBMODULE, COLUMN_QUESTION,
                COLUMN_ANSWER}, COLUMN_MODULE+" = ? AND "+COLUMN_SUBMODULE+" = ? AND "+COLUMN_STUDENT_ID+" = ? ", new String[]{module, submodule, String.valueOf(studentId)}, null, null, null);

        cursor.moveToFirst();
        if(cursor.getCount() < 0) {

            do {
                PerformanceAssessment performanceAssessment = new PerformanceAssessment();
                performanceAssessment.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                performanceAssessment.setStudentId(cursor.getInt(cursor.getColumnIndex(COLUMN_STUDENT_ID)));
                performanceAssessment.setModule(cursor.getString(cursor.getColumnIndex(COLUMN_MODULE)));
                performanceAssessment.setSubmodule(cursor.getString(cursor.getColumnIndex(COLUMN_SUBMODULE)));
                performanceAssessment.setQuestion(cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION)));
                performanceAssessment.setAnswer(cursor.getInt(cursor.getColumnIndex(COLUMN_ANSWER)));

                arrayList.add(performanceAssessment);
            } while (cursor.moveToNext());
        }

        sqLiteDatabase.close();


        return arrayList;
    }

    public void saveAnswer(PerformanceAssessment performanceAssessment){

        ContentValues contentValues = new ContentValues();
//        contentValues.put(COLUMN_ID,performanceAssessment.getId());
//        contentValues.put(COLUMN_MODULE,performanceAssessment.getId());
//        contentValues.put(COLUMN_SUBMODULE,performanceAssessment.getId());
//        contentValues.put(COLUMN_QUESTION,performanceAssessment.getId());
        contentValues.put(COLUMN_ANSWER,performanceAssessment.getAnswer());


        SQLiteDatabase sqLiteDatabase = performanceDatabase.getWritableDatabase();
        sqLiteDatabase.update(TABLE_NAME,contentValues,COLUMN_ID+" = ?", new String[]{String.valueOf(performanceAssessment.getId())});

        sqLiteDatabase.close();


    }

    public int getCount(int studentId){

        SQLiteDatabase sqLiteDatabase = performanceDatabase.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{COLUMN_STUDENT_ID }, COLUMN_STUDENT_ID+" = ? ", new String[]{String.valueOf(studentId)}, null, null, null);

        return cursor.getCount();

    }

    public int getRowId(int studentId){

        SQLiteDatabase sqLiteDatabase = performanceDatabase.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.query(true,TABLE_NAME, new String[]{COLUMN_STUDENT_ID, COLUMN_ROW_ID }, COLUMN_STUDENT_ID+" = ? ", new String[]{String.valueOf(studentId)}, null, null, null,null);

        cursor.moveToFirst();

        if (cursor.getCount()>0){

            return cursor.getInt(cursor.getColumnIndex(COLUMN_ROW_ID));
        }
        else{
            sqLiteDatabase.close();

            return 0;
        }

    }

    public ArrayList<PerformanceAssessment> getAllQuestions(int studentId){

        ArrayList<PerformanceAssessment> arrayList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = performanceDatabase.getReadableDatabase();

            Cursor cursor = sqLiteDatabase.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_STUDENT_ID,COLUMN_MODULE, COLUMN_SUBMODULE, COLUMN_QUESTION,
                    COLUMN_ANSWER}, COLUMN_STUDENT_ID+" = ? ", new String[]{String.valueOf(studentId)}, null, null, null);

            cursor.moveToFirst();
            do {

                PerformanceAssessment performanceAssessment = new PerformanceAssessment();
                performanceAssessment.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                performanceAssessment.setStudentId(cursor.getInt(cursor.getColumnIndex(COLUMN_STUDENT_ID)));
                performanceAssessment.setModule(cursor.getString(cursor.getColumnIndex(COLUMN_MODULE)));
                performanceAssessment.setSubmodule(cursor.getString(cursor.getColumnIndex(COLUMN_SUBMODULE)));
                performanceAssessment.setQuestion(cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION)));
                performanceAssessment.setAnswer(cursor.getInt(cursor.getColumnIndex(COLUMN_ANSWER)));

                arrayList.add(performanceAssessment);



            }while (cursor.moveToNext());

        sqLiteDatabase.close();

        return arrayList;
    }

    public void publish(int studentId){
        SQLiteDatabase sqLiteDatabase = performanceDatabase.getWritableDatabase();

        int rows = sqLiteDatabase.delete(TABLE_NAME,COLUMN_STUDENT_ID+" = ? ", new String[]{String.valueOf(studentId)});
        Log.e("Tag", ""+rows);
    }
}
