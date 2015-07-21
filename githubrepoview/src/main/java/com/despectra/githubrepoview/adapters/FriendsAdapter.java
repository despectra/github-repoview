package com.despectra.githubrepoview.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.models.User;

import java.util.List;

/**
 * Adapter for populating RecyclerView with the list of github friends
 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.Holder> {

    /**
     * List itself
     */
    private List<User> mFriends;

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item, viewGroup, false);
        return new Holder(itemView);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        User friend = mFriends.get(position);
        holder.getFirstLine().setText(friend.getLogin());
        holder.getSecondLine().setText(friend.getName());
    }

    @Override
    public int getItemCount() {
        if(mFriends == null) {
            return 0;
        }
        return mFriends.size();
    }

    /**
     * Replaces original list in adapter with the new one
     * @param newFriends new list of friends
     */
    public void updateList(List<User> newFriends) {
        int prevItemsCount = getItemCount();
        if(newFriends == null || newFriends.isEmpty()) {
            notifyItemRangeRemoved(0, prevItemsCount);
            return;
        }
        mFriends = newFriends;
        notifyItemRangeChanged(0, mFriends.size());
    }

    /**
     * ViewHolder for holding reusable item components in memory
     */
    public static class Holder extends RecyclerView.ViewHolder {

        private TextView mFirstLineView;
        private TextView mSecondLineView;

        public Holder(View itemView) {
            super(itemView);
            mFirstLineView = (TextView) itemView.findViewById(R.id.line1);
            mSecondLineView = (TextView) itemView.findViewById(R.id.line2);
        }

        public TextView getFirstLine() {
            return mFirstLineView;
        }

        public TextView getSecondLine() {
            return mSecondLineView;
        }
    }
}
