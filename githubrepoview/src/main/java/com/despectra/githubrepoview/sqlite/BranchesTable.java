package com.despectra.githubrepoview.sqlite;

import android.database.sqlite.SQLiteDatabase;

/**
 *
 */
public class BranchesTable {

    public static final String NAME = "branches";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_REPO_ID = "repoId";
    public static final String COLUMN_NAME = "name";

    public static final String SQL_CREATE = "create table " + NAME + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_REPO_ID + " integer not null references " + ReposTable.NAME + "(" + ReposTable.COLUMN_ID + ") on delete cascade, "
            + COLUMN_NAME + " text" +
            ");";

    public static final String[] ALL_COLUMNS = {
            COLUMN_ID,
            COLUMN_REPO_ID,
            COLUMN_NAME
    };

    public static final String SQL_INSERT = "insert into branches (repoId, name) values(?, ?)";
    public static final String SQL_UPDATE = "update branches set name = ? where repoId = ? and name = ?";
    public static final String SQL_DELETE = "delete from branches where _id = ?";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
