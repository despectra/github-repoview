package com.despectra.githubrepoview;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
/**
 * Abstract view holder for handling click events on items
 */
public class ClickableViewHolder extends RecyclerView.ViewHolder {

    private ViewDataBinding mBinding;
    private OnItemClickListener mListener;

    public ClickableViewHolder(final ViewDataBinding binding, OnItemClickListener listener) {
        super(binding.getRoot());
        mListener = listener;
        mBinding = binding;
        mBinding.setVariable(com.despectra.githubrepoview.BR.clickListener, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onItemClick(itemView, getAdapterPosition());
                }
            }
        });
    }

    public ViewDataBinding getBinding() {
        return mBinding;
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
