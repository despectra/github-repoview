package com.despectra.githubrepoview.activities;

import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.SimpleDividerItemDecoration;
import com.despectra.githubrepoview.Utils;
import com.despectra.githubrepoview.adapters.BranchesAdapter;
import com.despectra.githubrepoview.loaders.BranchesLoader;
import com.despectra.githubrepoview.models.Branch;
import com.despectra.githubrepoview.models.Repo;
import com.despectra.githubrepoview.models.User;
import com.google.gson.Gson;

import java.util.List;

/**
 * Activity for displaying information on exact github repository
 */
public class RepoActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Branch>> {

    public static final String REPO_DATA_EXTRA = "repoData";
    public static final String USER_DATA_EXTRA = "userData";

    private User mUser;
    private Repo mRepo;

    private Toolbar mToolbar;
    private RecyclerView mBranchesView;
    private BranchesAdapter mBranchesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);
        extractIntentData();
        extractViews();
        setupViews();
        getLoaderManager().initLoader(0, null, this);
    }

    private void extractViews() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mBranchesView = (RecyclerView) findViewById(R.id.branches_view);
    }

    private void setupViews() {
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mToolbar.setTitle(mRepo.getName());
        mToolbar.setSubtitle(getRepoStats());

        mBranchesView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mBranchesAdapter = new BranchesAdapter(null);
        mBranchesView.setAdapter(mBranchesAdapter);
        mBranchesView.addItemDecoration(new SimpleDividerItemDecoration(this));
    }

    /**
     * Deserializes repo and user data provided by startActivity intent extras
     */
    private void extractIntentData() {
        String userData = getIntent().getStringExtra(USER_DATA_EXTRA);
        String repoData = getIntent().getStringExtra(REPO_DATA_EXTRA);
        if (userData == null || repoData == null) {
            throw new IllegalStateException("Missing intent extras");
        }
        Gson gson = Utils.getDefaultGsonInstance();
        mRepo = gson.fromJson(repoData, Repo.class);
        mUser = gson.fromJson(userData, User.class);
    }

    /**
     * @return String with basic repo stats
     */
    public String getRepoStats() {
        return String.format("%s start, %s forks",
                mRepo.getStargazersCount(), mRepo.getForksCount());
    }

    @Override
    public Loader<List<Branch>> onCreateLoader(int i, Bundle bundle) {
        return new BranchesLoader(this, mUser.getLogin(), mRepo.getName());
    }

    @Override
    public void onLoadFinished(Loader<List<Branch>> loader, List<Branch> branches) {
        BranchesLoader branchesLoader = (BranchesLoader) loader;
        if(branchesLoader.loadingSucceeded()) {
            mBranchesAdapter.updateList(branches);
        } else {
            Toast.makeText(this, branchesLoader.getError().getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Branch>> loader) {
        mBranchesAdapter.updateList(null);
    }

}
