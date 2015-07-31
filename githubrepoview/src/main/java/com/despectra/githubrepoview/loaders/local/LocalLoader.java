package com.despectra.githubrepoview.loaders.local;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.despectra.githubrepoview.cache.db.Cache;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Loader for loading items from local cache (realm database)
 */
public abstract class LocalLoader<D> extends AsyncTaskLoader<List<D>> {

    private Cache mCache;

    public LocalLoader(Context context) {
        super(context);
        mCache = new Cache(context);
    }

    /**
     * Loads items list from the realm database
     * Copies query result to new list, just in order to deliver result to UI thread
     * @return list of items
     */
    @Override
    public List<D> loadInBackground() {
        mCache.openCache();
        List<D> cacheItems = new ArrayList<>();
        try {
            cacheItems.addAll(tryLoadData(mCache));
        } finally {
            mCache.closeCache();
        }

        return cacheItems;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    /**
     * Performs a query to local database
     * @param cache data cache object
     * @return loaded list
     */
    protected abstract List<D> tryLoadData(Cache cache);
}
