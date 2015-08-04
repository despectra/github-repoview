package com.despectra.githubrepoview.loaders.local;

import android.content.Context;

import com.despectra.githubrepoview.cache.db.DatabaseDao;
import com.despectra.githubrepoview.cache.db.FriendsDao;
import com.despectra.githubrepoview.models.realm.User;

import java.util.List;

/**
 * Loader for retrieving friends list from local database
 */
public class FriendsLocalLoader extends LocalLoader<User> {

    public FriendsLocalLoader(Context context) {
        super(context);
    }

    @Override
    protected String[] getSelectionColumnsValues() {
        return null;
    }

    @Override
    protected String[] getSelectionColumns() {
        return null;
    }

    @Override
    protected DatabaseDao<User> getDatabaseDao() {
        return new FriendsDao(getContext());
    }

}
