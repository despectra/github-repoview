package com.despectra.githubrepoview.loaders.local;

import android.content.Context;

import com.despectra.githubrepoview.cache.db.DatabaseDao;
import com.despectra.githubrepoview.cache.db.ReposDao;
import com.despectra.githubrepoview.models.Repo;
import com.despectra.githubrepoview.models.User;
import com.despectra.githubrepoview.sqlite.ReposTable;

/**
 * Loader for retrieving user repos list from local database
 */
public class ReposLocalLoader extends LocalLoader<Repo> {

    /**
     * Who's repos should we load?
     */
    private User mOwner;

    public ReposLocalLoader(Context context, User owner) {
        super(context);
        mOwner = owner;
    }


    @Override
    protected String[] getSelectionColumnsValues() {
        return new String[] {
                String.valueOf(mOwner.getId())
        };
    }

    @Override
    protected String[] getSelectionColumns() {
        return new String[] {
                ReposTable.COLUMN_OWNER_ID
        };
    }

    @Override
    protected DatabaseDao<Repo> getDatabaseDao() {
        return new ReposDao(getContext());
    }
}
