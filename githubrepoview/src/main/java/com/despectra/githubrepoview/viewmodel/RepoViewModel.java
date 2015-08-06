package com.despectra.githubrepoview.viewmodel;

import com.despectra.githubrepoview.models.Repo;

/**
 * Repo presentation model
 */
public class RepoViewModel extends ItemViewModel<Repo> {

    public RepoViewModel(Repo model) {
        super(model);
    }

    public String getName() {
        return mModel.getName();
    }

    public String getDescription() {
        return mModel.getDescription();
    }

}
