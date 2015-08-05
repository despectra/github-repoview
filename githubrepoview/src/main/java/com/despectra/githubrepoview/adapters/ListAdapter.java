package com.despectra.githubrepoview.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.despectra.githubrepoview.ClickableViewHolder;
import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.databinding.UserItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract RecyclerView adapter for rendering item views from simple java lists
 * Parametrized by list item type D and clickable view holder VH
 */
public abstract class ListAdapter<D> extends RecyclerView.Adapter<ClickableViewHolder> {

    /**
     * List itself
     */
    private List<D> mObjects;

    /**
     * Only filtered, visible items here
     */
    private List<D> mFilteredList = new ArrayList<>();

    /**
     * Item click listeners
     */
    private OnAdapterItemClickListener<D> mAdapterItemClickListener;
    protected ClickableViewHolder.OnItemClickListener mItemClickListener = new ClickableViewHolder.OnItemClickListener() {
        @Override
        public void onItemClick(View itemView, int position) {
            if (mAdapterItemClickListener != null) {
                mAdapterItemClickListener.onAdapterItemClick(getItemAtPosition(position), itemView, position);
            }
        }
    };

    private String mFilter = "";

    public ListAdapter() {
        super();
    }

    public ListAdapter(OnAdapterItemClickListener<D> itemClickListener) {
        this();
        setAdapterItemClickListener(itemClickListener);
    }

    public void setAdapterItemClickListener(OnAdapterItemClickListener<D> listener) {
        mAdapterItemClickListener = listener;
    }

    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), getItemLayoutRes(), viewGroup, false);
        return new ClickableViewHolder(binding, mItemClickListener);
    }

    protected abstract int getItemLayoutRes();

    /**
     * Returns item object by its adapter position
     * @param position adapter position
     * @return item object
     */
    public D getItemAtPosition(int position) {
        return mFilteredList.get(position);
    }

    /**
     * @return the amount of visible items
     */
    @Override
    public int getItemCount() {
        if(mFilteredList == null) {
            return 0;
        }
        return mFilteredList.size();
    }

    /**
     * Updates current filter string
     * @param filter new filter string
     */
    public void updateSearchFilter(String filter) {
        mFilter = filter;
        filterItems();
    }

    /**
     * Refreshes filtered list according to currently set filter string
     * Notifies adapter after filtering
     */
    private void filterItems() {
        if(mObjects.isEmpty()) {
            return;
        }
        mFilteredList.clear();
        for(int i = 0; i < mObjects.size(); i++) {
            D item = mObjects.get(i);
            if(mFilter.isEmpty() || testFilterPredicate(item, mFilter)) {
                mFilteredList.add(item);
            }
        }
        notifyDataSetChanged();
    }

    /**
     * Checks if the item is visible according to current filter string
     * @param item item to check
     * @param filter current filter string
     * @return true if item contains content acceptable by filter string
     */
    protected abstract boolean testFilterPredicate(D item, String filter);

    /**
     * Replaces original list in adapter with the new one
     * @param newObjects new list of data objects
     */
    public void updateList(List<D> newObjects) {
        int prevItemsCount = getItemCount();
        if(newObjects == null || newObjects.isEmpty()) {
            notifyItemRangeRemoved(0, prevItemsCount);
            return;
        }
        mObjects = newObjects;
        mFilteredList.addAll(mObjects);
        filterItems();
    }

    /**
     * Callback for handling item clicks
     * @param <D> Type of list item
     */
    public interface OnAdapterItemClickListener<D> {
        /**
         * Handles adapter item click event
         * @param item clicked object
         * @param itemView clicked view
         * @param position adapter position of clicked item
         */
        void onAdapterItemClick(D item, View itemView, int position);
    }
}
