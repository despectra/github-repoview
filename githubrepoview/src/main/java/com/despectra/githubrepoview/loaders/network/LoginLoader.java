package com.despectra.githubrepoview.loaders.network;

import android.content.Context;
import android.util.Base64;

import com.despectra.githubrepoview.LoginInfo;
import com.despectra.githubrepoview.models.realm.User;
import com.despectra.githubrepoview.rest.GitHubService;


/**
 * Loader for running asynchronous login operation off the UI thread
 */
public class LoginLoader extends GitHubApiLoader<User> {

    /**
     * User credentials
     */
    private String mAuthorization;

    public LoginLoader(Context context, String login, String password) {
        super(context);
        String credentials = login + ":" + password;
        mAuthorization = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
    }


    @Override
    protected String provideAuthorizationString() {
        return mAuthorization;
    }

    @Override
    protected User tryLoadData(GitHubService restService) {
        User loggedUser = restService.basicLogin();
        LoginInfo.persistLoggedUser(getContext(), loggedUser, mAuthorization);
        return loggedUser;
    }
}
