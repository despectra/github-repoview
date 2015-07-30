package com.despectra.githubrepoview.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.despectra.githubrepoview.models.realm.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for performing CRUD operations on users sqlite table
 */
public class FriendsSource {

    private SQLiteDatabase mDatabase;
    private CacheHelper mCacheHelper;

    public static final String[] ALL_COLUMNS = {
            FriendsTable.COLUMN_ID,
            FriendsTable.COLUMN_LOGIN,
            FriendsTable.COLUMN_NAME,
            FriendsTable.COLUMN_COMPANY,
            FriendsTable.COLUMN_LOCATION,
            FriendsTable.COLUMN_EMAIL,
            FriendsTable.COLUMN_AVATAR_URL
    };

    public static final String WHERE_ID_EQUALS = FriendsTable.COLUMN_ID + " = ?";

    public FriendsSource(Context context) {
        mCacheHelper = new CacheHelper(context);
    }

    public List<User> getAllFriends() {
        Cursor resultCursor = mDatabase.query(FriendsTable.NAME, ALL_COLUMNS, null, null, null, null, null);
        List<User> friends = new ArrayList<>();
        resultCursor.moveToFirst();
        while(resultCursor.isAfterLast()) {
            User friend = cursorRowToFriend(resultCursor);
            friends.add(friend);
            resultCursor.moveToNext();
        }
        return friends;
    }

    public long insertFriend(User friend) {
        ContentValues friendValues = friendToContentValues(friend, false);
        long newId = mDatabase.insert(FriendsTable.NAME, null, friendValues);
        friend.setId(newId);
        return newId;
    }

    public int updateFriend(User oldFriend, User updatedFriend) {
        return mDatabase.update(FriendsTable.NAME, friendToContentValues(updatedFriend, false), WHERE_ID_EQUALS, new String[]{ String.valueOf(oldFriend.getId()) });
    }

    public int deleteFriend(User friend) {
        return mDatabase.delete(FriendsTable.NAME, WHERE_ID_EQUALS, new String[]{ String.valueOf(friend.getId()) });
    }

    public void open() {
        mDatabase = mCacheHelper.getWritableDatabase();
    }

    public void close() {
        mDatabase.close();
    }

    private ContentValues friendToContentValues(User friend, boolean putId) {
        ContentValues values = new ContentValues();
        values.put(FriendsTable.COLUMN_ID, friend.getId());
        values.put(FriendsTable.COLUMN_LOGIN, friend.getLogin());
        values.put(FriendsTable.COLUMN_NAME, friend.getName());
        values.put(FriendsTable.COLUMN_AVATAR_URL, friend.getAvatarUrl());
        values.put(FriendsTable.COLUMN_COMPANY, friend.getCompany());
        values.put(FriendsTable.COLUMN_LOCATION, friend.getLocation());
        values.put(FriendsTable.COLUMN_EMAIL, friend.getEmail());
        return values;
    }

    private User cursorRowToFriend(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getLong(0));
        user.setLogin(cursor.getString(cursor.getColumnIndexOrThrow(FriendsTable.COLUMN_LOGIN)));
        user.setName(cursor.getString(cursor.getColumnIndexOrThrow(FriendsTable.COLUMN_NAME)));
        user.setAvatarUrl(cursor.getString(cursor.getColumnIndexOrThrow(FriendsTable.COLUMN_AVATAR_URL)));
        user.setCompany(cursor.getString(cursor.getColumnIndexOrThrow(FriendsTable.COLUMN_COMPANY)));
        user.setLocation(cursor.getString(cursor.getColumnIndexOrThrow(FriendsTable.COLUMN_LOCATION)));
        user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow(FriendsTable.COLUMN_EMAIL)));
        return user;
    }

}
