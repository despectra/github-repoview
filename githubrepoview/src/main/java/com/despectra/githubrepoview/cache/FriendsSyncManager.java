package com.despectra.githubrepoview.cache;

import com.despectra.githubrepoview.models.realm.User;

import io.realm.Realm;

/**
 * Class for refreshing friends table
 */
public class FriendsSyncManager extends CacheSyncManager<User, Long> {

    public FriendsSyncManager(Class<User> itemClass, Realm realm) {
        super(itemClass, realm);
    }

    @Override
    protected Long getItemPrimaryKey(User item) {
        return item.getId();
    }

    @Override
    protected void onUpdateLocalItem(User localItem, User networkItem) {
        User.fillUserPrimitives(localItem, networkItem);
    }

    @Override
    protected void onCreateLocalItem(User localItem, User networkItem) {
        User.fillUserPrimitives(localItem, networkItem);
    }
}
