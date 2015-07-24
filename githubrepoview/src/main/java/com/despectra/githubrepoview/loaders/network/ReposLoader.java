package com.despectra.githubrepoview.loaders.network;

import android.content.Context;

import com.despectra.githubrepoview.cache.ReposSyncManager;
import com.despectra.githubrepoview.models.Repo;
import com.despectra.githubrepoview.models.User;
import com.despectra.githubrepoview.rest.GitHubService;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * User repos async loader
 */
public class ReposLoader extends GitHubApiLoader<List<Repo>> {

    public static final String SORT_ORDER = "pushed";
    
    private String mUserName;

    public ReposLoader(Context context, String userName) {
        super(context);
        mUserName = userName;
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
        List<Repo> repos = restService.getUserRepos(mUserName, SORT_ORDER);

        Realm realm = Realm.getDefaultInstance();
        RealmList<Repo> localRepos = realm.where(User.class).equalTo("login", mUserName).findFirst().getRepos();
        ReposSyncManager syncManager = new ReposSyncManager(Repo.class, realm);
        try {
            syncManager.sync(repos, localRepos);
        } finally {
            realm.close();
        }
        return repos;
    }
}
