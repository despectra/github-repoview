package com.despectra.githubrepoview.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.SimpleDividerItemDecoration;
import com.despectra.githubrepoview.viewmodel.RepoViewModel;
import com.despectra.githubrepoview.viewmodel.ReposListViewModel;
import com.despectra.githubrepoview.viewmodel.UserViewModel;

/**
 * Fragment for rendering list of user repos
 */
public class ReposFragment extends ItemsListFragment<ReposListViewModel, RepoViewModel> {

    public static final String USER_ARG = "user";

    private UserViewModel mRepoOwner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String userJson = getArguments().getString(USER_ARG);
        if(userJson == null) {
            throw new IllegalStateException("Repos fragment must be instantiated with owner user item");
        }
        mRepoOwner = UserViewModel.deserialize(userJson);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(mRepoOwner.getName());
        actionBar.setSubtitle(mRepoOwner.getShortInfo());
        getRecyclerView().addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
    }

    @Override
    protected ReposListViewModel getListViewModel() {
        return new ReposListViewModel(getActivity(), getLoaderManager(), mRepoOwner);
    }

    @Override
    public void onAdapterItemClick(RepoViewModel repo, View itemView, int position) {
        Bundle args = new Bundle();
        args.putString(BranchesFragment.OWNER_ARG, mRepoOwner.serialize());
        args.putString(BranchesFragment.REPO_ARG, repo.serialize());

        FragmentManager manager = getFragmentManager();
        String branchesFragmentName = BranchesFragment.class.getName();
        BranchesFragment fragment = (BranchesFragment)
                ItemsListFragment.getInstance(getActivity(), manager, branchesFragmentName, args);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, branchesFragmentName)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(branchesFragmentName)
                .commit();
    }

}
