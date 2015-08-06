package com.despectra.githubrepoview.fragments;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;

import com.despectra.githubrepoview.LoginInfo;
import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.models.User;
import com.despectra.githubrepoview.viewmodel.FriendsListViewModel;
import com.despectra.githubrepoview.viewmodel.ItemListViewModel;
import com.despectra.githubrepoview.viewmodel.UserViewModel;

/**
 * Fragment for rendering list of friends
 */
public class FriendsFragment extends ItemsListFragment<FriendsListViewModel, UserViewModel> {

    public static final String TAG = "FriendsFragment";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        UserViewModel currentUser = LoginInfo.getLoggedUser(getActivity());
        if(currentUser == null) {
            throw new IllegalStateException("User must be logged in");
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(currentUser.getLogin());
        actionBar.setSubtitle(currentUser.getShortInfo());
    }

    @Override
    protected ItemListViewModel<UserViewModel, ?> getListViewModel() {
        return new FriendsListViewModel(getActivity(), getLoaderManager());
    }

    @Override
    public void onAdapterItemClick(UserViewModel user, View itemView, int position) {
        Bundle args = new Bundle();
        args.putString(ReposFragment.USER_ARG, user.serialize());

        FragmentManager manager = getFragmentManager();
        String reposFragmentName = ReposFragment.class.getName();
        ReposFragment reposFragment = (ReposFragment)
                ItemsListFragment.getInstance(getActivity(), manager, reposFragmentName, args);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, reposFragment, reposFragmentName)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(reposFragmentName)
                .commit();
    }


}
