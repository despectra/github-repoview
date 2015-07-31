package com.despectra.githubrepoview.loaders.local;

import android.content.Context;

import com.despectra.githubrepoview.cache.db.Cache;
import com.despectra.githubrepoview.models.realm.Repo;
import com.despectra.githubrepoview.models.realm.User;

import java.util.List;

import io.realm.Realm;

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
    protected List<Repo> tryLoadData(Cache cache) {
        return cache.getReposByOwnerId(mOwner.getId());
    }

}
