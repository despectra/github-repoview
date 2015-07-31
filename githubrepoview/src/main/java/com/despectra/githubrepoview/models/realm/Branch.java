package com.despectra.githubrepoview.models.realm;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Branch extends RealmObject {

    @PrimaryKey
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

    /**
     * Creates a new Branch instance with values filled from given branch
     * @param branch given branch
     * @return new branch
     */
    public static Branch copy(Branch branch) {
        Branch copiedBranch = new Branch();
        copiedBranch.setName(branch.getName());
        copiedBranch.setRepoId(branch.getRepoId());
        return copiedBranch;
    }

}