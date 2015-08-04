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

    public static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_OWNER_ID,
            COLUMN_NAME,
            COLUMN_DESCRIPTION,
            COLUMN_FORKS_COUNT,
            COLUMN_STARS_COUNT,
            COLUMN_WATCHERS_COUNT
    };

    public static final String SQL_INSERT = "insert into repos (_id, ownerId, name, description, forks_count, stars_count, watchers_count) values(?, ?, ?, ?, ?, ?, ?)";
    public static final String SQL_UPDATE = "update repos set ownerId = ?, name = ?, description = ?, forks_count = ?, stars_count = ?, watchers_count = ? where _id = ?";
    public static final String SQL_DELETE = "delete from repos where _id = ?";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
