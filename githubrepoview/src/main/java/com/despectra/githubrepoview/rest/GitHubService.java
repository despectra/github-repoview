package com.despectra.githubrepoview.rest;

import com.despectra.githubrepoview.models.User;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * retrofit interface that turns into REST client
 */
public interface GitHubService {

    String BASE_URL = "https://api.github.com/";

    @GET("/user")
    User basicLogin();

    @GET("/user/following")
    List<User> getFriends();

    @GET("/users/{username}")
    User getUserInfo(@Path("username") String userName);
}
