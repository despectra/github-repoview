package com.despectra.githubrepoview.activities;

import android.content.Intent;
import android.content.Loader;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.SimpleDividerItemDecoration;
import com.despectra.githubrepoview.Utils;
import com.despectra.githubrepoview.adapters.ListAdapter;
import com.despectra.githubrepoview.adapters.ReposAdapter;
import com.despectra.githubrepoview.loaders.local.ReposLocalLoader;
import com.despectra.githubrepoview.loaders.network.ReposLoader;
import com.despectra.githubrepoview.models.realm.Repo;
import com.despectra.githubrepoview.models.realm.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Activity for displaying short user info and list of his repos
 */
public class UserReposActivity extends ItemsListActivity<Repo> {

    public static final String USER_DATA_EXTRA = "userData";

    private User mUser;

    private CollapsingToolbarLayout mCollapsingToolbar;
    private ImageView mAvatarView;

    @Override
    protected void onContinueOnCreate() {
        extractUserData();
        extractViews();
        setupViews();
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
        mAvatarView = (ImageView) findViewById(R.id.ava);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
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

        getToolbar().setNavigationIcon(R.drawable.ic_arrow_back);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getItemsView().addItemDecoration(new SimpleDividerItemDecoration(this));
    }

    /**
     * Hide toolbar title manually
     */
    @Override
    protected void onSearchViewExpanded() {
        mCollapsingToolbar.setTitle("");
        getToolbar().setTitle("");
    }

    /**
     * Show toolbar title manually
     */
    @Override
    protected void onSearchViewCollapsed() {
        mCollapsingToolbar.setTitle(mUser.getName());
        getToolbar().setTitle(mUser.getName());
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_user_repos;
    }

    @Override
    protected ListAdapter createListAdapter() {
        return new ReposAdapter();
    }

    /**
     * Launches repo activity with selected repo
     * @param item clicked object
     * @param itemView clicked view
     * @param position adapter position of clicked item
     */
    @Override
    protected void onItemClick(Repo item, View itemView, int position) {
        Gson gson = Utils.getDefaultGsonInstance();
        String repoData = gson.toJson(item);
        String userData = gson.toJson(mUser);
        Intent intent = new Intent(this, RepoActivity.class);
        intent.putExtra(RepoActivity.USER_DATA_EXTRA, userData);
        intent.putExtra(RepoActivity.REPO_DATA_EXTRA, repoData);
        startActivity(intent);
    }

    @Override
    protected Loader<List<Repo>> createLocalLoader() {
        return new ReposLocalLoader(this, mUser);
    }

    @Override
    protected Loader<List<Repo>> createNetworkLoader() {
        return new ReposLoader(this, mUser);
    }
}
