package com.despectra.githubrepoview;

import com.despectra.githubrepoview.models.Branch;
import com.despectra.githubrepoview.models.Repo;
import com.despectra.githubrepoview.models.User;

import io.realm.annotations.RealmModule;

/**
 * Realm module describing tables schema
 */
@RealmModule(classes = {User.class, Repo.class, Branch.class})
public class RealmSchema {
}
