package com.despectra.githubrepoview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Abstract view holder for handling click events on items
 */
public abstract class ClickableViewHolder extends RecyclerView.ViewHolder {

    private OnItemClickListener mListener;

    public ClickableViewHolder(final View itemView, OnItemClickListener listener) {
        super(itemView);
        mListener = listener;
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null) {
                    mListener.onItemClick(itemView, getAdapterPosition());
                }
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    /**
     * Listener interface
     */
    public interface OnItemClickListener {
        /**
         * handles item click event
         * @param itemView clicked item
         * @param position adapter position of clicked item
         */
        void onItemClick(View itemView, int position);
    }
}
