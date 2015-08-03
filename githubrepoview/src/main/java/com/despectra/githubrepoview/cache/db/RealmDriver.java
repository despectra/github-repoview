package com.despectra.githubrepoview.cache.db;

import com.despectra.githubrepoview.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Realm-specific implementation of database driver
 */
public class RealmDriver<T extends RealmObject> extends DatabaseDriver<T> {

    private Realm mRealm;
    private Gson mGson;

    public RealmDriver(Class<T> type) {
        super(type);
        mRealm = Realm.getDefaultInstance();
        mGson = Utils.getDefaultGsonInstance();
    }

    @Override
    public List<JsonObject> getItems(String foreignKeyColumn, long foreignKeyValue) {
        RealmQuery<T> query = mRealm.where(getItemType());
        if(foreignKeyColumn != null) {
            query.equalTo(foreignKeyColumn, foreignKeyValue);
        }
        RealmResults<T> result = query.findAll();
        List<JsonObject> items = new ArrayList<>();
        for(T realmItem : result) {
            JsonObject item = (JsonObject) mGson.toJsonTree(realmItem, getItemType());
            items.add(item);
        }
        return items;
    }

    @Override
    public void create(JsonObject item) {
        T realmItem = mGson.fromJson(item, getItemType());
        mRealm.copyToRealm(realmItem);
    }

    @Override
    public void update(JsonObject oldItem, JsonObject newItem) {

    }

    @Override
    public void delete(JsonObject item) {
        T realmItem = mGson.fromJson(item, getItemType());
        realmItem.removeFromRealm();
    }
}
