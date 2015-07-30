package com.despectra.githubrepoview.loaders.local;

import android.content.Context;

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
    private String mLogin;

    public ReposLocalLoader(Context context, String login) {
        super(context);
        mLogin = login;
    }

    @Override
    protected List<Repo> tryLoadData(Realm realm) {
        return realm.where(User.class).equalTo("login", mLogin).findFirst().getRepos();
    }

    @Override
    protected Repo copyRealmItem(Repo item) {
        return Repo.copy(item);
    }
}
