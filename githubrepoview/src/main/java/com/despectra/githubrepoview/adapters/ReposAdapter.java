package com.despectra.githubrepoview.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.despectra.githubrepoview.ClickableViewHolder;
import com.despectra.githubrepoview.R;
import com.despectra.githubrepoview.models.realm.Repo;

/**
 * Adapter for rendering list of user repos
 */
public class ReposAdapter extends ListAdapter<Repo, ReposAdapter.Holder> {

    public ReposAdapter() {
        super();
    }

    public ReposAdapter(OnAdapterItemClickListener<Repo> itemClickListener) {
        super(itemClickListener);
    }

    @Override
    protected boolean testFilterPredicate(Repo item, String filter) {
        return item.getName().contains(filter) || item.getDescription().contains(filter);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_item, parent, false);
        return new Holder(itemView, mItemClickListener);
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Repo repo = getItemAtPosition(position);
        holder.getNameView().setText(repo.getName());
        holder.getDescriptionView().setText(repo.getDescription());
    }

    public static class Holder extends ClickableViewHolder {

        TextView mNameView;
        TextView mDescriptionView;

        public Holder(View itemView, OnItemClickListener listener) {
            super(itemView, listener);
            mNameView = (TextView) itemView.findViewById(R.id.line1);
            mDescriptionView = (TextView) itemView.findViewById(R.id.lines23);
        }

        public TextView getNameView() {
            return mNameView;
        }

        public TextView getDescriptionView() {
            return mDescriptionView;
        }
    }
}
