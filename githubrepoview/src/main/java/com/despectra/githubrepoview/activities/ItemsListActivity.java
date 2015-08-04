package com.despectra.githubrepoview.activities;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.despectra.githubrepoview.ClickableViewHolder;
import com.despectra.githubrepoview.LoginInfo;
import com.despectra.githubrepoview.LogoutDialog;
import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.adapters.ListAdapter;
import com.despectra.githubrepoview.loaders.network.GitHubApiLoader;

import java.util.List;

import io.realm.RealmObject;

/**
 * Abstract activity that renders items into RecyclerView
 * @param <D> item type parameter
 */
public abstract class ItemsListActivity<D> extends AppCompatActivity
        implements
        ListAdapter.OnAdapterItemClickListener<D>,
        SearchView.OnQueryTextListener,
        MenuItemCompat.OnActionExpandListener {

    //loader ids
    public static final int LOCAL_LOADER_ID = 0;
    public static final int NETWORK_LOADER_ID = 1;

    private static final String LOGOUT_CONFIRM_DIALOG = "logoutDialog";

    private RecyclerView mItemsView;
    private ListAdapter<D, ? extends ClickableViewHolder> mItemsAdapter;
    private Toolbar mToolbar;

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
                ItemsListActivity.this.getLoaderManager().restartLoader(LOCAL_LOADER_ID, null, mLocalLoaderCllbacks);
            } else {
                Toast.makeText(ItemsListActivity.this, githubLoader.getError().getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onLoaderReset(Loader<List<D>> loader) {
        }
    };
    private LogoutDialog.Callback mLogoutDialogCallback = new LogoutDialog.Callback() {
        @Override
        public void onLogoutConfirmed() {
            performLogout();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        setupToolbar();
        setupRecyclerView();
        onContinueOnCreate();

        getLoaderManager().restartLoader(LOCAL_LOADER_ID, null, mLocalLoaderCllbacks);
        getLoaderManager().initLoader(NETWORK_LOADER_ID, null, mNetworkLoaderCallbacks);

        if(savedInstanceState != null) {
            LogoutDialog dialog =  (LogoutDialog) getSupportFragmentManager().findFragmentByTag(LOGOUT_CONFIRM_DIALOG);
            if(dialog != null) {
                dialog.setCallback(mLogoutDialogCallback);
            }
        }
    }

    /**
     * Initialize toolbar behaviour
     */
    private void setupToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.inflateMenu(R.menu.main_menu);
        MenuItem searchItem = mToolbar.getMenu().findItem(R.id.search);
        MenuItemCompat.setOnActionExpandListener(searchItem, this);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        showLogoutDialog();
                        break;
                }
                return true;
            }
        });
    }

    /**
     * Shows logout confirmation dialog
     */
    private void showLogoutDialog() {
        LogoutDialog dialog =  (LogoutDialog) getSupportFragmentManager().findFragmentByTag(LOGOUT_CONFIRM_DIALOG);
        if(dialog == null) {
            dialog = new LogoutDialog();
        }
        dialog.setCallback(mLogoutDialogCallback);
        dialog.show(getSupportFragmentManager(), LOGOUT_CONFIRM_DIALOG);
    }

    /**
     * Logout action implementation
     * Remove all user credentials from shared preferences, switch to LoginActivity
     */
    private void performLogout() {
        LoginInfo.clearLoggedUser(this);

        getLoaderManager().destroyLoader(LOCAL_LOADER_ID);
        getLoaderManager().destroyLoader(NETWORK_LOADER_ID);

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Extracts and initializes main RecyclerView
     */
    private void setupRecyclerView() {
        mItemsView = (RecyclerView) findViewById(android.R.id.list);
        if(mItemsView == null) {
            throw new IllegalStateException("Layout for ItemsListActivity should have RecyclerView with id=@android:id/list");
        }
        mItemsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mItemsAdapter = createListAdapter();
        mItemsAdapter.setAdapterItemClickListener(this);
        mItemsView.setAdapter(mItemsAdapter);
    }

    /**
     * Implementation of ListAdapter.OnAdapterItemClickListener interface
     * @param item clicked object
     * @param itemView clicked view
     * @param position adapter position of clicked item
     */
    @Override
    public void onAdapterItemClick(D item, View itemView, int position) {
        onItemClick(item, itemView, position);
    }

    /**
     * @return activitys main recyclerview
     */
    protected RecyclerView getItemsView() {
        return mItemsView;
    }

    /**
     * @return main toolbar
     */
    protected Toolbar getToolbar() {
        return mToolbar;
    }

    /**
     * @return layout id resource for activity
     */
    protected abstract int getLayoutRes();

    /**
     * Implement this method to launch custom code in inherited activity within OnCreate
     * RecyclerView and ListAdapter are available from here
     */
    protected abstract void onContinueOnCreate();

    /**
     * Generates concrete list adapter
     * @return list adapter
     */
    protected abstract ListAdapter<D, ? extends ClickableViewHolder> createListAdapter();

    /**
     * Handles adapter item click event
     * @param item clicked item
     * @param itemView clicked view
     * @param position adapter position
     */
    protected abstract void onItemClick(D item, View itemView, int position);

    /**
     * Generates concrete local loader
     * @return local loader
     */
    protected abstract Loader<List<D>> createLocalLoader();

    /**
     * Generates concrete network loader
     * @return network loader
     */
    protected abstract Loader<List<D>> createNetworkLoader();

    /**
     * Handle search view text submission event
     * @param query text entered
     * @return true if event was handled by listened
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        mItemsAdapter.updateSearchFilter(query);
        return true;
    }

    /**
     * Handle search view text change event
     * @param newText text entered
     * @return true if event was handled by listened
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        mItemsAdapter.updateSearchFilter(newText);
        return true;
    }

    /**
     * handles searchview expanding event
     * @param menuItem item with search icon
     * @return true if event is handled by this listener
     */
    @Override
    public boolean onMenuItemActionExpand(MenuItem menuItem) {
        onSearchViewExpanded();
        return true;
    }

    /**
     * handles searchview collapsing event
     * @param menuItem item with search icon
     * @return true if event is handled by this listener
     */
    @Override
    public boolean onMenuItemActionCollapse(MenuItem menuItem) {
        onSearchViewCollapsed();
        return true;
    }

    protected void onSearchViewExpanded() {
    }

    protected void onSearchViewCollapsed() {
    }
}
