package com.despectra.githubrepoview.models;

import com.google.gson.annotations.Expose;


public class Branch {

    private long id;
    @Expose
    private String name;
    @Expose
    private long repoId;
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

    public long getRepoId() {
        return repoId;
    }

    public void setRepoId(long id) {
        repoId = id;
    }

}