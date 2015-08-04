package com.despectra.githubrepoview.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.despectra.githubrepoview.ClickableViewHolder;
import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.activities.MainActivity;
import com.despectra.githubrepoview.adapters.ListAdapter;
import com.despectra.githubrepoview.loaders.network.GitHubApiLoader;

import java.util.List;

/**
 * fragment that shows a list of items from data source <br>
 * Parametrized by item type <D>
 */
public abstract class ItemsListFragment<D> extends Fragment implements ListAdapter.OnAdapterItemClickListener<D>, SearchView.OnQueryTextListener {

    //loader ids
    public static final int LOCAL_LOADER_ID = 0;
    public static final int NETWORK_LOADER_ID = 1;

    private RecyclerView mItemsView;
    private ListAdapter<D, ? extends ClickableViewHolder> mItemsAdapter;

    /**
     * Loader callbacks for both local and network loader
     */
    private LoaderManager.LoaderCallbacks<List<D>> mLocalLoaderCllbacks = new LoaderManager.LoaderCallbacks<List<D>>() {

        @Override
        public Loader<List<D>> onCreateLoader(int i, Bundle bundle) {
            return createLocalLoader();
        }

        /**
         * Handles local loader result
         * Populates ListAdapter with obtained list
         * @param loader finished loader
         * @param localItems items obtained from local cache
         */
        @Override
        public void onLoadFinished(Loader<List<D>> loader, List<D> localItems) {
            mItemsAdapter.updateList(localItems);
        }

        @Override
        public void onLoaderReset(Loader<List<D>> loader) {
            mItemsAdapter.updateList(null);
        }
    };

    private LoaderManager.LoaderCallbacks<List<D>> mNetworkLoaderCallbacks = new LoaderManager.LoaderCallbacks<List<D>>() {
        @Override
        public Loader<List<D>> onCreateLoader(int i, Bundle bundle) {
            return createNetworkLoader();
        }

        /**
         * Handles network loader result
         * Forces local loader restarting
         * @param loader finished loader
         * @param items loaded items
         */
        @Override
        public void onLoadFinished(Loader<List<D>> loader, List<D> items) {
            GitHubApiLoader githubLoader = (GitHubApiLoader) loader;
            if(githubLoader.loadingSucceeded()) {
                ItemsListFragment.this.getLoaderManager().restartLoader(LOCAL_LOADER_ID, null, mLocalLoaderCllbacks);
            } else {
                Toast.makeText(getActivity(), githubLoader.getError().getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onLoaderReset(Loader<List<D>> loader) {
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MainActivity mainActivity = (MainActivity) activity;
        mainActivity.setSearchViewListener(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutRes(), container, false);
        setupRecyclerView(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().restartLoader(LOCAL_LOADER_ID, null, mLocalLoaderCllbacks);
        getLoaderManager().initLoader(NETWORK_LOADER_ID, null, mNetworkLoaderCallbacks);
    }

    protected ActionBar getSupportActionBar() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        return activity.getSupportActionBar();
    }

    /**
     * Extracts and initializes main RecyclerView
     */
    private void setupRecyclerView(View rootView) {
        mItemsView = (RecyclerView) rootView.findViewById(android.R.id.list);
        if(mItemsView == null) {
            throw new IllegalStateException("Layout for ItemsListActivity should have RecyclerView with id=@android:id/list");
        }
        mItemsView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mItemsAdapter = createListAdapter();
        mItemsAdapter.setAdapterItemClickListener(this);
        mItemsView.setAdapter(mItemsAdapter);
    }

    protected RecyclerView getRecyclerView() {
        return mItemsView;
    }

    protected int getLayoutRes() {
        return R.layout.fragment_items_list;
    }

    protected abstract ListAdapter<D,? extends ClickableViewHolder> createListAdapter();

    protected abstract Loader<List<D>> createLocalLoader();
    protected abstract Loader<List<D>> createNetworkLoader();

    @Override
    public boolean onQueryTextSubmit(String query) {
        mItemsAdapter.updateSearchFilter(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mItemsAdapter.updateSearchFilter(newText);
        return true;
    }
}
