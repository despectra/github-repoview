package com.despectra.githubrepoview.cache.db;

import com.despectra.githubrepoview.models.realm.Branch;
import com.despectra.githubrepoview.models.realm.Repo;
import com.despectra.githubrepoview.models.realm.User;
import com.despectra.githubrepoview.sqlite.BranchesTable;
import com.despectra.githubrepoview.sqlite.FriendsTable;
import com.despectra.githubrepoview.sqlite.ReposTable;

/**
 * Class for generating unique column name of SQLite table by model type
 */
public class UniqueItemWhereGenerator {

    public static String generateWhere(Object item) {
        if(item instanceof User) {
            return FriendsTable.COLUMN_ID + " = ?";
        }
        if(item instanceof Repo) {
            return ReposTable.COLUMN_ID + " = ?";
        }
        if(item instanceof Branch) {
            return BranchesTable.COLUMN_ID + " = ? and " + BranchesTable.COLUMN_NAME + " = ?";
        }
        throw new ClassCastException("Only model classes are allowed here");
    }

    public static String[] generateWhereArgs(Object item) {
        if(item instanceof User) {
            User user = (User) item;
            return new String[] { String.valueOf(user.getId()) };
        }
        if(item instanceof Repo) {
            Repo repo = (Repo) item;
            return new String[] { String.valueOf(repo.getId()) };
        }
        if(item instanceof Branch) {
            Branch branch = (Branch) item;
            return new String[] { String.valueOf(branch.getId()), branch.getName() };
        }
        throw new ClassCastException("Only model classes are allowed here");
    }
}
