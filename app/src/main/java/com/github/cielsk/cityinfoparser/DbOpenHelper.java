package com.github.cielsk.cityinfoparser;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 *
 */
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "DbOpenHelper";

    public static final String DB_NAME = "cities.db";

    public static final int DB_VERSION = 1;

    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 由省、市、县一级一级建立表格
        db.execSQL(Province.CREATE_TABLE);
        db.execSQL(City.CREATE_TABLE);
        db.execSQL(County.CREATE_TABLE);

        Log.d(TAG, "Database created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
