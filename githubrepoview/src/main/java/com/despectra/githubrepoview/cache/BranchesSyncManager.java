package com.despectra.githubrepoview.cache;

import com.despectra.githubrepoview.cache.db.BranchesDao;
import com.despectra.githubrepoview.models.Branch;
import com.despectra.githubrepoview.models.Repo;

/**
 * Class for refreshing branches records in cache
 */
public class BranchesSyncManager extends CacheSyncManager<Branch, String> {

    private final Repo mParentRepo;

    public BranchesSyncManager(BranchesDao dao, Repo parentRepo) {
        super(dao);
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
        localItem.setRepoId(mParentRepo.getId());
        localItem.setName(networkItem.getName());
    }

    @Override
    protected Branch createNewItemModel() {
        return new Branch();
    }
}
