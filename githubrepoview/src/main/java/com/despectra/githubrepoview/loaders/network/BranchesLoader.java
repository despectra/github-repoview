package com.despectra.githubrepoview.loaders.network;

import android.content.Context;

import com.despectra.githubrepoview.cache.BranchesSyncManager;
import com.despectra.githubrepoview.models.realm.Branch;
import com.despectra.githubrepoview.models.realm.Repo;
import com.despectra.githubrepoview.models.realm.User;
import com.despectra.githubrepoview.rest.GitHubService;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Branches list async loader
 */
public class BranchesLoader extends GitHubApiLoader<List<Branch>> {

    /**
     * Data needed to load list of branches
     */
    private String mUserName;
    private String mRepoName;

    public BranchesLoader(Context context, String userName, String repoName) {
        super(context);
        mUserName = userName;
        mRepoName = repoName;
    }

    /**
     * @return null, because we are already logged in
     */
    @Override
    protected String provideAuthorizationString() {
        return null;
    }

    /**
     * loads list of repository branches
     * @param restService constructed rest service to make API calls
     * @return loaded list
     */
    @Override
    protected List<Branch> tryLoadData(GitHubService restService) {
        List<Branch> branches = restService.getRepoBranches(mUserName, mRepoName);
        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).equalTo("login", mUserName).findFirst();
        Repo repo = user.getRepos().where().equalTo("name", mRepoName).findFirst();
        RealmList<Branch> localBranches = repo.getBranches();
        BranchesSyncManager syncManager = new BranchesSyncManager(Branch.class, realm);
        try {
            syncManager.sync(branches, localBranches);
        } finally {
            realm.close();
        }
        return branches;
    }
}
