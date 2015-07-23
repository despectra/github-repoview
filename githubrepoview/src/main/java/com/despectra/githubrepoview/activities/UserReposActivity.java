package com.despectra.githubrepoview.activities;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.Toast;

import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.SimpleDividerItemDecoration;
import com.despectra.githubrepoview.Utils;
import com.despectra.githubrepoview.adapters.ListAdapter;
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
public class UserReposActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Repo>>,ListAdapter.OnAdapterItemClickListener<Repo> {

    public static final String USER_DATA_EXTRA = "userData";

    private User mUser;

    private Toolbar mToolbar;
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
        Gson gson = Utils.getDefaultGsonInstance();
        mUser = gson.fromJson(userData, User.class);
    }

    private void extractViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mAvatarView = (ImageView) findViewById(R.id.ava);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mReposView = (RecyclerView) findViewById(R.id.repos_view);
    }

    /**
     * prepare views for their work
     */
    private void setupViews() {
        //load avatar image in header only after layout happened
        mAvatarView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                Picasso.with(UserReposActivity.this)
                        .load(mUser.getAvatarUrl())
                        .resize(0, mAvatarView.getHeight())
                        .into(mAvatarView);
                return true;
            }
        });
        mCollapsingToolbar.setTitle(mUser.getName());
        mCollapsingToolbar.setExpandedTitleColor(Color.WHITE);
        mCollapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mReposView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mReposView.addItemDecoration(new SimpleDividerItemDecoration(this));
        mReposAdapter = new ReposAdapter(this);
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

    /**
     * Launches repo activity with selected repo
     * @param item clicked object
     * @param itemView clicked view
     * @param position adapter position of clicked item
     */
    @Override
    public void onAdapterItemClick(Repo item, View itemView, int position) {
        Gson gson = Utils.getDefaultGsonInstance();
        String repoData = gson.toJson(item);
        String userData = gson.toJson(mUser);
        Intent intent = new Intent(this, RepoActivity.class);
        intent.putExtra(RepoActivity.USER_DATA_EXTRA, userData);
        intent.putExtra(RepoActivity.REPO_DATA_EXTRA, repoData);
        startActivity(intent);
    }
}
