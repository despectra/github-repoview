package com.despectra.githubrepoview.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.despectra.githubrepoview.ClickableViewHolder;
import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.models.Branch;

/**
 * Adapter for rendering branches list
 */
public class BranchesAdapter extends ListAdapter<Branch, BranchesAdapter.Holder> {

    public BranchesAdapter() {
        super();
    }

    public BranchesAdapter(OnAdapterItemClickListener<Branch> itemClickListener) {
        super(itemClickListener);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.branch_item, parent, false);
        return new Holder(itemView, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Branch branch = getItemAtPosition(position);
        TextView textView = holder.getTextView();
        textView.setText(branch.getName());
        if(branch.getName().equals("master")) {
            textView.setTextColor(textView.getContext().getResources().getColor(R.color.md_green_800));
        } else {
            textView.setTextColor(Color.BLACK);
        }
    }

    public static class Holder extends ClickableViewHolder {

        TextView mTextView;

        public Holder(View itemView, OnItemClickListener listener) {
            super(itemView, listener);
            mTextView = (TextView) itemView;
        }

        public TextView getTextView() {
            return mTextView;
        }
    }
}
