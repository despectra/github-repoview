package com.despectra.githubrepoview.adapters;

import com.despectra.githubrepoview.ClickableViewHolder;
import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.databinding.RepoItemBinding;
import com.despectra.githubrepoview.viewmodel.RepoViewModel;

/**
 * Adapter for rendering list of user repos
 */
public class ReposAdapter extends ListAdapter<RepoViewModel> {

    public ReposAdapter(OnAdapterItemClickListener<RepoViewModel> itemClickListener) {
        super(itemClickListener);
    }

    @Override
    protected int getItemLayoutRes() {
        return R.layout.repo_item;
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        RepoViewModel repo = getItemAtPosition(position);
        RepoItemBinding binding = (RepoItemBinding) holder.getBinding();
        binding.setRepo(repo);
    }

    @Override
    protected boolean testFilterPredicate(RepoViewModel item, String filter) {
        return item.getName().contains(filter) || item.getDescription().contains(filter);
    }

}
