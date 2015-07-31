package com.despectra.githubrepoview.cache.db;

import com.despectra.githubrepoview.models.realm.Branch;
import com.despectra.githubrepoview.models.realm.Repo;
import com.despectra.githubrepoview.models.realm.User;
import com.despectra.githubrepoview.sqlite.BranchesTable;
import com.despectra.githubrepoview.sqlite.FriendsTable;
import com.despectra.githubrepoview.sqlite.ReposTable;

/**
 * Class for generating SQLite database table name by model type
 */
public class TableNameGenerator {

    public static String generate(Class type) {
        if(type == User.class) {
            return FriendsTable.NAME;
        }
        if(type == Repo.class) {
            return ReposTable.NAME;
        }
        if(type == Branch.class) {
            return BranchesTable.NAME;
        }
        throw new ClassCastException("Only model classes are allowed here");
    }

}
