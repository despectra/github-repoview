package com.despectra.githubrepoview.adapters;

import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.despectra.githubrepoview.BR;
import com.despectra.githubrepoview.ClickableViewHolder;
import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.databinding.RepoItemBinding;
import com.despectra.githubrepoview.models.Repo;

/**
 * Adapter for rendering list of user repos
 */
public class ReposAdapter extends ListAdapter<Repo> {

    public ReposAdapter(OnAdapterItemClickListener<Repo> itemClickListener) {
        super(itemClickListener);
    }

    @Override
    protected int getItemLayoutRes() {
        return R.layout.repo_item;
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        Repo repo = getItemAtPosition(position);
        RepoItemBinding binding = (RepoItemBinding) holder.getBinding();
        binding.setRepo(repo);
    }

    @Override
    protected boolean testFilterPredicate(Repo item, String filter) {
        return item.getName().contains(filter) || item.getDescription().contains(filter);
    }

}
