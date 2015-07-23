package com.despectra.githubrepoview.local;

import com.despectra.githubrepoview.SetOperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Class for refreshing local cache (realm database)
 * @param <D> type of item
 * @param <K> type of key field
 */
public abstract class CacheSyncManager<D extends RealmObject, K> {

    private Realm mRealm;
    private Class<D> mItemClassParameter;

    public CacheSyncManager(Class<D> itemClass, Realm realm) {
        mRealm = Realm.getDefaultInstance();
        mItemClassParameter = itemClass;
    }

    public Realm getRealm() {
        return mRealm;
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

        Set<K> toUpdate = SetOperations.intersection(networkItemsMap.keySet(), localItemsMap.keySet());
        Set<K> toCreate = SetOperations.difference(networkItemsMap.keySet(), localItemsMap.keySet());
        Set<K> toDelete = SetOperations.difference(localItemsMap.keySet(), networkItemsMap.keySet());

        mRealm.beginTransaction();
        for(K id : toCreate) {
            D newItem = mRealm.createObject(mItemClassParameter);
            onCreateLocalItem(newItem, networkItemsMap.get(id));
            if(localItems instanceof RealmList) {
                localItems.add(newItem);
            }
        }
        for(K id : toUpdate) {
            D existingFriend = localItemsMap.get(id);
            onUpdateLocalItem(existingFriend, networkItemsMap.get(id));
        }
        for(K id : toDelete) {
            D deletedUser = localItemsMap.get(id);
            deletedUser.removeFromRealm();
        }

        mRealm.commitTransaction();
        mRealm.close();
    }

    /**
     * Creates a mapping PrimaryKey -> Item from a list of items
     * @param items list of items
     * @return mapping
     */
    private Map<K, D> getMapFromItemsList(List<D> items) {
        Map<K, D> idsMap = new HashMap<>();
        for(D item : items) {
            idsMap.put(getItemPrimaryKey(item), item);
        }
        return idsMap;
    }

    /**
     * Returns primary key field value of given item
     * @param item given item
     * @return value of primary key
     */
    protected abstract K getItemPrimaryKey(D item);

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
}
