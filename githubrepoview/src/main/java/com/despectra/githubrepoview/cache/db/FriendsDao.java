package com.despectra.githubrepoview.cache.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.despectra.githubrepoview.models.realm.User;
import com.despectra.githubrepoview.sqlite.FriendsTable;

/**
 * Concrete DatabaseDao implementation for friends table
 */
public class FriendsDao extends DatabaseDao<User> {

    public FriendsDao(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return FriendsTable.NAME;
    }

    @Override
    protected String[] getTableColumns() {
        return FriendsTable.ALL_COLUMNS;
    }

    @Override
    protected User getItemFromCursor(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(cursor.getColumnIndexOrThrow(FriendsTable.COLUMN_ID)));
        user.setLogin(cursor.getString(cursor.getColumnIndexOrThrow(FriendsTable.COLUMN_LOGIN)));
        user.setName(cursor.getString(cursor.getColumnIndexOrThrow(FriendsTable.COLUMN_NAME)));
        user.setAvatarUrl(cursor.getString(cursor.getColumnIndexOrThrow(FriendsTable.COLUMN_AVATAR_URL)));
        user.setCompany(cursor.getString(cursor.getColumnIndexOrThrow(FriendsTable.COLUMN_COMPANY)));
        user.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(FriendsTable.COLUMN_LOCATION)));
        user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(FriendsTable.COLUMN_EMAIL)));
        return user;
    }

    @Override
    protected void bindToInsert(User item, SQLiteStatement insertStmt) {
        insertStmt.bindLong(1, item.getId());
        insertStmt.bindString(2, item.getLogin());
        insertStmt.bindString(3, item.getName());
        insertStmt.bindString(4, item.getCompany());
        insertStmt.bindString(5, item.getLocation());
        insertStmt.bindString(6, item.getAvatarUrl());
        insertStmt.bindString(7, item.getEmail());
    }

    @Override
    protected void bindToUpdate(User oldItem, User newItem, SQLiteStatement updateStmt) {
        updateStmt.bindString(1, newItem.getLogin());
        updateStmt.bindString(2, newItem.getName());
        updateStmt.bindString(3, newItem.getCompany());
        updateStmt.bindString(4, newItem.getLocation());
        updateStmt.bindString(5, newItem.getAvatarUrl());
        updateStmt.bindString(6, newItem.getEmail());
        updateStmt.bindLong(7, oldItem.getId());
    }

    @Override
    protected void bindToDelete(User item, SQLiteStatement deleteStmt) {
        deleteStmt.bindLong(1, item.getId());
    }

    @Override
    protected String getInsertSql() {
        return FriendsTable.SQL_INSERT;
    }

    @Override
    protected String getUpdateSql() {
        return FriendsTable.SQL_UPDATE;
    }

    @Override
    protected String getDeleteSql() {
        return FriendsTable.SQL_DELETE;
    }
}
