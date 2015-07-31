package com.despectra.githubrepoview.sqlite;

import android.database.sqlite.SQLiteDatabase;

/**
 *
 */
public class ReposTable {
    public static final String NAME = "repos";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_OWNER_ID = "ownerId";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_FORKS_COUNT = "forks_count";
    public static final String COLUMN_STARS_COUNT = "stars_count";
    public static final String COLUMN_WATCHERS_COUNT = "watchers_count";

    public static final String SQL_CREATE = "create table " + NAME + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_OWNER_ID + " integer not null references " + FriendsTable.NAME + "(" + FriendsTable.COLUMN_ID + ") on delete cascade, "
            + COLUMN_NAME + " text, "
            + COLUMN_DESCRIPTION + " text, "
            + COLUMN_FORKS_COUNT + " integer, "
            + COLUMN_STARS_COUNT + " integer, "
            + COLUMN_WATCHERS_COUNT + " integer" +
            ");";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
