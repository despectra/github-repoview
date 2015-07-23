package com.despectra.githubrepoview.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.despectra.githubrepoview.adapters.ListAdapter;

import io.realm.RealmObject;

/**
 * Abstract activity that renders items into RecyclerView
 * @param <D> item type parameter
 */
public abstract class ItemsListActivity<D extends RealmObject> extends AppCompatActivity {

    private RecyclerView mItemsView;
    private ListAdapter mItemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
