package com.despectra.githubrepoview;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.despectra.githubrepoview.models.User;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

/**
 * Activity for displaying short user info and list of his repos
 */
public class UserReposActivity extends AppCompatActivity {

    public static final String USER_DATA_EXTRA = "userData";

    private User mUser;

    private CollapsingToolbarLayout mCollapsingToolbar;
    private ImageView mAvatarView;
    private RecyclerView mReposView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_repos);
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
    }
}
