package com.despectra.githubrepoview.loaders;

import android.content.Context;

import com.despectra.githubrepoview.models.Branch;
import com.despectra.githubrepoview.models.Repo;
import com.despectra.githubrepoview.models.User;

import java.util.List;

import io.realm.Realm;

/**
 * Loader of branches from local cache
 */
public class BranchesLocalLoader extends LocalLoader<Branch> {

    /**
     * Fields to identify exact repo
     */
    private String mLogin;
    private String mRepoName;

    public BranchesLocalLoader(Context context, String login, String repoName) {
        super(context);
        mLogin = login;
        mRepoName = repoName;
    }

    @Override
    protected List<Branch> tryLoadData(Realm realm) {
        User user = realm.where(User.class).equalTo("login", mLogin).findFirst();
        Repo repo = user.getRepos().where().equalTo("name", mRepoName).findFirst();
        return repo.getBranches();
    }

    @Override
    protected Branch copyRealmItem(Branch item) {
        return Branch.copy(item);
    }
}
