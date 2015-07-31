package com.despectra.githubrepoview.cache.db;

import android.database.Cursor;

import com.despectra.githubrepoview.models.realm.Branch;
import com.despectra.githubrepoview.models.realm.Repo;
import com.despectra.githubrepoview.models.realm.User;
import com.despectra.githubrepoview.sqlite.BranchesTable;
import com.despectra.githubrepoview.sqlite.FriendsTable;
import com.despectra.githubrepoview.sqlite.ReposTable;

/**
 * Class for producing model objects from raw cursor data
 * Based on
 */
public class ItemFromCursorGenerator {

    private Class<?> mItemType;

    public ItemFromCursorGenerator(Class<?> itemType) {
        mItemType = itemType;
    }

    public Object generate(Cursor cursor) {
        if(mItemType == User.class) {
            return generateUser(cursor);
        }
        if(mItemType == Repo.class) {
            return generateRepo(cursor);
        }
        if(mItemType == Branch.class) {
            return generateBranch(cursor);
        }
        throw new ClassCastException("Only model classes are allowed here");
    }

    private User generateUser(Cursor cursor) {
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

    private Repo generateRepo(Cursor cursor) {
        Repo repo = new Repo();
        repo.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ReposTable.COLUMN_ID)));
        repo.setOwnerId(cursor.getLong(cursor.getColumnIndexOrThrow(ReposTable.COLUMN_OWNER_ID)));
        repo.setName(cursor.getString(cursor.getColumnIndexOrThrow(ReposTable.COLUMN_NAME)));
        repo.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(ReposTable.COLUMN_DESCRIPTION)));
        repo.setForksCount(cursor.getInt(cursor.getColumnIndexOrThrow(ReposTable.COLUMN_FORKS_COUNT)));
        repo.setStargazersCount(cursor.getInt(cursor.getColumnIndexOrThrow(ReposTable.COLUMN_STARS_COUNT)));
        repo.setWatchersCount(cursor.getInt(cursor.getColumnIndexOrThrow(ReposTable.COLUMN_WATCHERS_COUNT)));
        return repo;
    }

    private Branch generateBranch(Cursor cursor) {
        Branch branch = new Branch();
        branch.setId(cursor.getLong(cursor.getColumnIndexOrThrow(BranchesTable.COLUMN_ID)));
        branch.setRepoId(cursor.getLong(cursor.getColumnIndexOrThrow(BranchesTable.COLUMN_REPO_ID)));
        branch.setName(cursor.getString(cursor.getColumnIndexOrThrow(BranchesTable.COLUMN_NAME)));
        return branch;
    }

}
