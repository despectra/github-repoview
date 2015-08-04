package com.despectra.githubrepoview.loaders.local;

import android.content.Context;

import com.despectra.githubrepoview.cache.db.BranchesDao;
import com.despectra.githubrepoview.cache.db.DatabaseDao;
import com.despectra.githubrepoview.models.Branch;
import com.despectra.githubrepoview.models.Repo;
import com.despectra.githubrepoview.sqlite.BranchesTable;

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
    protected String[] getSelectionColumnsValues() {
        return new String[]{
                String.valueOf(mRepo.getId())
        };
    }

    @Override
    protected String[] getSelectionColumns() {
        return new String[] {
                BranchesTable.COLUMN_REPO_ID
        };
    }

    @Override
    protected DatabaseDao<Branch> getDatabaseDao() {
        return new BranchesDao(getContext());
    }
}
