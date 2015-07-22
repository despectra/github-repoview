package com.despectra.githubrepoview.loaders;

import android.content.Context;

import com.despectra.githubrepoview.models.Repo;
import com.despectra.githubrepoview.rest.GitHubService;

import java.util.List;

/**
 * User repos async loader
 */
public class ReposLoader extends GitHubApiLoader<List<Repo>> {

    public static final String SORT_ORDER = "pushed";
    
    private String mUserName;

    public ReposLoader(Context context, String userName) {
        super(context);
        mUserName = userName;
    }

    /**
     * @return null because loading user repos means that we re currently logged in
     */
    @Override
    protected String provideAuthorizationString() {
        return null;
    }

    @Override
    protected List<Repo> tryLoadData(GitHubService restService) {
        return restService.getUserRepos(mUserName, SORT_ORDER);
    }
}
