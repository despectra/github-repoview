package com.despectra.githubrepoview.loaders.local;

import android.content.Context;

import com.despectra.githubrepoview.cache.db.Cache;
import com.despectra.githubrepoview.models.realm.User;

import java.util.List;

import io.realm.Realm;

/**
 * Loader for retrieving friends list from local database
 */
public class FriendsLocalLoader extends LocalLoader<User> {

    public FriendsLocalLoader(Context context) {
        super(context);
    }

    @Override
    protected List<User> tryLoadData(Cache cache) {
        return cache.getAllFriends();
    }

}
