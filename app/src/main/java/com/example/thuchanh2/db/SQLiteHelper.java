package com.example.thuchanh2.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "app.db";
    private static final Integer version = 1;

    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE ticket(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT, depart INTEGER, date TEXT," +
                "has_package TEXT, price INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS ticket";
        db.execSQL(sql);
        onCreate(db);
    }
}
