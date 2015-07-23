package com.despectra.githubrepoview.models;

import com.google.gson.annotations.Expose;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Branch extends RealmObject {

    @PrimaryKey
    private String name;

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

    public static Branch copy(Branch branch) {
        Branch copiedBranch = new Branch();
        copiedBranch.setName(branch.getName());
        return copiedBranch;
    }
}