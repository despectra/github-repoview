package com.despectra.githubrepoview.activities;

import android.content.Loader;
import android.view.View;

import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.SimpleDividerItemDecoration;
import com.despectra.githubrepoview.Utils;
import com.despectra.githubrepoview.adapters.BranchesAdapter;
import com.despectra.githubrepoview.adapters.ListAdapter;
import com.despectra.githubrepoview.loaders.local.BranchesLocalLoader;
import com.despectra.githubrepoview.loaders.network.BranchesLoader;
import com.despectra.githubrepoview.models.realm.Branch;
import com.despectra.githubrepoview.models.realm.Repo;
import com.despectra.githubrepoview.models.realm.User;
import com.google.gson.Gson;

import java.util.List;

/**
 * Activity for displaying information on exact github repository
 */
public class RepoActivity extends ItemsListActivity<Branch> {

    public static final String REPO_DATA_EXTRA = "repoData";
    public static final String USER_DATA_EXTRA = "userData";

    private User mUser;
    private Repo mRepo;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_repo;
    }

    @Override
    protected void onContinueOnCreate() {
        extractIntentData();
        setupViews();
    }

    @Override
    protected ListAdapter createListAdapter() {
        return new BranchesAdapter();
    }

    @Override
    protected void onItemClick(Branch item, View itemView, int position) {
    }

    @Override
    protected Loader<List<Branch>> createLocalLoader() {
        return new BranchesLocalLoader(this, mRepo);
    }

    @Override
    protected Loader<List<Branch>> createNetworkLoader() {
        return new BranchesLoader(this, mUser, mRepo);
    }

    private void setupViews() {
        getToolbar().setNavigationIcon(R.drawable.ic_arrow_back);
        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getToolbar().setTitle(mRepo.getName());
        getToolbar().setSubtitle(getRepoStats());
        getItemsView().addItemDecoration(new SimpleDividerItemDecoration(this));
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
        return getString(R.string.repo_info_template, mRepo.getStargazersCount(), mRepo.getForksCount());
    }

}
