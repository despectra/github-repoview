package com.despectra.githubrepoview.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.annotations.PrimaryKey;

public class Repo {

    @PrimaryKey
    @Expose
    private long id;
    @Expose
    private String name;
    @Expose
    private String description;
    @Expose
    @SerializedName("forks_count")
    private int forksCount;
    @Expose
    @SerializedName("stargazers_count")
    private int stargazersCount;
    @Expose
    @SerializedName("watchers_count")
    private int watchersCount;

    private long ownerId;

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

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    /**
     * Just fill primitive only values of targetRepo with values of from
     * @param targetRepo repo that will be filled
     * @param from repo which will give the values
     */
    public static void fillRepoPrimitives(Repo targetRepo, Repo from) {
        targetRepo.setId(from.getId());
        targetRepo.setOwnerId(from.getOwnerId());
        targetRepo.setName(from.getName());
        targetRepo.setDescription(from.getDescription() != null ? from.getDescription() : "");
        targetRepo.setForksCount(from.getForksCount());
        targetRepo.setStargazersCount(from.getStargazersCount());
        targetRepo.setWatchersCount(from.getWatchersCount());
    }

}
