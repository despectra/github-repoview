package com.despectra.githubrepoview.loaders.network;

import android.content.Context;

import com.despectra.githubrepoview.cache.BranchesSyncManager;
import com.despectra.githubrepoview.cache.CacheSyncManager;
import com.despectra.githubrepoview.cache.db.BranchesDao;
import com.despectra.githubrepoview.cache.db.DatabaseDao;
import com.despectra.githubrepoview.models.Branch;
import com.despectra.githubrepoview.models.Repo;
import com.despectra.githubrepoview.models.User;
import com.despectra.githubrepoview.rest.GitHubService;
import com.despectra.githubrepoview.sqlite.BranchesTable;

import java.util.List;

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
    protected String[] getLocalWhereCols() {
        return new String[] {
                BranchesTable.COLUMN_REPO_ID
        };
    }

    @Override
    protected String[] getLocalWhereColsValues() {
        return new String[] {
                String.valueOf(mRepo.getId())
        };
    }

    @Override
    protected List<Branch> loadItemsFromNetwork(GitHubService restService) {
        return restService.getRepoBranches(mOwner.getLogin(), mRepo.getName());
    }

    @Override
    protected DatabaseDao<Branch> getDatabaseDao() {
        return new BranchesDao(getContext());
    }

    @Override
    protected CacheSyncManager<Branch, String> getCacheSyncManager() {
        return new BranchesSyncManager(new BranchesDao(getContext()), mRepo);
    }
}
