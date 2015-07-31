package com.despectra.githubrepoview.cache.db;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Realm-specific implementation of DbWriteStrategy
 */
public class RealmWriteStrategy implements DbWriteStrategy {

    private Realm mRealm;

    @Override
    public void beginTransaction(Context context) {
        mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();
    }

    @Override
    public void create(Object item) {
        checkItemType(item);
        RealmObject realmItem = (RealmObject) item;
        mRealm.copyToRealm(realmItem);
    }

    /**
     *
     * @param type model type
     * @return next id value in realm table
     */
    @Override
    public long getNextItemId(Class type) {
        return mRealm.where(type).maximumInt("id") + 1;
    }

    @Override
    public void update(Object oldItem, Object newItem) {
    }

    @Override
    public void delete(Object item) {
        checkItemType(item);
    }

    private void checkItemType(Object item) {
        if(!(item instanceof RealmObject)) {
            throw new ClassCastException("Realm object only allowed here");
        }
    }

    @Override
    public void commitTransaction() {
        mRealm.commitTransaction();
        mRealm.close();
    }

}
