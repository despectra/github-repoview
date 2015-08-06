package com.despectra.githubrepoview.viewmodel;

import android.app.LoaderManager;
import android.content.Context;

import com.despectra.githubrepoview.adapters.BranchesAdapter;
import com.despectra.githubrepoview.adapters.ListAdapter;
import com.despectra.githubrepoview.loaders.local.BranchesLocalLoader;
import com.despectra.githubrepoview.loaders.local.LocalLoader;
import com.despectra.githubrepoview.loaders.network.BranchesLoader;
import com.despectra.githubrepoview.loaders.network.ListLoader;
import com.despectra.githubrepoview.models.Branch;

/**
 * Branches list presentation model
 */
public class BranchesListViewModel extends ItemListViewModel<BranchViewModel, Branch> {

    private UserViewModel mOwner;
    private RepoViewModel mRepo;

    public BranchesListViewModel(Context context, LoaderManager loaderManager, UserViewModel owner, RepoViewModel repo) {
        super(context, loaderManager);
        mRepo = repo;
        mOwner = owner;
    }

    @Override
    protected LocalLoader<Branch> createLocalLoader(Context context) {
        return new BranchesLocalLoader(context, mRepo.getModel());
    }

    @Override
    protected ListLoader<Branch> createNetworkLoader(Context context) {
        return new BranchesLoader(context, mOwner.getModel(), mRepo.getModel());
    }

    @Override
    protected ListAdapter<BranchViewModel> createListAdapter() {
        return new BranchesAdapter(null);
    }

    @Override
    protected BranchViewModel createItemViewModel(Branch model) {
        return new BranchViewModel(model);
    }
}
