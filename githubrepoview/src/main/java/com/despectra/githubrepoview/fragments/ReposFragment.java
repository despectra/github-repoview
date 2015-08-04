package com.despectra.githubrepoview.fragments;

import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.despectra.githubrepoview.ClickableViewHolder;
import com.despectra.githubrepoview.SimpleDividerItemDecoration;
import com.despectra.githubrepoview.Utils;
import com.despectra.githubrepoview.adapters.ListAdapter;
import com.despectra.githubrepoview.adapters.ReposAdapter;
import com.despectra.githubrepoview.loaders.local.ReposLocalLoader;
import com.despectra.githubrepoview.loaders.network.ReposLoader;
import com.despectra.githubrepoview.models.Repo;
import com.despectra.githubrepoview.models.User;
import com.google.gson.Gson;

import java.util.List;

/**
 * Fragment for rendering list of user repos
 */
public class ReposFragment extends ItemsListFragment<Repo> {

    public static final String USER_ARG = "user";
    public static final String TAG = "ReposFragment";

    private User mRepoOwner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userJson = getArguments().getString(USER_ARG);
        if(userJson == null) {
            throw new IllegalStateException("Repos fragment must be instantiated with owner user item");
        }
        Gson gson = Utils.getDefaultGsonInstance();
        mRepoOwner = gson.fromJson(userJson, User.class);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mRepoOwner.getName());
        actionBar.setSubtitle(String.format("%s, %s", mRepoOwner.getCompany(), mRepoOwner.getLocation()));
        getRecyclerView().addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
    }

    @Override
    protected ListAdapter<Repo, ? extends ClickableViewHolder> createListAdapter() {
        return new ReposAdapter(this);
    }

    @Override
    protected Loader<List<Repo>> createLocalLoader() {
        return new ReposLocalLoader(getActivity(), mRepoOwner);
    }

    @Override
    protected Loader<List<Repo>> createNetworkLoader() {
        return new ReposLoader(getActivity(), mRepoOwner);
    }

    @Override
    public void onAdapterItemClick(Repo item, View itemView, int position) {

    }
}
