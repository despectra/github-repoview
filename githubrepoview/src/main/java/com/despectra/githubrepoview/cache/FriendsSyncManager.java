package com.despectra.githubrepoview.cache;

import android.content.Context;

import com.despectra.githubrepoview.cache.db.DatabaseDao;
import com.despectra.githubrepoview.cache.db.FriendsDao;
import com.despectra.githubrepoview.models.realm.User;

import io.realm.Realm;

/**
 * Class for refreshing friends table
 */
public class FriendsSyncManager extends CacheSyncManager<User, Long> {

    public FriendsSyncManager(FriendsDao dao) {
        super(dao);
    }

    @Override
    protected Long getItemUniqueKey(User item) {
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

    @Override
    protected User createNewItemModel() {
        return new User();
    }
}
