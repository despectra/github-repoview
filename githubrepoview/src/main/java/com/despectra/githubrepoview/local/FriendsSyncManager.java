package com.despectra.githubrepoview.local;

import com.despectra.githubrepoview.models.User;

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

    private void fillFromNetwork(User localUser, User networkUser) {
        localUser.setId(networkUser.getId());
        localUser.setLogin(networkUser.getLogin());
        localUser.setName(networkUser.getName());
        localUser.setEmail(networkUser.getEmail());
        localUser.setLocation(networkUser.getLocation());
        localUser.setCompany(networkUser.getCompany());
        localUser.setAvatarUrl(networkUser.getAvatarUrl());
    }
}
