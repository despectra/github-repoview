package com.despectra.githubrepoview.models;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Branch extends RealmObject {

    @PrimaryKey
    private long id;
    private String name;

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
     * Creates a new Branch instance with values filled from given branch
     * @param branch given branch
     * @return new branch
     */
    public static Branch copy(Branch branch) {
        Branch copiedBranch = new Branch();
        copiedBranch.setName(branch.getName());
        return copiedBranch;
    }
}