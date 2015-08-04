package com.despectra.githubrepoview.cache.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import com.despectra.githubrepoview.sqlite.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Class providing crud operations on model items
 * Parametrized by item model type
 */
public abstract class DatabaseDao<T> {

    private final Context mContext;
    private SQLiteDatabase mDatabase;

    /*
    Prepared statements for write operations
     */
    private SQLiteStatement mInsertStatement;
    private SQLiteStatement mUpdateStatement;
    private SQLiteStatement mDeleteStatement;

    public DatabaseDao(Context context) {
        mContext = context;
    }

    /**
     * Open connection to SQLite database and compile statements if it's null
     */
    public void open() {
        SQLiteOpenHelper helper = new DatabaseHelper(mContext);
        mDatabase = helper.getWritableDatabase();
        if(mInsertStatement == null) {
            mInsertStatement = mDatabase.compileStatement(getInsertSql());
        }
        if(mUpdateStatement == null) {
            mUpdateStatement = mDatabase.compileStatement(getUpdateSql());
        }
        if(mDeleteStatement == null) {
            mDeleteStatement = mDatabase.compileStatement(getDeleteSql());
        }
    }

    public void close() {
        mDatabase.close();
    }

    /**
     * Transaction methods
     */

    public void beginTransaction() {
        mDatabase.beginTransaction();
    }

    public void commitTransaction() {
        mDatabase.setTransactionSuccessful();
        mDatabase.endTransaction();
    }

    public void rollbackTransaction() {
        mDatabase.endTransaction();
    }

    /**
     * Retrieve items from database filtered by where conditions
     * @param where columns names
     * @param whereArgs columns values to filter
     * @return list of items obtained from database
     */
    public final List<T> getItems(String[] where, String[] whereArgs) {
        String whereClause = null;
        if(where != null && whereArgs != null) {
            //prepare where clause
            StringBuilder whereBuilder = new StringBuilder();
            for(String whereItem : where) {
                whereBuilder
                        .append(whereItem)
                        .append(" = ")
                        .append("?");
            }
            whereClause = whereBuilder.toString();
        }
        Cursor cursor = mDatabase.query(getTableName(), getTableColumns(), whereClause, whereArgs, null, null, null);
        cursor.moveToFirst();
        List<T> items = new ArrayList<>();
        while(!cursor.isAfterLast()) {
            //convert cursor to list of items
            T item = getItemFromCursor(cursor);
            items.add(item);
            cursor.moveToNext();
        }
        return items;
    }

    /**
     * Perform insert operation
     * @param item item to insert
     */
    public final void create(T item) {
        bindToInsert(item, mInsertStatement);
        mInsertStatement.executeInsert();
        mInsertStatement.clearBindings();
    }

    /**
     * Perform update operation
     * @param oldItem item that is about to update
     * @param newItem item with updated fields values
     */
    public final void update(T oldItem, T newItem) {
        bindToUpdate(oldItem, newItem, mUpdateStatement);
        mUpdateStatement.executeUpdateDelete();
        mUpdateStatement.clearBindings();
    }

    /**
     * Perform delete operation
     * @param item item to delete
     */
    public final void delete(T item) {
        bindToDelete(item, mDeleteStatement);
        mDeleteStatement.executeUpdateDelete();
        mDeleteStatement.clearBindings();
    }

    /**
     * Abstract methods for binding items fields values to SQL statements
     */

    protected abstract void bindToInsert(T item, SQLiteStatement insertStmt);
    protected abstract void bindToUpdate(T oldItem, T newItem, SQLiteStatement updateStmt);
    protected abstract void bindToDelete(T item, SQLiteStatement deleteStmt);

    /**
     * @return SQLite table name
     */
    protected abstract String getTableName();

    /**
     * @return array of all columns names in SQLite table
     */
    protected abstract String[] getTableColumns();

    /**
     * Converts current cursor record to item object
     * @param cursor current cursor
     * @return new item with fields values from cursor
     */
    protected abstract T getItemFromCursor(Cursor cursor);

    /**
     * Abstract methods for obtaining SQL write queries for compiling statements
     * @return sql insert/update/delete query
     */

    protected abstract String getInsertSql();
    protected abstract String getUpdateSql();
    protected abstract String getDeleteSql();
}
