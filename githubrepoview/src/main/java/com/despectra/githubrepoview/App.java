package com.despectra.githubrepoview;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Application class containing realm instantiation
 */
public class App extends Application {

    private RealmConfiguration mRealmConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        initRealm();
    }

    private void initRealm() {
        mRealmConfig = new RealmConfiguration.Builder(this)
                .name("repoview.realm")
                .setModules(new RealmSchema())
                .build();
        Realm.setDefaultConfiguration(mRealmConfig);
    }

    public RealmConfiguration getDefaultRealmConfiguration() {
        return mRealmConfig;
    }
}
