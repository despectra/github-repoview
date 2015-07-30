package com.despectra.githubrepoview.cache;

import com.despectra.githubrepoview.models.realm.Repo;

import io.realm.Realm;

/**
 * Class for refreshing repos from cache
 */
public class ReposSyncManager extends CacheSyncManager<Repo, Long> {

    public ReposSyncManager(Class<Repo> itemClass, Realm realm) {
        super(itemClass, realm);
    }

    @Override
    protected Long getItemPrimaryKey(Repo item) {
        return item.getId();
    }

    @Override
    protected void onUpdateLocalItem(Repo localItem, Repo networkItem) {
        Repo.fillRepoPrimitives(localItem, networkItem);
    }

    @Override
    protected void onCreateLocalItem(Repo localItem, Repo networkItem) {
        Repo.fillRepoPrimitives(localItem, networkItem);
    }
}
