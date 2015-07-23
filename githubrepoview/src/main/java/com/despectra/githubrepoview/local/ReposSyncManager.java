package com.despectra.githubrepoview.local;

import com.despectra.githubrepoview.models.Repo;

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
        fillFromNetwork(localItem, networkItem);
    }

    @Override
    protected void onCreateLocalItem(Repo localItem, Repo networkItem) {
        fillFromNetwork(localItem, networkItem);
    }

    private void fillFromNetwork(Repo localItem, Repo networkItem) {
        localItem.setId(networkItem.getId());
        localItem.setName(networkItem.getName());
        localItem.setDescription(networkItem.getDescription() != null ? networkItem.getDescription() : "");
        localItem.setForksCount(networkItem.getForksCount());
        localItem.setStargazersCount(networkItem.getStargazersCount());
        localItem.setWatchersCount(networkItem.getWatchersCount());
    }
}
