package com.despectra.githubrepoview.cache.db;

import android.content.Context;

import com.despectra.githubrepoview.models.realm.Branch;
import com.despectra.githubrepoview.models.realm.Repo;
import com.despectra.githubrepoview.models.realm.User;
import com.despectra.githubrepoview.sqlite.BranchesTable;
import com.despectra.githubrepoview.sqlite.ReposTable;

import java.util.List;

/**
 * Class for obtaining lists of different items from local database
 */
public class Cache {

    private DbStrategy mDbStrategy;
    private Context mContext;

    public Cache(Context context) {
        mDbStrategy = DbUtils.getDefaultStrategy();
        mContext = context;
    }

    public List<User> getAllFriends() {
        return mDbStrategy.getByColumnsValues(User.class, null, null);
    }

    public List<Repo> getReposByOwnerId(long ownerId) {
        return mDbStrategy.getByColumnsValues(Repo.class, new String[] { ReposTable.COLUMN_OWNER_ID }, new Object[]{ ownerId });
    }

    public List<Branch> getBranchesByRepoId(long repoId) {
        return mDbStrategy.getByColumnsValues(Branch.class, new String[] { BranchesTable.COLUMN_REPO_ID }, new Object[]{ repoId });
    }

    public void openCache() {
        mDbStrategy.open(mContext);
    }

    public void closeCache() {
        mDbStrategy.close();
    }

}
