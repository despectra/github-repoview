package com.despectra.githubrepoview;

import android.app.Application;

import com.despectra.githubrepoview.sqlite.DatabaseHelper;

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
