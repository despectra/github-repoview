package com.despectra.githubrepoview.cache.db;


/**
 * Static methods for obtaining high-level database objects
 */
public class DbObjectsFactory {
    private DbObjectsFactory() {}

    private static DbWriteStrategy sDbWriteStrategy = new RealmWriteStrategy();

    public static DbWriteStrategy getDefaultWriteStrategy() {
        return sDbWriteStrategy;
    }

}
