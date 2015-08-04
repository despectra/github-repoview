package com.despectra.githubrepoview.cache;

import com.despectra.githubrepoview.cache.db.ReposDao;
import com.despectra.githubrepoview.models.Repo;
import com.despectra.githubrepoview.models.User;

/**
 * Class for refreshing repos from cache
 */
public class ReposSyncManager extends CacheSyncManager<Repo, Long> {

    private final User mRepoOwner;

    public ReposSyncManager(ReposDao dao, User owner) {
        super(dao);
        mRepoOwner = owner;
    }

    @Override
    protected Long getItemUniqueKey(Repo item) {
        return item.getId();
    }

    @Override
    protected void onUpdateLocalItem(Repo localItem, Repo networkItem) {
        Repo.fillRepoPrimitives(localItem, networkItem);
        localItem.setOwnerId(mRepoOwner.getId());
    }

    @Override
    protected void onCreateLocalItem(Repo localItem, Repo networkItem) {
        Repo.fillRepoPrimitives(localItem, networkItem);
        localItem.setOwnerId(mRepoOwner.getId());
    }

    @Override
    protected Repo createNewItemModel() {
        return new Repo();
    }
}
