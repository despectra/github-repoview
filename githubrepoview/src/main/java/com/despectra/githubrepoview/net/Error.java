package com.despectra.githubrepoview.net;

import android.support.annotation.NonNull;

import retrofit.RetrofitError;

/**
 * Request error model class
 */
public class Error {
    public enum Type {
        HTTP,
        NETWORK,
        UNEXPECTED
    }
    private Type mErrorType;
    private String mErrorMessage;

    public Error(@NonNull Type type, @NonNull String message) {
        mErrorType = type;
        mErrorMessage = message;
    }

    /**
     * @return type of error
     */
    public Type getType() {
        return mErrorType;
    }

    /**
     * @return error message
     */
    @NonNull public String getMessage() {
        return mErrorMessage;
    }

    /**
     * Converts RetrofitError object to Error
     * @param retrofitError converting error object
     * @return Error object
     */
    public static Error fromRetrofitError(RetrofitError retrofitError) {
        String message = retrofitError.getMessage();
        Type type;
        switch(retrofitError.getKind()) {
            case HTTP:
                type = Error.Type.HTTP;
                break;
            case NETWORK:
                type = Error.Type.NETWORK;
                break;
            case CONVERSION:
                type = Error.Type.UNEXPECTED;
                break;
            case UNEXPECTED:
                type = Error.Type.UNEXPECTED;
                break;
            default:
                type = Error.Type.UNEXPECTED;
                break;
        }
        return new Error(type, message);
    }

}
