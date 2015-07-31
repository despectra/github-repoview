package com.despectra.githubrepoview.loaders.local;

import android.content.Context;

import com.despectra.githubrepoview.cache.db.Cache;
import com.despectra.githubrepoview.models.realm.Branch;
import com.despectra.githubrepoview.models.realm.Repo;
import com.despectra.githubrepoview.models.realm.User;

import java.util.List;

import io.realm.Realm;

/**
 * Loader of branches from local cache
 */
public class BranchesLocalLoader extends LocalLoader<Branch> {

    /**
     * Load branches of this repo
     */
    private Repo mRepo;

    public BranchesLocalLoader(Context context, Repo repo) {
        super(context);
        mRepo = repo;
    }

    @Override
    protected List<Branch> tryLoadData(Cache cache) {
        return cache.getBranchesByRepoId(mRepo.getId());
    }

}
