package com.despectra.githubrepoview.net;

import android.support.annotation.Nullable;

/**
 * Abstract HTTP request result
 */
public abstract class Result {

    /**
     * Error object
     */
    private Error mError;

    /**
     * Empty constructor creates successful result
     */
    public Result() {
        mError = null;
    }

    /**
     * Creates result of failed request
     * @param error error description object
     */
    public Result(Error error) {
        mError = error;
    }

    /**
     * @return true if request was successful
     */
    public boolean isSuccess() {
        return mError == null;
    }

    /**
     * @return error object describing fail reason
     */
    @Nullable
    public Error getError() {
        return mError;
    }
}
