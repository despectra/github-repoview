package com.despectra.githubrepoview.net;

import com.despectra.githubrepoview.models.User;


/**
 * Result of a login operation
 */
public class LoginResult extends Result {

    private User mLoggedUser = null;

    private LoginResult(User loggedUser) {
        super();
        mLoggedUser = loggedUser;
    }

    private LoginResult(Error error) {
        super(error);
    }

    /**
     * Constructs successful result object
     * @param loggedUser user that successfully logged in
     */
    public static LoginResult succeeded(User loggedUser) {
        return new LoginResult(loggedUser);
    }

    /**
     * Constructs failed result object
     * @param error request error
     */
    public static LoginResult failed(Error error) {
        return new LoginResult(error);
    }

    /**
     *
     * @return user logged in
     */
    public User getUser() {
        return mLoggedUser;
    }
}
