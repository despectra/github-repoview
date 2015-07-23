package com.despectra.githubrepoview.activities;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.despectra.githubrepoview.LoginInfo;
import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.Utils;
import com.despectra.githubrepoview.adapters.FriendsAdapter;
import com.despectra.githubrepoview.adapters.ListAdapter;
import com.despectra.githubrepoview.loaders.FriendsLoader;
import com.despectra.githubrepoview.loaders.FriendsLocalLoader;
import com.despectra.githubrepoview.models.User;
import com.despectra.githubrepoview.net.Error;
import com.google.gson.Gson;

import java.util.List;

/**
 * Activity for displaying list of users friends (followers)
 */
public class FriendsActivity extends AppCompatActivity
        implements ListAdapter.OnAdapterItemClickListener<User> {

    private static final int LOCAL_LOADER_ID = 0;
    public static final int NETWORK_LOADER_ID = 1;

    private User mCurrentUser;
    private Toolbar mAppBar;
    private RecyclerView mFriendsView;
    private FriendsAdapter mFriendsAdapter;

    private LoaderManager.LoaderCallbacks<List<User>> mLocalLoaderCallbacks = new LoaderManager.LoaderCallbacks<List<User>>() {
        @Override
        public Loader<List<User>> onCreateLoader(int i, Bundle bundle) {
            return new FriendsLocalLoader(FriendsActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<List<User>> loader, List<User> users) {
            mFriendsAdapter.updateList(users);
        }

        @Override
        public void onLoaderReset(Loader<List<User>> loader) {
            mFriendsAdapter.updateList(null);
        }
    };
    private LoaderManager.LoaderCallbacks<List<User>> mNetworkLoaderCallbacks = new LoaderManager.LoaderCallbacks<List<User>>() {
        @Override
        public Loader<List<User>> onCreateLoader(int i, Bundle bundle) {
            return new FriendsLoader(FriendsActivity.this);
        }

        @Override
        public void onLoadFinished(Loader<List<User>> loader, List<User> users) {
            FriendsLoader friendsLoader = (FriendsLoader) loader;
            if (friendsLoader.loadingSucceeded()) {
                FriendsActivity.this.getLoaderManager().restartLoader(LOCAL_LOADER_ID, null, mLocalLoaderCallbacks);
            } else {
                Toast.makeText(FriendsActivity.this, friendsLoader.getError().getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onLoaderReset(Loader<List<User>> loader) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentUser = LoginInfo.getLoggedUser(this);
        if(mCurrentUser == null) {
            throw new IllegalStateException("This activity requires login info in shared preferences");
        }
        setContentView(R.layout.activity_friends);
        extractViews();
        setupViews();
        getLoaderManager().initLoader(LOCAL_LOADER_ID, null, mLocalLoaderCallbacks);
        getLoaderManager().initLoader(NETWORK_LOADER_ID, null, mNetworkLoaderCallbacks);
    }

    /**
     * Extracts views from hierarchy and assigns it to view fields
     */
    private void extractViews() {
        mAppBar = (Toolbar) findViewById(R.id.appbar);
        mFriendsView = (RecyclerView) findViewById(R.id.friends_view);

    }

    /**
     * Simple views setting up
     */
    private void setupViews() {
        mAppBar.setTitle(mCurrentUser.getLogin());
        mFriendsAdapter = new FriendsAdapter(this);
        mFriendsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mFriendsView.setAdapter(mFriendsAdapter);
    }

    /**
     * Launches new UserReposActivity
     * @param item selected user
     * @param itemView item clicked on
     * @param position item position in adapter
     */
    @Override
    public void onAdapterItemClick(User item, View itemView, int position) {
        Intent intent = new Intent(this, UserReposActivity.class);
        //serialize selected item to JSON
        Gson gson = Utils.getDefaultGsonInstance();
        String userData = gson.toJson(item);
        intent.putExtra(UserReposActivity.USER_DATA_EXTRA, userData);
        startActivity(intent);
    }
}
