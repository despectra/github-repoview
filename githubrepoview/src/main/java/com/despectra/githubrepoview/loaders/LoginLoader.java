package com.despectra.githubrepoview.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.despectra.githubrepoview.LoginInfo;
import com.despectra.githubrepoview.net.*;
import com.despectra.githubrepoview.models.User;
import com.despectra.githubrepoview.net.Error;
import com.despectra.githubrepoview.rest.GitHubService;
import com.despectra.githubrepoview.rest.RestServiceGenerator;

import retrofit.RetrofitError;

/**
 * Loader for running asynchronous login operation off the UI thread
 */
public class LoginLoader extends AsyncTaskLoader<LoginResult> {

    /**
     * Store result here
     */
    private LoginResult mResult = null;

    /**
     * User credentials
     */
    private String mLogin;
    private String mPassword;

    public LoginLoader(Context context, String login, String password) {
        super(context);
        mLogin = login;
        mPassword = password;
    }

    /**
     * Performs an API call for obtaining user info by credentials
     * Writes user info and auth string to shared prefs if succeed
     * @return result of this operation
     */
    @Override
    public LoginResult loadInBackground() {
        String credentials = mLogin + ":" + mPassword;
        String basicAuthorization = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

        GitHubService gitHubService = RestServiceGenerator.createService(GitHubService.class, GitHubService.BASE_URL,
                basicAuthorization);
        try {
            User loggedUser = gitHubService.basicLogin();
            LoginInfo.persistLoggedUser(getContext(), loggedUser, basicAuthorization);
            mResult = LoginResult.succeeded(loggedUser);
        } catch (RetrofitError error) {
            mResult = LoginResult.failed(Error.fromRetrofitError(error));
        }
        return mResult;
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(mResult != null && mResult.isSuccess()) {
            deliverResult(mResult);
        } else {
            forceLoad();
        }
    }

    /**
     * Handles a request to stop the Loader.
     */
    @Override
    protected void onStopLoading() {
        super.onStopLoading();
        cancelLoad();
    }

    /**
     * Handles a request to completely reset the Loader.
     */
    @Override
    protected void onReset() {
        super.onReset();
        stopLoading();
        if(mResult != null) {
            mResult = null;
        }
    }

}
