package com.example.project.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final int SCHEMA = 1;
    private static final String DATABASE_NAME = "TravellerProject";
    private static final String USER_TABLE = "USER";
    private static final String TRIP_TABLE = "TRIP";

    private static DBHelper instance = null;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE + " (                    "
                + "IDUSER integer primary key autoincrement not null,"
                + "LOGIN text not null,"
                + "EMAIL text not null,"
                + "PASSWORD text not null);"
        );

        db.execSQL("create table " + TRIP_TABLE + "("
                + " IDTRIP integer primary key autoincrement not null, "
                + " START text not null,"
                + " FINISH text not null, "
                + " Data text not null,"
                + " CAPACITY integer not null,"
                + " PRICE integer not null);"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
