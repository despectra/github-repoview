package com.despectra.githubrepoview.loaders.network;

import android.content.Context;

import com.despectra.githubrepoview.cache.FriendsSyncManager;
import com.despectra.githubrepoview.models.realm.User;
import com.despectra.githubrepoview.rest.GitHubService;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * Github friends async loader
 */
public class FriendsLoader extends GitHubApiLoader<List<User> > {

    public FriendsLoader(Context context) {
        super(context);
    }

    /**
     * @return null, because loading friends means that we are currently logged in
     */
    @Override
    protected String provideAuthorizationString() {
        return null;
    }

    @Override
    protected List<User> tryLoadData(GitHubService restService) {
        List<User> friends = restService.getFriends();
        //may impact performance
        for(int i = 0; i < friends.size(); i++) {
            User extendedFriend = restService.getUserInfo(friends.get(i).getLogin());
            friends.set(i, extendedFriend);
        }

        Realm realm = Realm.getDefaultInstance();
        RealmResults<User> localFriends = realm.where(User.class).findAll();

        FriendsSyncManager syncManager = new FriendsSyncManager(getContext());
        try {
            syncManager.sync(friends, localFriends);
        } finally {
            realm.close();
        }

        return friends;
    }
}
