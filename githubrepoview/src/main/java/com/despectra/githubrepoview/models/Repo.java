package com.despectra.githubrepoview.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Repo {

    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private String description;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;
    @Expose
    private Object language;
    @SerializedName("forks_count")
    @Expose
    private int forksCount;
    @SerializedName("stargazers_count")
    @Expose
    private int stargazersCount;
    @SerializedName("watchers_count")
    @Expose
    private int watchersCount;
    @SerializedName("pushed_at")
    @Expose
    private String pushedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     *
     * @return
     * The id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(int id) {
        this.id = id;
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
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The htmlUrl
     */
    public String getHtmlUrl() {
        return htmlUrl;
    }

    /**
     *
     * @param htmlUrl
     * The html_url
     */
    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    /**
     *
     * @return
     * The language
     */
    public Object getLanguage() {
        return language;
    }

    /**
     *
     * @param language
     * The language
     */
    public void setLanguage(Object language) {
        this.language = language;
    }

    /**
     *
     * @return
     * The forksCount
     */
    public int getForksCount() {
        return forksCount;
    }

    /**
     *
     * @param forksCount
     * The forks_count
     */
    public void setForksCount(int forksCount) {
        this.forksCount = forksCount;
    }

    /**
     *
     * @return
     * The stargazersCount
     */
    public int getStargazersCount() {
        return stargazersCount;
    }

    /**
     *
     * @param stargazersCount
     * The stargazers_count
     */
    public void setStargazersCount(int stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    /**
     *
     * @return
     * The watchersCount
     */
    public int getWatchersCount() {
        return watchersCount;
    }

    /**
     *
     * @param watchersCount
     * The watchers_count
     */
    public void setWatchersCount(int watchersCount) {
        this.watchersCount = watchersCount;
    }

    /**
     *
     * @return
     * The pushedAt
     */
    public String getPushedAt() {
        return pushedAt;
    }

    /**
     *
     * @param pushedAt
     * The pushed_at
     */
    public void setPushedAt(String pushedAt) {
        this.pushedAt = pushedAt;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
