package com.despectra.githubrepoview;

import com.despectra.githubrepoview.models.realm.Branch;
import com.despectra.githubrepoview.models.realm.Repo;
import com.despectra.githubrepoview.models.realm.User;

import io.realm.annotations.RealmModule;

/**
 * Realm module describing tables schema
 */
@RealmModule(classes = {User.class, Repo.class, Branch.class})
public class RealmSchema {
}
