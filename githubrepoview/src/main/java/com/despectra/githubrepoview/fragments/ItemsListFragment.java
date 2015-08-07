package com.despectra.githubrepoview.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
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

import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.activities.MainActivity;
import com.despectra.githubrepoview.adapters.ListAdapter;
import com.despectra.githubrepoview.viewmodel.ItemListViewModel;
import com.despectra.githubrepoview.viewmodel.ItemViewModel;

/**
 * fragment that shows a list of items from data source <br>
 * Parametrized by item type <D>
 */
public abstract class ItemsListFragment<LVM extends ItemListViewModel, IVM extends ItemViewModel<?>>
        extends Fragment
        implements ListAdapter.OnAdapterItemClickListener<IVM>, SearchView.OnQueryTextListener {

    private RecyclerView mItemsView;
    private ItemListViewModel<IVM, ?> mListViewModel;

    public static ItemsListFragment getInstance(Context context, FragmentManager manager, String fname, Bundle args) {
        ItemsListFragment fragment = (ItemsListFragment) manager.findFragmentByTag(fname);
        if (fragment == null) {
            fragment = (ItemsListFragment) Fragment.instantiate(context, fname);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        MainActivity mainActivity = (MainActivity) activity;
        mainActivity.setSearchViewListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListViewModel = getListViewModel();
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
        mListViewModel.requestData();
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
        mListViewModel.getAdapter().setAdapterItemClickListener(this);
        mItemsView.setAdapter(mListViewModel.getAdapter());
    }

    protected RecyclerView getRecyclerView() {
        return mItemsView;
    }

    protected int getLayoutRes() {
        return R.layout.fragment_items_list;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        mListViewModel.filterData(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        mListViewModel.filterData(newText);
        return true;
    }

    protected abstract ItemListViewModel<IVM,?> getListViewModel();
}
