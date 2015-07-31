package com.despectra.githubrepoview.cache;

import android.content.Context;

import com.despectra.githubrepoview.models.realm.Branch;
import com.despectra.githubrepoview.models.realm.Repo;

import io.realm.Realm;

/**
 * Class for refreshing branches records in cache
 */
public class BranchesSyncManager extends CacheSyncManager<Branch, String> {

    private final Repo mParentRepo;

    public BranchesSyncManager(Context context, Repo parentRepo) {
        super(context);
        mParentRepo = parentRepo;
    }

    @Override
    protected String getItemUniqueKey(Branch item) {
        return item.getName();
    }

    @Override
    protected void onUpdateLocalItem(Branch localItem, Branch networkItem) {
        localItem.setName(networkItem.getName());
    }

    @Override
    protected void onCreateLocalItem(Branch localItem, Branch networkItem) {
        localItem.setId(getDbWriteStrategy().getNextItemId(Branch.class));
        localItem.setRepoId(mParentRepo.getId());
        localItem.setName(networkItem.getName());
    }

    @Override
    protected Branch createNewItemModel() {
        return new Branch();
    }
}
