package com.despectra.githubrepoview.loaders.local;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.despectra.githubrepoview.cache.db.DatabaseDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Loader for loading items from local database
 */
public abstract class LocalLoader<D> extends AsyncTaskLoader<List<D>> {

    private DatabaseDao<D> mDao;

    public LocalLoader(Context context) {
        super(context);
        mDao = getDatabaseDao();
    }

    /**
     * Loads items list from the realm database
     * Copies query result to new list, just in order to deliver result to UI thread
     * @return list of items
     */
    @Override
    public List<D> loadInBackground() {
        mDao.open();
        List<D> localItems = null;

        try {
            localItems = mDao.getItems(getSelectionColumns(), getSelectionColumnsValues());
        } finally {
            mDao.close();
        }

        return localItems;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    /**
     * @return default columns names for selection the items from database
     */
    protected abstract String[] getSelectionColumns();

    /**
     * @return default columns values for selection the items from database
     */
    protected abstract String[] getSelectionColumnsValues();

    /**
     * @return dao for retrieving list of items
     */
    protected abstract DatabaseDao<D> getDatabaseDao();
}
