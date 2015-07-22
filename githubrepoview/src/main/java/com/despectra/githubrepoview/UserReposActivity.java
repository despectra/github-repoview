package com.despectra.githubrepoview;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import com.despectra.githubrepoview.adapters.ReposAdapter;
import com.despectra.githubrepoview.loaders.ReposLoader;
import com.despectra.githubrepoview.models.Repo;
import com.despectra.githubrepoview.models.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Activity for displaying short user info and list of his repos
 */
public class UserReposActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Repo>> {

    public static final String USER_DATA_EXTRA = "userData";

    private User mUser;

    private CollapsingToolbarLayout mCollapsingToolbar;
    private ImageView mAvatarView;
    private RecyclerView mReposView;

    private ReposAdapter mReposAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_repos);
        extractUserData();
        extractViews();
        setupViews();
        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * Deserializes user data provided by startActivity intent extras
     */
    private void extractUserData() {
        String userData = getIntent().getStringExtra(USER_DATA_EXTRA);
        if (userData == null) {
            throw new IllegalStateException("Missing user data extra");
        }
        Gson gson = new Gson();
        mUser = gson.fromJson(userData, User.class);
    }

    private void extractViews() {
        mAvatarView = (ImageView) findViewById(R.id.ava);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mReposView = (RecyclerView) findViewById(R.id.repos_view);
    }

    private void setupViews() {
        //load avatar image in header only after layout happened
        mAvatarView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Picasso.with(UserReposActivity.this)
                        .load(mUser.getAvatarUrl())
                        .resize(0, mAvatarView.getHeight())
                        //.centerInside()
                        .into(mAvatarView);
                return true;
            }
        });
        mCollapsingToolbar.setTitle(mUser.getName());
        mReposView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mReposAdapter = new ReposAdapter(null);
        mReposView.setAdapter(mReposAdapter);
    }

    @Override
    public Loader<List<Repo>> onCreateLoader(int id, Bundle params) {
        return new ReposLoader(this, mUser.getLogin());
    }

    @Override
    public void onLoadFinished(Loader<List<Repo>> loader, List<Repo> repos) {
        ReposLoader reposLoader = (ReposLoader) loader;
        if(reposLoader.loadingSucceeded()) {
            mReposAdapter.updateList(repos);
        } else {
            Toast.makeText(this, reposLoader.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Repo>> loader) {
        mReposAdapter.updateList(null);
    }
}
