package com.despectra.githubrepoview;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.despectra.githubrepoview.adapters.FriendsAdapter;
import com.despectra.githubrepoview.loaders.FriendsLoader;
import com.despectra.githubrepoview.models.User;
import com.despectra.githubrepoview.net.GetFriendsResult;

import java.util.List;


public class FriendsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<GetFriendsResult> {

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
    public Loader<GetFriendsResult> onCreateLoader(int id, Bundle bundle) {
        return new FriendsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<GetFriendsResult> loader, GetFriendsResult result) {
        if(result.isSuccess()) {
            List<User> friends = result.getFriends();
            mFriendsAdapter.updateList(friends);
        } else {
            Toast.makeText(this, result.getError().getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<GetFriendsResult> loader) {
        mFriendsAdapter.updateList(null);
    }

}
