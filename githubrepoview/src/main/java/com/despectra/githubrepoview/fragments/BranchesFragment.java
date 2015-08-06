package com.despectra.githubrepoview.fragments;

import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.despectra.githubrepoview.ClickableViewHolder;
import com.despectra.githubrepoview.SimpleDividerItemDecoration;
import com.despectra.githubrepoview.Utils;
import com.despectra.githubrepoview.adapters.BranchesAdapter;
import com.despectra.githubrepoview.adapters.ListAdapter;
import com.despectra.githubrepoview.loaders.local.BranchesLocalLoader;
import com.despectra.githubrepoview.loaders.network.BranchesLoader;
import com.despectra.githubrepoview.models.Branch;
import com.despectra.githubrepoview.models.Repo;
import com.despectra.githubrepoview.models.User;
import com.despectra.githubrepoview.viewmodel.BranchViewModel;
import com.despectra.githubrepoview.viewmodel.BranchesListViewModel;
import com.despectra.githubrepoview.viewmodel.ItemListViewModel;
import com.despectra.githubrepoview.viewmodel.RepoViewModel;
import com.despectra.githubrepoview.viewmodel.UserViewModel;
import com.google.gson.Gson;

import java.util.List;

/**
 * fragment for rendering list of repo branches
 */
public class BranchesFragment extends ItemsListFragment<BranchesListViewModel, BranchViewModel> {

    public static final java.lang.String REPO_ARG = "repo";
    public static final java.lang.String OWNER_ARG = "owner";
    private RepoViewModel mRepo;
    private UserViewModel mOwner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String repoJson = getArguments().getString(REPO_ARG);
        String ownerJson = getArguments().getString(OWNER_ARG);
        if(repoJson == null || ownerJson == null) {
            throw new IllegalStateException("Branches fragment must be instantiated with owner and repo item");
        }
        mRepo = RepoViewModel.deserialize(repoJson);
        mOwner = UserViewModel.deserialize(ownerJson);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mRepo.getName());
        actionBar.setSubtitle(mRepo.getStats());
        getRecyclerView().addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
    }

    @Override
    protected BranchesListViewModel getListViewModel() {
        return new BranchesListViewModel(getActivity(), getLoaderManager(), mOwner, mRepo);
    }

    @Override
    public void onAdapterItemClick(BranchViewModel item, View itemView, int position) {
    }
}
