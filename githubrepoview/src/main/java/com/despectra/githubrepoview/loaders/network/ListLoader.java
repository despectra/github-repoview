package com.despectra.githubrepoview.loaders.network;

import android.content.Context;

import com.despectra.githubrepoview.cache.CacheSyncManager;
import com.despectra.githubrepoview.cache.db.DatabaseDao;
import com.despectra.githubrepoview.rest.GitHubService;

import java.util.List;

/**
 * Loader for obtaining list of generic items from rest service and pushing it to local cache
 */
public abstract class ListLoader<D> extends GitHubApiLoader<List<D>> {

    private DatabaseDao<D> mDao;

    public ListLoader(Context context) {
        super(context);
        mDao = getDatabaseDao();
    }

    @Override
    protected final String provideAuthorizationString() {
        return null;
    }

    @Override
    protected final List<D> tryLoadData(GitHubService restService) {
        List<D> networkItems = loadItemsFromNetwork(restService);
        mDao.open();
        List<D> localItems = mDao.getItems(getLocalWhereCols(), getLocalWhereColsValues());

        CacheSyncManager<D, ? extends Comparable> syncManager = getCacheSyncManager();
        try {
            syncManager.sync(networkItems, localItems);
        } finally {
            mDao.close();
        }
        return networkItems;
    }

    /**
     * @return default columns names for selection items from local database
     */
    protected abstract String[] getLocalWhereCols();
    /**
     * @return default columns values for selection items from local database
     */
    protected abstract String[] getLocalWhereColsValues();

    /**
     * Performs HTTP request to REST service to obtain updated items list
     * @param restService implementation of REST service
     * @return items list from REST service
     */
    protected abstract List<D> loadItemsFromNetwork(GitHubService restService);

    /**
     * @return dao for performing cache refreshing operations
     */
    protected abstract DatabaseDao<D> getDatabaseDao();

    /**
     * @return sync manager for refreshing cache
     */
    protected abstract CacheSyncManager<D, ? extends Comparable> getCacheSyncManager();
}
