package main.darkhorse.com.getsportyassisment.performance;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by kumargaurav on 28/03/17.
 */

public class PerformanceDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME = "GS_DB";
    public static final String TABLE_NAME = "performance";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ROW_ID = "rowid";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_STUDENT_ID = "student_id";
    public static final String COLUMN_MODULE = "module";
    public static final String COLUMN_SUBMODULE = "sub_module";
    public static final String COLUMN_QUESTION = "question";
    public static final String COLUMN_ANSWER = "answer";
    public static final int VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_NAME + "( " + COLUMN_ID
            + " integer primary key autoincrement, "+COLUMN_STUDENT_ID+" integer, " + COLUMN_MODULE
            + " text, "+COLUMN_SUBMODULE+" text, "+COLUMN_QUESTION+" text, "+COLUMN_ANSWER+" integer, "+COLUMN_ROW_ID+" integer, "
            + COLUMN_STATUS+" integer )";

    public PerformanceDatabase(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        Log.e("Tag"," Create db "+DATABASE_CREATE);
        sqLiteDatabase.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }



}
