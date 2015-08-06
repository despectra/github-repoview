package com.despectra.githubrepoview.viewmodel;

import android.app.LoaderManager;
import android.content.Context;

import com.despectra.githubrepoview.adapters.ListAdapter;
import com.despectra.githubrepoview.adapters.ReposAdapter;
import com.despectra.githubrepoview.loaders.local.LocalLoader;
import com.despectra.githubrepoview.loaders.local.ReposLocalLoader;
import com.despectra.githubrepoview.loaders.network.ListLoader;
import com.despectra.githubrepoview.loaders.network.ReposLoader;
import com.despectra.githubrepoview.models.Repo;

/**
 * Repos list presentation model
 */
public class ReposListViewModel extends ItemListViewModel<RepoViewModel, Repo> {

    private UserViewModel mOwner;

    public ReposListViewModel(Context context, LoaderManager loaderManager, UserViewModel owner) {
        super(context, loaderManager);
        mOwner = owner;
    }

    @Override
    protected LocalLoader<Repo> createLocalLoader(Context context) {
        return new ReposLocalLoader(context, mOwner.getModel());
    }

    @Override
    protected ListLoader<Repo> createNetworkLoader(Context context) {
        return new ReposLoader(context, mOwner.getModel());
    }

    @Override
    protected ListAdapter<RepoViewModel> createListAdapter() {
        return new ReposAdapter(null);
    }

    @Override
    protected RepoViewModel createItemViewModel(Repo repo) {
        return new RepoViewModel(repo);
    }
}
