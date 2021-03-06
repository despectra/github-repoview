package com.despectra.githubrepoview.cache;

import com.despectra.githubrepoview.SetOperations;
import com.despectra.githubrepoview.cache.db.DatabaseDao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Class for refreshing local cache (realm database)
 * @param <D> type of item
 * @param <K> type of unique key field
 */
public abstract class CacheSyncManager<D, K extends Comparable> {

    /**
     * Database write strategy implementation to perform write operations
     */
    private DatabaseDao<D> mDao;

    public CacheSyncManager(DatabaseDao<D> dao) {
        mDao = dao;
    }

    /**
     * Performs cache refreshing based on obtained items list from the network
     * Calculates three sets - updating items, creating items and deleting items
     * Performs database operations on each items of these sets
     * @param networkItems Items that just came from the network (result of API call)
     * @param localItems Items that are currently available in local database
     */
    public void sync(List<D> networkItems, List<D> localItems) {
        Map<K, D> networkItemsMap = getMapFromItemsList(networkItems);
        Map<K, D> localItemsMap = getMapFromItemsList(localItems);

        //simple sets operations helps a lot
        Set<K> toUpdate = SetOperations.intersection(networkItemsMap.keySet(), localItemsMap.keySet());
        Set<K> toCreate = SetOperations.difference(networkItemsMap.keySet(), localItemsMap.keySet());
        Set<K> toDelete = SetOperations.difference(localItemsMap.keySet(), networkItemsMap.keySet());

        mDao.open();
        mDao.beginTransaction();

        try {
            //create new
            for (K id : toCreate) {
                D newItem = createNewItemModel();
                onCreateLocalItem(newItem, networkItemsMap.get(id));
                mDao.create(newItem);
            }
            //update existing
            for (K id : toUpdate) {
                D existingItem = localItemsMap.get(id);
                onUpdateLocalItem(existingItem, networkItemsMap.get(id));
                mDao.update(existingItem, networkItemsMap.get(id));
            }
            //delete non-existing
            for (K id : toDelete) {
                D deletedItem = localItemsMap.get(id);
                mDao.delete(deletedItem);
            }
            mDao.commitTransaction();
        } catch(Exception e) {
            mDao.rollbackTransaction();
        }

        mDao.close();
    }

    /**
     * Creates a mapping UniqueKeyFieldValue -> Item from a list of items
     * @param items list of items
     * @return mapping
     */
    private Map<K, D> getMapFromItemsList(List<D> items) {
        Map<K, D> idsMap = new TreeMap<>();
        for(D item : items) {
            idsMap.put(getItemUniqueKey(item), item);
        }
        return idsMap;
    }

    /**
     * Returns primary key field value of given item
     * @param item given item
     * @return value of primary key
     */
    protected abstract K getItemUniqueKey(D item);

    /**
     * Performs all specific updating operations - assigning fields, calling setters etc.
     * @param localItem existing item in cache
     * @param networkItem item with data to load into localItem
     */
    protected abstract void onUpdateLocalItem(D localItem, D networkItem);

    /**
     * Performs all specific creating operations - assigning fields, calling setters etc.
     * @param localItem empty item that is going to be stored in cache
     * @param networkItem item with data to load into localItem
     */
    protected abstract void onCreateLocalItem(D localItem, D networkItem);

    /**
     * @return new item object
     */
    protected abstract D createNewItemModel();
}
