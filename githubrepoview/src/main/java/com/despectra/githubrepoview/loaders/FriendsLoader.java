package com.despectra.githubrepoview.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;

import com.despectra.githubrepoview.FriendsActivity;
import com.despectra.githubrepoview.LoginInfo;
import com.despectra.githubrepoview.models.User;
import com.despectra.githubrepoview.net.Error;
import com.despectra.githubrepoview.net.GetFriendsResult;
import com.despectra.githubrepoview.rest.GitHubService;
import com.despectra.githubrepoview.rest.RestServiceGenerator;

import java.util.List;

import retrofit.RetrofitError;

/**
 * Github friends async loader
 */
public class FriendsLoader extends AsyncTaskLoader<GetFriendsResult> {

    private GetFriendsResult mResult;

    public FriendsLoader(Context context) {
        super(context);
    }

    /**
     * Loads list of followers/following and gets extended info about each friend
     * @return result of operation
     */
    @Override
    public GetFriendsResult loadInBackground() {
        User currentUser = LoginInfo.getLoggedUser(getContext());
        if(currentUser == null) {
            throw new IllegalStateException("Missing current user data in shared prefs");
        }
        String authorization = LoginInfo.getAuthorization(getContext());
        GitHubService restService = RestServiceGenerator.createService(GitHubService.class, GitHubService.BASE_URL, authorization);
        try {
            List<User> friends = restService.getFriends();
            //may impact performance
            for(int i = 0; i < friends.size(); i++) {
                User extendedFriend = restService.getUserInfo(friends.get(i).getLogin());
                friends.set(i, extendedFriend);
            }
            mResult = GetFriendsResult.succeeded(friends);
        } catch (RetrofitError error) {
            mResult = GetFriendsResult.failed(Error.fromRetrofitError(error));
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
