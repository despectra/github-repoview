package com.despectra.githubrepoview.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

/**
 * Loader for loading items from local cache (realm database)
 */
public abstract class LocalLoader<D> extends AsyncTaskLoader<List<D>> {

    public LocalLoader(Context context) {
        super(context);
    }

    /**
     * Loads items list from the realm database
     * Copies query result to new list, just in order to deliver result to UI thread
     * @return list of items
     */
    @Override
    public List<D> loadInBackground() {
        Realm realm = Realm.getDefaultInstance();
        ArrayList<D> copiedItems = new ArrayList<>();
        try {
            List<D> realmItems = tryLoadData(realm);
            for (D item : realmItems) {
                D copiedItem = copyRealmItem(item);
                copiedItems.add(copiedItem);
            }
        } finally {
            realm.close();
        }

        return copiedItems;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    /**
     * Performs a query to realm database
     * @param realm instance of realm
     * @return
     */
    protected abstract List<D> tryLoadData(Realm realm);

    /**
     * Copies realm result item into new item of the same type
     * @param item realm result item
     * @return new item
     */
    protected abstract D copyRealmItem(D item);
}
