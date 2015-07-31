package com.despectra.githubrepoview.loaders.network;

import android.content.Context;

import com.despectra.githubrepoview.cache.CacheSyncManager;
import com.despectra.githubrepoview.cache.ReposSyncManager;
import com.despectra.githubrepoview.cache.db.Cache;
import com.despectra.githubrepoview.models.realm.Repo;
import com.despectra.githubrepoview.models.realm.User;
import com.despectra.githubrepoview.rest.GitHubService;
import com.despectra.githubrepoview.sqlite.FriendsTable;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * User repos async loader
 */
public class ReposLoader extends ListLoader<Repo> {

    public static final String SORT_ORDER = "pushed";
    
    private User mUser;

    public ReposLoader(Context context, User user) {
        super(context);
        mUser = user;
    }

    @Override
    protected List<Repo> loadItemsFromNetwork(GitHubService restService) {
        return restService.getUserRepos(mUser.getLogin(), SORT_ORDER);
    }

    @Override
    protected List<Repo> loadItemsFromCache(Cache cache) {
        return cache.getReposByOwnerId(mUser.getId());
    }

    @Override
    protected CacheSyncManager getCacheSyncManager() {
        return new ReposSyncManager(getContext(), mUser);
    }
}
