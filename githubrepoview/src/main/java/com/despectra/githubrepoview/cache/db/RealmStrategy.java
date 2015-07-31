package com.despectra.githubrepoview.cache.db;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Realm-specific implementation of DbStrategy
 */
public class RealmStrategy implements DbStrategy {

    private Realm mRealm;

    @Override
    public List getByColumnsValues(Class<?> modelClass, String[] columns, Object[] values) {
        Class<? extends RealmObject> realmClass = (Class<? extends RealmObject>) modelClass;
        RealmQuery query = mRealm.where(realmClass);
        if(columns != null && values != null) {
            for(int i = 0; i < columns.length; i++) {
                String column = columns[i];
                Object value = values[i];
                if(value instanceof String) {
                    query.equalTo(column, (String) value);
                } else if(value instanceof Long) {
                    query.equalTo(column, (Long) value);
                }
            }
        }
        RealmResults result = query.findAll();
        List copiedResult = new ArrayList();
        for(Object item : result) {
            copiedResult.add(RealmObjectCopyGenerator.generate((RealmObject) item));
        }
        return copiedResult;
    }

    @Override
    public void open(Context context) {
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public void beginTransaction() {
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
    }

    @Override
    public void rollbackTransaction() {
        mRealm.cancelTransaction();
    }

    @Override
    public void close() {
        mRealm.close();
    }

}
