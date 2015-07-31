package com.despectra.githubrepoview.cache.db;


import android.content.Context;

import com.despectra.githubrepoview.models.realm.User;

/**
 * Static methods for obtaining high-level database objects
 */
public class DbUtils {
    private DbUtils() {}

    public static DbStrategy getDefaultStrategy() {
        return new SQLiteStrategy();
    }

}
