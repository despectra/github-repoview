package com.despectra.githubrepoview.viewmodel;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.despectra.githubrepoview.adapters.ListAdapter;
import com.despectra.githubrepoview.loaders.local.LocalLoader;
import com.despectra.githubrepoview.loaders.network.ListLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class that provides access to application data to UI
 */
public abstract class ItemListViewModel<VM extends ItemViewModel<BM>, BM> {

    private static final int LOCAL_LOADER_ID = 0;
    private static final int NETWORK_LOADER_ID = 1;

    private List<VM> mItems = new ArrayList<>();

    private Context mContext;
    private LoaderManager mLoaderManager;

    /**
     * Loader callbacks for both local and network loader
     */
    private LoaderManager.LoaderCallbacks<List<BM>> mLocalLoaderCallbacks = new LoaderManager.LoaderCallbacks<List<BM>>() {
        @Override
        public Loader<List<BM>> onCreateLoader(int id, Bundle args) {
            return createLocalLoader(mContext);
        }

        /**
         * Handles local loader result
         * Populates ListAdapter with obtained list
         * @param loader finished loader
         * @param data items obtained from local cache
         */
        @Override
        public void onLoadFinished(Loader<List<BM>> loader, List<BM> data) {
            mItems.clear();
            for(BM model : data) {
                mItems.add(createItemViewModel(model));
            }
            mAdapter.updateList(mItems);
        }

        @Override
        public void onLoaderReset(Loader<List<BM>> loader) {
        }
    };

    private LoaderManager.LoaderCallbacks<List<BM>> mNetworkLoaderCallbacks = new LoaderManager.LoaderCallbacks<List<BM>>() {
        @Override
        public Loader<List<BM>> onCreateLoader(int id, Bundle args) {
            return createNetworkLoader(mContext);
        }

        /**
         * Handles network loader result
         * Forces local loader restarting
         * @param loader finished loader
         * @param data loaded items
         */
        @Override
        public void onLoadFinished(Loader<List<BM>> loader, List<BM> data) {
            mLoaderManager.restartLoader(LOCAL_LOADER_ID, null, mLocalLoaderCallbacks);
        }

        @Override
        public void onLoaderReset(Loader<List<BM>> loader) {
        }
    };

    private ListAdapter<VM> mAdapter;

    public ItemListViewModel(Context context, LoaderManager loaderManager) {
        mContext = context;
        mLoaderManager = loaderManager;
        mAdapter = createListAdapter();
    }

    public void requestData() {
        mLoaderManager.restartLoader(LOCAL_LOADER_ID, null, mLocalLoaderCallbacks);
        mLoaderManager.initLoader(NETWORK_LOADER_ID, null, mNetworkLoaderCallbacks);
    }

    public void filterData(String filter) {
        mAdapter.updateSearchFilter(filter);
    }

    public ListAdapter<VM> getAdapter() {
        return mAdapter;
    }

    protected abstract LocalLoader<BM> createLocalLoader(Context context);

    protected abstract ListLoader<BM> createNetworkLoader(Context context);

    protected abstract ListAdapter<VM> createListAdapter();

    protected abstract VM createItemViewModel(BM model);

}
