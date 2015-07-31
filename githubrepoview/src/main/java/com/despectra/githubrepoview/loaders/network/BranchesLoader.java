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
    private User mOwner;
    private Repo mRepo;

    public BranchesLoader(Context context, User owner, Repo repo) {
        super(context);
        mOwner = owner;
        mRepo = repo;
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
        List<Branch> branches = restService.getRepoBranches(mOwner.getLogin(), mRepo.getName());

        Realm realm = Realm.getDefaultInstance();
        User user = realm.where(User.class).equalTo("login", mOwner.getLogin()).findFirst();
        Repo repo = user.getRepos().where().equalTo("name", mRepo.getName()).findFirst();
        RealmList<Branch> localBranches = repo.getBranches();
        BranchesSyncManager syncManager = new BranchesSyncManager(getContext(), repo);
        try {
            syncManager.sync(branches, localBranches);
        } finally {
            realm.close();
        }
        return branches;
    }
}
