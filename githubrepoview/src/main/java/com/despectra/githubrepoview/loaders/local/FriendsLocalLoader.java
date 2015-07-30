package com.despectra.githubrepoview.loaders.local;

import android.content.Context;

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
    protected List<User> tryLoadData(Realm realm) {
        return realm.where(User.class).findAll();
    }

    @Override
    protected User copyRealmItem(User item) {
        return User.copy(item);
    }
}
