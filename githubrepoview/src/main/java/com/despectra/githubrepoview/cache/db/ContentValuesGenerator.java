package com.despectra.githubrepoview.cache.db;

import android.content.ContentValues;

import com.despectra.githubrepoview.models.realm.Branch;
import com.despectra.githubrepoview.models.realm.Repo;
import com.despectra.githubrepoview.models.realm.User;
import com.despectra.githubrepoview.sqlite.BranchesTable;
import com.despectra.githubrepoview.sqlite.FriendsTable;
import com.despectra.githubrepoview.sqlite.ReposTable;

/**
 * Class for generating content values by model type
 */
public class ContentValuesGenerator {

    public static ContentValues generate(Object item) {
        if(item instanceof User) {
            return generateForUser((User) item);
        }
        if(item instanceof Repo) {
            return generateForRepo((Repo) item);
        }
        if(item instanceof Branch) {
            return generateForBranch((Branch) item);
        }
        throw new ClassCastException("Only model classes are allowed here");
    }

    private static ContentValues generateForUser(User user) {
        ContentValues values = new ContentValues();
        if(user.getId() != 0) {
            values.put(FriendsTable.COLUMN_ID, user.getId());
        }
        values.put(FriendsTable.COLUMN_LOGIN, user.getLogin());
        values.put(FriendsTable.COLUMN_NAME, user.getName());
        values.put(FriendsTable.COLUMN_AVATAR_URL, user.getAvatarUrl());
        values.put(FriendsTable.COLUMN_COMPANY, user.getCompany());
        values.put(FriendsTable.COLUMN_LOCATION, user.getLocation());
        values.put(FriendsTable.COLUMN_EMAIL, user.getEmail());
        return values;
    }

    private static ContentValues generateForRepo(Repo repo) {
        ContentValues values = new ContentValues();
        if(repo.getId() != 0) {
            values.put(ReposTable.COLUMN_ID, repo.getId());
        }
        values.put(ReposTable.COLUMN_OWNER_ID, repo.getOwnerId());
        values.put(ReposTable.COLUMN_NAME, repo.getName());
        values.put(ReposTable.COLUMN_DESCRIPTION, repo.getDescription());
        values.put(ReposTable.COLUMN_FORKS_COUNT, repo.getForksCount());
        values.put(ReposTable.COLUMN_STARS_COUNT, repo.getStargazersCount());
        values.put(ReposTable.COLUMN_WATCHERS_COUNT, repo.getWatchersCount());
        return values;
    }

    private static ContentValues generateForBranch(Branch branch) {
        ContentValues values = new ContentValues();
        if(branch.getId() != 0) {
            values.put(BranchesTable.COLUMN_ID, branch.getId());
        }
        values.put(BranchesTable.COLUMN_REPO_ID, branch.getRepoId());
        values.put(BranchesTable.COLUMN_NAME, branch.getName());
        return values;
    }

}
