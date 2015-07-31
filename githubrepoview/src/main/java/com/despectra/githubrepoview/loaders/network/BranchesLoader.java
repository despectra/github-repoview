package com.despectra.githubrepoview.loaders.network;

import android.content.Context;

import com.despectra.githubrepoview.cache.BranchesSyncManager;
import com.despectra.githubrepoview.cache.CacheSyncManager;
import com.despectra.githubrepoview.cache.db.Cache;
import com.despectra.githubrepoview.models.realm.Branch;
import com.despectra.githubrepoview.models.realm.Repo;
import com.despectra.githubrepoview.models.realm.User;
import com.despectra.githubrepoview.rest.GitHubService;
import com.despectra.githubrepoview.sqlite.FriendsTable;
import com.despectra.githubrepoview.sqlite.ReposTable;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Branches list async loader
 */
public class BranchesLoader extends ListLoader<Branch> {

    /**
     * Data needed to load list of branches
     */
    private User mOwner;
    private Repo mRepo;

    public BranchesLoader(Context context, User owner, Repo repo) {
        super(context);
        mOwner = owner;
        mRepo = repo;
    }

    @Override
    protected List<Branch> loadItemsFromNetwork(GitHubService restService) {
        return restService.getRepoBranches(mOwner.getLogin(), mRepo.getName());
    }

    @Override
    protected List<Branch> loadItemsFromCache(Cache cache) {
        return cache.getBranchesByRepoId(mRepo.getId());
    }

    @Override
    protected CacheSyncManager getCacheSyncManager() {
        return new BranchesSyncManager(getContext(), mRepo);
    }
}
