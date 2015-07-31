package com.despectra.githubrepoview.cache.db;

import android.content.Context;

import java.util.List;
import java.util.Map;

import io.realm.RealmObject;

/**
 * Interface for performing write operations on generic database
 * Implement it for custom database driver (sqlite, realm etc.)
 */
public interface DbStrategy {

    List getByColumnsValues(Class<?> modelClass, String[] columns, Object[] values);

    /**
     * Opens database connection
     * @param context for database-specific operations
     */
    void open(Context context);

    /**
     * Begins database transaction
     */
    void beginTransaction();

    /**
     * creates new record in database with data from given object
     * @param item given object
     */
    void create(Object item);

    /**
     * Obtains next generated id column value for given model type
     * Use it to set id field value to model object
     * @param type model type
     * @return next id
     */
    long getNextItemId(Class type);

    /**
     * updates old database object with new one
     * @param oldItem old item
     * @param newItem updated item
     */
    void update(Object oldItem, Object newItem);

    /**
     * deletes given item from database
     * @param item given item
     */
    void delete(Object item);

    void commitTransaction();

    void rollbackTransaction();

    /**
     * closes database connection
     */
    void close();
}
