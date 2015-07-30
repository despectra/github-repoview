package com.despectra.githubrepoview.sqlite;

import android.database.sqlite.SQLiteDatabase;

/**
 * Class describing friends sqlite table
 */
public class FriendsTable {

    public static final String NAME = "friends";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_COMPANY = "company";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_AVATAR_URL = "avatar_url";
    public static final String COLUMN_EMAIL = "email";

    public static final String SQL_CREATE = "create table " + NAME + "("
                    + COLUMN_ID + " integer primary key autoincrement, "
                    + COLUMN_LOGIN + " text, "
                    + COLUMN_NAME + " text, "
                    + COLUMN_EMAIL + " text, "
                    + COLUMN_COMPANY + " text, "
                    + COLUMN_LOCATION + " text, "
                    + COLUMN_AVATAR_URL + " text" +
            ");";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
