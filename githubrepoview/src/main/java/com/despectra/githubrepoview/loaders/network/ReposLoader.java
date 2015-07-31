package com.despectra.githubrepoview.loaders.network;

import android.content.Context;

import com.despectra.githubrepoview.cache.ReposSyncManager;
import com.despectra.githubrepoview.models.realm.Repo;
import com.despectra.githubrepoview.models.realm.User;
import com.despectra.githubrepoview.rest.GitHubService;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * User repos async loader
 */
public class ReposLoader extends GitHubApiLoader<List<Repo>> {

    public static final String SORT_ORDER = "pushed";
    
    private User mUser;

    public ReposLoader(Context context, User user) {
        super(context);
        mUser = user;
    }

    /**
     * @return null because loading user repos means that we re currently logged in
     */
    @Override
    protected String provideAuthorizationString() {
        return null;
    }

    @Override
    protected List<Repo> tryLoadData(GitHubService restService) {
        List<Repo> repos = restService.getUserRepos(mUser.getLogin(), SORT_ORDER);

        Realm realm = Realm.getDefaultInstance();
        RealmList<Repo> localRepos = realm.where(User.class).equalTo("login", mUser.getLogin()).findFirst().getRepos();
        ReposSyncManager syncManager = new ReposSyncManager(getContext(), mUser);
        try {
            syncManager.sync(repos, localRepos);
        } finally {
            realm.close();
        }
        return repos;
    }
}
