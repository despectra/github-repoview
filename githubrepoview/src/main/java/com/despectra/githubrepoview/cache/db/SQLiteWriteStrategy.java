package com.despectra.githubrepoview.cache.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.despectra.githubrepoview.sqlite.DatabaseHelper;

/**
 * SQLite-specific implementation of DbWriteStrategy
 */
public class SQLiteWriteStrategy implements DbWriteStrategy {

    SQLiteDatabase mDatabase;

    @Override
    public void beginTransaction(Context context) {
        SQLiteOpenHelper helper = new DatabaseHelper(context);
        mDatabase = helper.getWritableDatabase();
        mDatabase.beginTransaction();
    }

    @Override
    public void create(Object item) {
        String table = TableNameGenerator.generate(item);
        ContentValues values = ContentValuesGenerator.generate(item);
        mDatabase.insert(table, null, values);
    }

    /**
     *
     * @param type model type
     * @return 0 because sqlite supports autoincrement
     */
    @Override
    public long getNextItemId(Class type) {
        return 0;
    }

    @Override
    public void update(Object oldItem, Object newItem) {
        String table = TableNameGenerator.generate(oldItem);
        ContentValues values = ContentValuesGenerator.generate(newItem);
        String where = UniqueItemWhereGenerator.generateWhere(oldItem);
        String[] whereArgs = UniqueItemWhereGenerator.generateWhereArgs(oldItem);
        mDatabase.update(table, values, where, whereArgs);
    }

    @Override
    public void delete(Object item) {
        String table = TableNameGenerator.generate(item);
        String where = UniqueItemWhereGenerator.generateWhere(item);
        String[] whereArgs = UniqueItemWhereGenerator.generateWhereArgs(item);
        mDatabase.delete(table, where, whereArgs);
    }

    @Override
    public void commitTransaction() {
        mDatabase.endTransaction();
        mDatabase.close();
    }

}
