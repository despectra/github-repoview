package com.despectra.githubrepoview.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.despectra.githubrepoview.ClickableViewHolder;
import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.RoundedTransformation;
import com.despectra.githubrepoview.models.realm.User;
import com.squareup.picasso.Picasso;

/**
 * Adapter for populating RecyclerView with the list of github friends
 */
public class FriendsAdapter extends ListAdapter<User, FriendsAdapter.Holder> {

    public FriendsAdapter() {
        super();
    }

    public FriendsAdapter(OnAdapterItemClickListener<User> itemClickListener) {
        super(itemClickListener);
    }

    @Override
    protected boolean testFilterPredicate(User item, String filter) {
        return item.getLogin().contains(filter) || item.getName().contains(filter);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.user_item, viewGroup, false);
        return new Holder(itemView, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        User friend = getItemAtPosition(position);
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

    /**
     * ViewHolder for holding reusable item components in memory
     */
    public static class Holder extends ClickableViewHolder {

        private ImageView mIconView;
        private TextView mFirstLineView;
        private TextView mSecondLineView;

        public Holder(View itemView, OnItemClickListener listener) {
            super(itemView, listener);
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
