package com.despectra.githubrepoview.loaders.network;

import android.content.Context;

import com.despectra.githubrepoview.cache.CacheSyncManager;
import com.despectra.githubrepoview.cache.FriendsSyncManager;
import com.despectra.githubrepoview.cache.db.DatabaseDao;
import com.despectra.githubrepoview.cache.db.FriendsDao;
import com.despectra.githubrepoview.models.realm.User;
import com.despectra.githubrepoview.rest.GitHubService;

import java.util.List;


/**
 * Github friends async loader
 */
public class FriendsLoader extends ListLoader<User> {

    public FriendsLoader(Context context) {
        super(context);
    }

    @Override
    protected String[] getLocalWhereCols() {
        return null;
    }

    @Override
    protected String[] getLocalWhereColsValues() {
        return null;
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
    protected DatabaseDao<User> getDatabaseDao() {
        return new FriendsDao((getContext()));
    }

    @Override
    protected CacheSyncManager<User, Long> getCacheSyncManager() {
        return new FriendsSyncManager(new FriendsDao(getContext()));
    }
}
