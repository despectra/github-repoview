package com.despectra.githubrepoview.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.despectra.githubrepoview.ClickableViewHolder;

import java.util.List;

/**
 * Abstract RecyclerView adapter for rendering item views from simple java lists
 * Parametrized by list item type D and clickable view holder VH
 */
public abstract class ListAdapter<D, VH extends ClickableViewHolder> extends RecyclerView.Adapter<VH> {
    /**
     * List itself
     */
    private List<D> mObjects;
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

    public ListAdapter(OnAdapterItemClickListener<D> itemClickListener) {
        super();
        mAdapterItemClickListener = itemClickListener;
    }

    public D getItemAtPosition(int position) {
        return mObjects.get(position);
    }

    @Override
    public int getItemCount() {
        if(mObjects == null) {
            return 0;
        }
        return mObjects.size();
    }

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
        notifyItemRangeChanged(0, mObjects.size());
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
