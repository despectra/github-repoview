package com.despectra.githubrepoview.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.RoundedTransformation;
import com.despectra.githubrepoview.models.User;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

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
        Picasso.with(holder.getIcon().getContext())
                .load(friend.getAvatarUrl())
                .resizeDimen(R.dimen.item_icon_width, R.dimen.item_icon_height)
                .centerCrop()
                .transform(new RoundedTransformation(40, 0))
                .placeholder(R.drawable.avatar_placeholder)
                .into(holder.getIcon());
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

        private ImageView mIconView;
        private TextView mFirstLineView;
        private TextView mSecondLineView;

        public Holder(View itemView) {
            super(itemView);
            mIconView = (ImageView) itemView.findViewById(R.id.icon);
            mFirstLineView = (TextView) itemView.findViewById(R.id.line1);
            mSecondLineView = (TextView) itemView.findViewById(R.id.line2);
        }

        public ImageView getIcon() {
            return mIconView;
        }

        public TextView getFirstLine() {
            return mFirstLineView;
        }

        public TextView getSecondLine() {
            return mSecondLineView;
        }
    }
}
