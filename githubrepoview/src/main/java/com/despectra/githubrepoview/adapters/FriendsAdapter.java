package com.despectra.githubrepoview.adapters;

import com.despectra.githubrepoview.ClickableViewHolder;
import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.databinding.UserItemBinding;
import com.despectra.githubrepoview.viewmodel.UserViewModel;

/**
 * Adapter for populating RecyclerView with the list of github friends
 */
public class FriendsAdapter extends ListAdapter<UserViewModel> {

    public FriendsAdapter(OnAdapterItemClickListener<UserViewModel> itemClickListener) {
        super(itemClickListener);
    }

    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        UserViewModel friend = getItemAtPosition(position);
        UserItemBinding binding = (UserItemBinding) holder.getBinding();
        binding.setUser(friend);

    }

    @Override
    protected int getItemLayoutRes() {
        return R.layout.user_item;
    }

    @Override
    protected boolean testFilterPredicate(UserViewModel item, String filter) {
        return item.getLogin().contains(filter) || item.getName().contains(filter);
    }
}
