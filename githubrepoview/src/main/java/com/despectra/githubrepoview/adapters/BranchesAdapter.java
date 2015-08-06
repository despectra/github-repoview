package com.despectra.githubrepoview.adapters;

import com.despectra.githubrepoview.ClickableViewHolder;
import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.databinding.BranchItemBinding;
import com.despectra.githubrepoview.viewmodel.BranchViewModel;

/**
 * Adapter for rendering branches list
 */
public class BranchesAdapter extends ListAdapter<BranchViewModel> {

    public BranchesAdapter(OnAdapterItemClickListener<BranchViewModel> itemClickListener) {
        super(itemClickListener);
    }

    @Override
    protected int getItemLayoutRes() {
        return R.layout.branch_item;
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        BranchViewModel branch = getItemAtPosition(position);
        BranchItemBinding binding = (BranchItemBinding) holder.getBinding();
        binding.setBranch(branch);
    }

    @Override
    protected boolean testFilterPredicate(BranchViewModel item, String filter) {
        return item.getName().contains(filter);
    }

}
