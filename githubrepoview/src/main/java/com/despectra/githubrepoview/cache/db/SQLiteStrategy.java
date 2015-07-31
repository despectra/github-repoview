package com.despectra.githubrepoview.cache.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.despectra.githubrepoview.sqlite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.realm.RealmObject;

/**
 * SQLite-specific implementation of DbStrategy
 */
public class SQLiteStrategy implements DbStrategy {

    SQLiteDatabase mDatabase;

    @Override
    public List getByColumnsValues(Class<?> modelClass, String[] columns, Object[] values) {
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();

        String table = TableNameGenerator.generate(modelClass);
        builder.setTables(table);
        String[] whereArgs = null;
        if(columns != null && values != null) {
            for (String column : columns) {
                builder.appendWhere(column + " = ?");
            }
            whereArgs = new String[values.length];
            for(int i = 0; i < values.length; i++) {
                if(values[i] instanceof String) {
                    whereArgs[i] = (String) values[i];
                } else if(values[i] instanceof Long) {
                    whereArgs[i] = String.valueOf(values[i]);
                }
            }
        }

        String query = builder.buildQuery(null, null, null, null, null, null);
        Log.e("app", "SQLite get query: " + query);
        Cursor resultCursor = mDatabase.rawQuery(query, whereArgs);

        resultCursor.moveToFirst();
        List<Object> items = new ArrayList<>();
        ItemFromCursorGenerator generator = new ItemFromCursorGenerator(modelClass);
        while (!resultCursor.isAfterLast()) {
            Object item = generator.generate(resultCursor);
            items.add(item);
            resultCursor.moveToNext();
        }
        resultCursor.close();
        return items;
    }

    @Override
    public void open(Context context) {
        SQLiteOpenHelper helper = new DatabaseHelper(context);
        mDatabase = helper.getWritableDatabase();
    }

    @Override
    public void beginTransaction() {
        mDatabase.beginTransaction();
    }

    @Override
    public void create(Object item) {
        String table = TableNameGenerator.generate(item.getClass());
        ContentValues values = ContentValuesGenerator.generate(item);
        long id = mDatabase.insertOrThrow(table, null, values);
        Log.e("app", "item " + id + " inserted");
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
        String table = TableNameGenerator.generate(oldItem.getClass());
        ContentValues values = ContentValuesGenerator.generate(newItem);
        String where = UniqueItemWhereGenerator.generateWhere(oldItem);
        String[] whereArgs = UniqueItemWhereGenerator.generateWhereArgs(oldItem);
        int count = mDatabase.update(table, values, where, whereArgs);
        Log.e("app", count + " items updated");
    }

    @Override
    public void delete(Object item) {
        String table = TableNameGenerator.generate(item.getClass());
        String where = UniqueItemWhereGenerator.generateWhere(item);
        String[] whereArgs = UniqueItemWhereGenerator.generateWhereArgs(item);
        int count = mDatabase.delete(table, where, whereArgs);
        Log.e("app", count + " items deleted");
    }

    @Override
    public void commitTransaction() {
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    @Override
    public void rollbackTransaction() {
        mDatabase.endTransaction();
    }

    @Override
    public void close() {
        mDatabase.close();
    }
}
