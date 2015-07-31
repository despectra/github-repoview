package com.despectra.githubrepoview.loaders.network;

import android.content.Context;

import com.despectra.githubrepoview.cache.CacheSyncManager;
import com.despectra.githubrepoview.cache.FriendsSyncManager;
import com.despectra.githubrepoview.cache.db.Cache;
import com.despectra.githubrepoview.models.realm.User;
import com.despectra.githubrepoview.rest.GitHubService;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Github friends async loader
 */
public class FriendsLoader extends ListLoader<User> {

    public FriendsLoader(Context context) {
        super(context);
    }

    @Override
    protected List<User> loadItemsFromNetwork(GitHubService restService) {
        List<User> friends = restService.getFriends();
        //may impact performance
        for(int i = 0; i < friends.size(); i++) {
            User extendedFriend = restService.getUserInfo(friends.get(i).getLogin());
            friends.set(i, extendedFriend);
        }
        return friends;
    }

    @Override
    protected List<User> loadItemsFromCache(Cache cache) {
        return cache.getAllFriends();
    }

    @Override
    protected CacheSyncManager getCacheSyncManager() {
        return new FriendsSyncManager(getContext());
    }
}
