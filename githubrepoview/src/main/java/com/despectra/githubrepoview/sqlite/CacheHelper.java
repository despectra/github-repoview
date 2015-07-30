package com.despectra.githubrepoview.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 *
 */
public class CacheHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "cache.db";
    public static final int DATABASE_VERSION = 1;

    public CacheHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        FriendsTable.onCreate(db);
        ReposTable.onCreate(db);
        BranchesTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        FriendsTable.onUpgrade(db, oldVersion, newVersion);
        ReposTable.onUpgrade(db, oldVersion, newVersion);
        BranchesTable.onUpgrade(db, oldVersion, newVersion);
    }
}
