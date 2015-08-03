package com.despectra.githubrepoview.cache.db;

import com.despectra.githubrepoview.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;

/**
 * Class providing crud operations on different items
 * Parametrized by item model type
 */
public class DatabaseDao<T extends RealmObject> {

    private final Class<T> mType;
    private final DatabaseDriver<T> mDriver;
    private final Gson mGson;

    private DatabaseDao(Class<T> modelType, DatabaseDriver<T> driver) {
        mType = modelType;
        mDriver = driver;
        mGson = Utils.getDefaultGsonInstance();
    }

    public static <T extends RealmObject> DatabaseDao<T> getDao(Class<T> modelType, DatabaseDriver<T> driver) {
        return new DatabaseDao<>(modelType, driver);
    }

    public List<T> getItems(String foreignKeyColumns, long foreignKeyValue) {
        List<JsonObject> rawItems = mDriver.getItems(foreignKeyColumns, foreignKeyValue);
        List<T> items = new ArrayList<>();
        for (JsonObject rawItem : rawItems) {
            items.add(mGson.fromJson(rawItem, mType));
        }
        return items;
    }

    public void create(T item) {
        JsonObject rawItem = convertItemToJson(item);
        mDriver.create(rawItem);
    }

    public void update(T oldItem, T newItem) {
        mDriver.update(convertItemToJson(oldItem), convertItemToJson(newItem));
    }

    public void delete(T item) {
        mDriver.delete(convertItemToJson(item));
    }

    private JsonObject convertItemToJson(T item) {
        return (JsonObject) mGson.toJsonTree(item, mType);
    }

}
