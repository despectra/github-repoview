package com.despectra.githubrepoview.rest;

import com.despectra.githubrepoview.models.User;

import retrofit.http.GET;
import retrofit.http.POST;

/**
 * retrofit interface that turns into REST client
 */
public interface GitHubService {

    String BASE_URL = "https://api.github.com/";

    @GET("/user")
    User basicLogin();
}
