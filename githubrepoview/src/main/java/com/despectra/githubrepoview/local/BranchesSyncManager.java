package com.despectra.githubrepoview.local;

import com.despectra.githubrepoview.models.Branch;

import io.realm.Realm;

/**
 * Class for refreshing branches records in cache
 */
public class BranchesSyncManager extends CacheSyncManager<Branch, String> {

    public BranchesSyncManager(Class<Branch> itemClass, Realm realm) {
        super(itemClass, realm);
    }

    @Override
    protected String getItemPrimaryKey(Branch item) {
        return item.getName();
    }

    @Override
    protected void onUpdateLocalItem(Branch localItem, Branch networkItem) {
        localItem.setName(networkItem.getName());
    }

    @Override
    protected void onCreateLocalItem(Branch localItem, Branch networkItem) {
        localItem.setId(getRealm().where(Branch.class).maximumInt("id") + 1);
        localItem.setName(networkItem.getName());
    }
}
