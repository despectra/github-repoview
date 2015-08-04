package com.despectra.githubrepoview.fragments;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.despectra.githubrepoview.ClickableViewHolder;
import com.despectra.githubrepoview.LoginInfo;
import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.Utils;
import com.despectra.githubrepoview.adapters.FriendsAdapter;
import com.despectra.githubrepoview.adapters.ListAdapter;
import com.despectra.githubrepoview.loaders.local.FriendsLocalLoader;
import com.despectra.githubrepoview.loaders.network.FriendsLoader;
import com.despectra.githubrepoview.models.User;
import com.google.gson.Gson;

import java.util.List;

/**
 * Fragment for rendering list of friends
 */
public class FriendsFragment extends ItemsListFragment<User> {

    public static final String TAG = "FriendsFragment";

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        User currentUser = LoginInfo.getLoggedUser(getActivity());
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(currentUser.getLogin());
        actionBar.setSubtitle("");
    }

    @Override
    protected ListAdapter<User, ? extends ClickableViewHolder> createListAdapter() {
        return new FriendsAdapter(this);
    }

    @Override
    protected Loader<List<User>> createLocalLoader() {
        return new FriendsLocalLoader(getActivity());
    }

    @Override
    protected Loader<List<User>> createNetworkLoader() {
        return new FriendsLoader(getActivity());
    }

    @Override
    public void onAdapterItemClick(User user, View itemView, int position) {
        Bundle args = new Bundle();
        Gson gson = Utils.getDefaultGsonInstance();
        String userJson = gson.toJson(user, User.class);
        args.putString(ReposFragment.USER_ARG, userJson);

        ReposFragment reposFragment = (ReposFragment)
                Fragment.instantiate(getActivity(), ReposFragment.class.getName(), args);
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, reposFragment, ReposFragment.TAG)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(ReposFragment.TAG)
                .commit();
    }


}
