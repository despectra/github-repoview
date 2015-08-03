package com.despectra.githubrepoview.cache.db;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import io.realm.RealmObject;

/**
 * Class for performing database-specific low-level operations
 */
public abstract class DatabaseDriver<T extends RealmObject> {

    private Class<T> mItemType;

    public DatabaseDriver(Class<T> itemType) {
        mItemType = itemType;
    }

    public Class<T> getItemType() {
        return mItemType;
    }

    public abstract List<JsonObject> getItems(String foreignKeyColumn, long foreignKeyValue);

    public abstract void create(JsonObject item);

    public abstract void update(JsonObject oldItem, JsonObject newItem);

    public abstract void delete(JsonObject item);

}
