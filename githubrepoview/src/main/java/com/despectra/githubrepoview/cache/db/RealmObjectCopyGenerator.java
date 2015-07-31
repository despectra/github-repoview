package com.despectra.githubrepoview.cache.db;

import com.despectra.githubrepoview.models.realm.Branch;
import com.despectra.githubrepoview.models.realm.Repo;
import com.despectra.githubrepoview.models.realm.User;

import io.realm.RealmObject;

/**
 *
 */
public class RealmObjectCopyGenerator {

    public static RealmObject generate(RealmObject originalItem) {
        if(originalItem instanceof User) {
            return generateUser((User) originalItem);
        }
        if(originalItem instanceof Repo) {
            return generateRepo((Repo) originalItem);
        }
        if(originalItem instanceof Branch) {
            return generateBranch((Branch) originalItem);
        }
        throw new ClassCastException("Only model classes are allowed here");
    }

    private static RealmObject generateBranch(Branch originalBranch) {
        return Branch.copy(originalBranch);
    }

    private static RealmObject generateRepo(Repo originalRepo) {
        return Repo.copy(originalRepo);
    }

    private static RealmObject generateUser(User originalUser) {
        return User.copy(originalUser);
    }

}
