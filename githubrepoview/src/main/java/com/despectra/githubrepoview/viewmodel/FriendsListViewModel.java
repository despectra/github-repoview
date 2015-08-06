package com.despectra.githubrepoview.viewmodel;

import android.app.LoaderManager;
import android.content.Context;

import com.despectra.githubrepoview.adapters.FriendsAdapter;
import com.despectra.githubrepoview.adapters.ListAdapter;
import com.despectra.githubrepoview.loaders.local.FriendsLocalLoader;
import com.despectra.githubrepoview.loaders.local.LocalLoader;
import com.despectra.githubrepoview.loaders.network.FriendsLoader;
import com.despectra.githubrepoview.loaders.network.ListLoader;
import com.despectra.githubrepoview.models.User;

/**
 * Friends list presentation model
 */
public class FriendsListViewModel extends ItemListViewModel<UserViewModel, User> {

    public FriendsListViewModel(Context context, LoaderManager loaderManager) {
        super(context, loaderManager);
    }

    @Override
    protected LocalLoader<User> createLocalLoader(Context context) {
        return new FriendsLocalLoader(context);
    }

    @Override
    protected ListLoader<User> createNetworkLoader(Context context) {
        return new FriendsLoader(context);
    }

    @Override
    protected ListAdapter<UserViewModel> createListAdapter() {
        return new FriendsAdapter(null);
    }

    @Override
    protected UserViewModel createItemViewModel(User user) {
        return new UserViewModel(user);
    }
}
