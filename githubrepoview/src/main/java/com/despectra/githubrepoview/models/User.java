package com.despectra.githubrepoview.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * User model class
 */
public class User extends RealmObject {

    @PrimaryKey
    private long id;
    private String login;
    @SerializedName("avatar_url")
    private String avatarUrl;
    private String name;
    private String company;
    private String location;
    private String email;



    private RealmList<Repo> repos;

    /**
     *
     * @return
     * The login
     */
    public String getLogin() {
        return login;
    }

    /**
     *
     * @param login
     * The login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     *
     * @return
     * The id
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The avatarUrl
     */
    public String getAvatarUrl() {
        return avatarUrl;
    }

    /**
     *
     * @param avatarUrl
     * The avatar_url
     */
    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The company
     */
    public String getCompany() {
        return company;
    }

    /**
     *
     * @param company
     * The company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     *
     * @return
     * The location
     */
    public String getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public RealmList<Repo> getRepos() {
        return repos;
    }

    public void setRepos(RealmList<Repo> repos) {
        this.repos = repos;
    }

    public static User copy(User user) {
        User copiedUser = new User();
        fillUserPrimitives(copiedUser, user);
        return copiedUser;
    }

    /**
     * Just fill primitive only values of targetUser with values of from
     * @param targetUser user that will be filled
     * @param from user which will give the values
     */
    public static void fillUserPrimitives(User targetUser, User from) {
        targetUser.setId(from.getId());
        targetUser.setLogin(from.getLogin());
        targetUser.setName(from.getName());
        targetUser.setEmail(from.getEmail());
        targetUser.setLocation(from.getLocation());
        targetUser.setCompany(from.getCompany());
        targetUser.setAvatarUrl(from.getAvatarUrl());
    }

}