package com.despectra.githubrepoview;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.despectra.githubrepoview.adapters.FriendsAdapter;
import com.despectra.githubrepoview.loaders.FriendsLoader;
import com.despectra.githubrepoview.models.User;
import com.despectra.githubrepoview.net.Error;

import java.util.List;


public class FriendsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<User>> {

    private static final int FRIENDS_LOADER_ID = 0;
    private User mCurrentUser;
    private Toolbar mAppBar;
    private RecyclerView mFriendsView;
    private FriendsAdapter mFriendsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrentUser = LoginInfo.getLoggedUser(this);
        if(mCurrentUser == null) {
            throw new IllegalStateException("This activity requires login info in shared preferences");
        }
        setContentView(R.layout.activity_friends);
        extractViews();
        bindDataToViews();
        getLoaderManager().initLoader(FRIENDS_LOADER_ID, null, this);
    }

    /**
     * Extracts views from hierarchy and assigns it to view fields
     */
    private void extractViews() {
        mAppBar = (Toolbar) findViewById(R.id.appbar);
        mFriendsView = (RecyclerView) findViewById(R.id.friends_view);
        mFriendsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    /**
     * Binds data to necessary views
     */
    private void bindDataToViews() {
        mAppBar.setTitle(mCurrentUser.getLogin());
        mFriendsAdapter = new FriendsAdapter();
        mFriendsView.setAdapter(mFriendsAdapter);
    }

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle bundle) {
        return new FriendsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<User>> loader, List<User> result) {
        FriendsLoader friendsLoader = (FriendsLoader) loader;
        if(friendsLoader.loadingSucceeded()) {
            mFriendsAdapter.updateList(result);
        } else {
            Error error = friendsLoader.getError();
            Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<User>> loader) {
        mFriendsAdapter.updateList(null);
    }

}
