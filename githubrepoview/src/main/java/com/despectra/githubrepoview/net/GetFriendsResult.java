package com.despectra.githubrepoview.net;

import com.despectra.githubrepoview.models.User;

import java.util.List;

/**
 *
 */
public class GetFriendsResult extends Result {

    private List<User> mFriends;

    private GetFriendsResult(List<User> friends) {
        super();
        mFriends = friends;
    }

    private GetFriendsResult(Error error) {
        super(error);
    }

    /**
     * Constructs successful result object
     * @param friends list of users friends
     */
    public static GetFriendsResult succeeded(List<User> friends) {
        return new GetFriendsResult(friends);
    }

    /**
     * Constructs failed result object
     * @param error request error
     */
    public static GetFriendsResult failed(Error error) {
        return new GetFriendsResult(error);
    }

    /**
     *
     * @return list of friends
     */
    public List<User> getFriends() {
        return mFriends;
    }

}
