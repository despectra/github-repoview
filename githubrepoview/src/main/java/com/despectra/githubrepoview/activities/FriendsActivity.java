package com.despectra.githubrepoview.activities;

import android.content.Intent;
import android.content.Loader;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.despectra.githubrepoview.LoginInfo;
import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.Utils;
import com.despectra.githubrepoview.adapters.FriendsAdapter;
import com.despectra.githubrepoview.adapters.ListAdapter;
import com.despectra.githubrepoview.loaders.network.FriendsLoader;
import com.despectra.githubrepoview.loaders.local.FriendsLocalLoader;
import com.despectra.githubrepoview.models.User;
import com.google.gson.Gson;

import java.util.List;

/**
 * Activity for displaying list of users friends (followers)
 */
public class FriendsActivity extends ItemsListActivity<User> {

    private User mCurrentUser;
    private Toolbar mAppBar;

    @Override
    protected void onContinueOnCreate() {
        mCurrentUser = LoginInfo.getLoggedUser(this);
        if(mCurrentUser == null) {
            throw new IllegalStateException("This activity requires login info in shared preferences");
        }
        extractViews();
        setupViews();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_friends;
    }

    @Override
    protected ListAdapter createListAdapter() {
        return new FriendsAdapter();
    }

    /**
     * Launches new UserReposActivity
     * @param item selected user
     * @param itemView item clicked on
     * @param position item position in adapter
     */
    @Override
    protected void onItemClick(User item, View itemView, int position) {
        Intent intent = new Intent(this, UserReposActivity.class);
        //serialize selected item to JSON
        Gson gson = Utils.getDefaultGsonInstance();
        String userData = gson.toJson(item);
        intent.putExtra(UserReposActivity.USER_DATA_EXTRA, userData);
        startActivity(intent);
    }

    @Override
    protected Loader<List<User>> createLocalLoader() {
        return new FriendsLocalLoader(this);
    }

    @Override
    protected Loader<List<User>> createNetworkLoader() {
        return new FriendsLoader(this);
    }

    /**
     * Extracts views from hierarchy and assigns it to view fields
     */
    private void extractViews() {
        mAppBar = (Toolbar) findViewById(R.id.appbar);
    }

    /**
     * Simple views setting up
     */
    private void setupViews() {
        mAppBar.setTitle(mCurrentUser.getLogin());
    }

}
