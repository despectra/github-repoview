package com.despectra.githubrepoview.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.despectra.githubrepoview.net.Error;
import com.despectra.githubrepoview.rest.GitHubService;
import com.despectra.githubrepoview.rest.RestServiceGenerator;

import retrofit.RetrofitError;

/**
 * Created by Андрей on 21.07.2015.
 */

/**
 * Abstract async data loader from GitHub API
 * Parametrized by datatype <b>D</b> of desired result
 */

public abstract class GitHubApiLoader<D> extends AsyncTaskLoader<D> {

    /**
     * Loaded data is being stored here
     */
    private D mResultData;

    /**
     * Error object indicating that something went wrong
     */
    private Error mLoadingError = null;


    public GitHubApiLoader(Context context) {
        super(context);
    }

    /**
     * Perform data loading in worker thread
     * @return loaded data
     */
    @Override
    public D loadInBackground() {
        GitHubService gitHubService = RestServiceGenerator.createService(GitHubService.class, GitHubService.BASE_URL,
                provideAuthorizationString());
        try {
            mResultData = tryLoadData(gitHubService);
        } catch (RetrofitError error) {
            mLoadingError = Error.fromRetrofitError(error);
        }
        return mResultData;
    }

    /**
     *
     * @return true if last loading completed successfully
     */
    public boolean loadingSucceeded() {
        return mLoadingError == null;
    }

    /**
     * @return loading error object
     */
    public Error getError() {
        return mLoadingError;
    }

    /**
     * Handles a request to start the Loader.
     */
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(mResultData != null && loadingSucceeded()) {
            //if data is already loaded
            deliverResult(mResultData);
        } else {
            //if error happened before or no data available
            mLoadingError = null;
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
        if(mResultData != null) {
            mResultData = null;
        }
        if (mLoadingError != null) {
            mLoadingError = null;
        }
    }

    /**
     * Returns auth string for constructing rest service
     * @return authorization string
     */
    protected abstract String provideAuthorizationString();

    /**
     * Performs exact API calls in order to load data
     * @param restService constructed rest service to make API calls
     * @return loaded data
     */
    protected abstract D tryLoadData(GitHubService restService);
}