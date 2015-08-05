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
import com.google.gson.Gson;

import java.util.List;

/**
 * fragment for rendering list of repo branches
 */
public class BranchesFragment extends ItemsListFragment<Branch> {

    public static final java.lang.String REPO_ARG = "repo";
    public static final java.lang.String OWNER_ARG = "owner";
    public static final String TAG = "Branchesfragment";
    private Repo mRepo;
    private User mOwner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String repoJson = getArguments().getString(REPO_ARG);
        String ownerJson = getArguments().getString(OWNER_ARG);
        if(repoJson == null || ownerJson == null) {
            throw new IllegalStateException("Branches fragment must be instantiated with owner and repo item");
        }
        Gson gson = Utils.getDefaultGsonInstance();
        mRepo = gson.fromJson(repoJson, Repo.class);
        mOwner = gson.fromJson(ownerJson, User.class);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mRepo.getName());
        actionBar.setSubtitle(String.format("%s forks, %s stars, %s watchers",
                mRepo.getForksCount(), mRepo.getStargazersCount(), mRepo.getWatchersCount()));
        getRecyclerView().addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
    }

    @Override
    protected ListAdapter<Branch> createListAdapter() {
        return new BranchesAdapter(this);
    }

    @Override
    protected Loader<List<Branch>> createLocalLoader() {
        return new BranchesLocalLoader(getActivity(), mRepo);
    }

    @Override
    protected Loader<List<Branch>> createNetworkLoader() {
        return new BranchesLoader(getActivity(), mOwner, mRepo);
    }

    @Override
    public void onAdapterItemClick(Branch item, View itemView, int position) {
    }
}
