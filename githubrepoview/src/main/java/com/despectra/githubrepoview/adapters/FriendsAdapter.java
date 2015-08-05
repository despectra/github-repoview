package com.despectra.githubrepoview.adapters;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.despectra.githubrepoview.ClickableViewHolder;
import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.databinding.UserItemBinding;
import com.despectra.githubrepoview.models.User;

/**
 * Adapter for populating RecyclerView with the list of github friends
 */
public class FriendsAdapter extends ListAdapter<User> {

    public FriendsAdapter(OnAdapterItemClickListener<User> itemClickListener) {
        super(itemClickListener);
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        User friend = getItemAtPosition(position);
        UserItemBinding binding = (UserItemBinding) holder.getBinding();
        binding.setUser(friend);

    }

    @Override
    protected int getItemLayoutRes() {
        return R.layout.user_item;
    }

    @Override
    protected boolean testFilterPredicate(User item, String filter) {
        return item.getLogin().contains(filter) || item.getName().contains(filter);
    }
}
