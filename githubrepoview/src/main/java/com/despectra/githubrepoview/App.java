package com.despectra.githubrepoview;

import android.app.Application;

import com.despectra.githubrepoview.sqlite.DatabaseHelper;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Application class containing realm instantiation
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void dropDatabase() {
        deleteDatabase(DatabaseHelper.DATABASE_NAME);
    }
}
