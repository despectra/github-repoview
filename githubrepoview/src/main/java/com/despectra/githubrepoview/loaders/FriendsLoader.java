package com.despectra.githubrepoview.loaders;

import android.content.Context;

import com.despectra.githubrepoview.LoginInfo;
import com.despectra.githubrepoview.models.User;
import com.despectra.githubrepoview.rest.GitHubService;

import java.util.List;


/**
 * Github friends async loader
 */
public class FriendsLoader extends GitHubApiLoader<List<User> > {

    public FriendsLoader(Context context) {
        super(context);
    }

    @Override
    protected String provideAuthorizationString() {
        User currentUser = LoginInfo.getLoggedUser(getContext());
        if(currentUser == null) {
            throw new IllegalStateException("Missing current user data in shared prefs");
        }
        return LoginInfo.getAuthorization(getContext());
    }

    @Override
    protected List<User> tryLoadData(GitHubService restService) {
        List<User> friends = restService.getFriends();
        //may impact performance
        for(int i = 0; i < friends.size(); i++) {
            User extendedFriend = restService.getUserInfo(friends.get(i).getLogin());
            friends.set(i, extendedFriend);
        }
        return friends;
    }
}
