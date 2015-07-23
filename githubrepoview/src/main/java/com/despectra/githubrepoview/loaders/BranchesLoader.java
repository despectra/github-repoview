package com.despectra.githubrepoview.loaders;

import android.content.Context;

import com.despectra.githubrepoview.models.Branch;
import com.despectra.githubrepoview.rest.GitHubService;

import java.util.List;

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
        return restService.getRepoBranches(mUserName, mRepoName);
    }
}
