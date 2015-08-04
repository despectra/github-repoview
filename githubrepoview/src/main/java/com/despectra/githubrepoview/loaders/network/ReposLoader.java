package com.despectra.githubrepoview.loaders.network;

import android.content.Context;

import com.despectra.githubrepoview.cache.CacheSyncManager;
import com.despectra.githubrepoview.cache.ReposSyncManager;
import com.despectra.githubrepoview.cache.db.DatabaseDao;
import com.despectra.githubrepoview.cache.db.ReposDao;
import com.despectra.githubrepoview.models.Repo;
import com.despectra.githubrepoview.models.User;
import com.despectra.githubrepoview.rest.GitHubService;
import com.despectra.githubrepoview.sqlite.ReposTable;

import java.util.List;

/**
 * User repos async loader
 */
public class ReposLoader extends ListLoader<Repo> {

    public static final String SORT_ORDER = "pushed";
    
    private User mUser;

    public ReposLoader(Context context, User user) {
        super(context);
        mUser = user;
    }

    @Override
    protected String[] getLocalWhereCols() {
        return new String[] {
                ReposTable.COLUMN_OWNER_ID
        };
    }

    @Override
    protected String[] getLocalWhereColsValues() {
        return new String[] {
                String.valueOf(mUser.getId())
        };
    }

    @Override
    protected List<Repo> loadItemsFromNetwork(GitHubService restService) {
        return restService.getUserRepos(mUser.getLogin(), SORT_ORDER);
    }

    @Override
    protected DatabaseDao<Repo> getDatabaseDao() {
        return new ReposDao(getContext());
    }

    @Override
    protected CacheSyncManager<Repo, Long> getCacheSyncManager() {
        return new ReposSyncManager(new ReposDao(getContext()), mUser);
    }
}
