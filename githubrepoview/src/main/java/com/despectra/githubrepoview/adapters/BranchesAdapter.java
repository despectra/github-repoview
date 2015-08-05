package com.despectra.githubrepoview.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.despectra.githubrepoview.BR;
import com.despectra.githubrepoview.ClickableViewHolder;
import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.databinding.BranchItemBinding;
import com.despectra.githubrepoview.models.Branch;

/**
 * Adapter for rendering branches list
 */
public class BranchesAdapter extends ListAdapter<Branch> {

    public BranchesAdapter(OnAdapterItemClickListener<Branch> itemClickListener) {
        super(itemClickListener);
    }

    @Override
    protected int getItemLayoutRes() {
        return R.layout.branch_item;
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        Branch branch = getItemAtPosition(position);
        BranchItemBinding binding = (BranchItemBinding) holder.getBinding();
        binding.setBranch(branch);
    }

    @Override
    protected boolean testFilterPredicate(Branch item, String filter) {
        return item.getName().contains(filter);
    }

}
