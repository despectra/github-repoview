package com.despectra.githubrepoview.viewmodel;

import com.despectra.githubrepoview.Utils;
import com.despectra.githubrepoview.models.Repo;
import com.google.gson.Gson;

/**
 * Repo presentation model
 */
public class RepoViewModel extends ItemViewModel<Repo> {

    public RepoViewModel(Repo model) {
        super(model, Repo.class);
    }

    public String getName() {
        return mModel.getName();
    }

    public String getDescription() {
        return mModel.getDescription();
    }

    public String getStats() {
        return String.format("%s forks, %s stars, %s watchers",
                mModel.getForksCount(), mModel.getStargazersCount(), mModel.getWatchersCount());
    }

    public static RepoViewModel deserialize(String json) {
        Gson gson = Utils.getDefaultGsonInstance();
        return new RepoViewModel(gson.fromJson(json, Repo.class));
    }
}
