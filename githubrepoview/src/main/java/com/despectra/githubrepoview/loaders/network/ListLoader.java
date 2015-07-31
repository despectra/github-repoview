package com.despectra.githubrepoview.loaders.network;

import android.content.Context;

import com.despectra.githubrepoview.cache.CacheSyncManager;
import com.despectra.githubrepoview.cache.FriendsSyncManager;
import com.despectra.githubrepoview.cache.db.Cache;
import com.despectra.githubrepoview.models.realm.User;
import com.despectra.githubrepoview.rest.GitHubService;

import java.util.List;

/**
 * Loader for obtaining list of generic items from rest service and pushing it to local cache
 */
public abstract class ListLoader<D> extends GitHubApiLoader<List<D>> {

    private Cache mCache;

    public ListLoader(Context context) {
        super(context);
        mCache = new Cache(context);
    }

    @Override
    protected final String provideAuthorizationString() {
        return null;
    }

    @Override
    protected final List<D> tryLoadData(GitHubService restService) {
        List<D> networkItems = loadItemsFromNetwork(restService);
        mCache.openCache();
        List<D> localItems = loadItemsFromCache(mCache);

        CacheSyncManager syncManager = getCacheSyncManager();
        try {
            syncManager.sync(networkItems, localItems);
        } finally {
            mCache.closeCache();
        }
        return networkItems;
    }

    protected abstract List<D> loadItemsFromNetwork(GitHubService restService);
    protected abstract List<D> loadItemsFromCache(Cache cache);
    protected abstract CacheSyncManager getCacheSyncManager();
}
