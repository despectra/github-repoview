package com.despectra.githubrepoview.rest;

import com.despectra.githubrepoview.models.realm.Branch;
import com.despectra.githubrepoview.models.realm.Repo;
import com.despectra.githubrepoview.models.realm.User;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

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

    @GET("/users/{username}/repos")
    List<Repo> getUserRepos(@Path("username") String userName, @Query("sort") String sort);

    @GET("/repos/{username}/{reponame}/branches")
    List<Branch> getRepoBranches(@Path("username") String userName, @Path("reponame") String repoName);
}
