package com.despectra.githubrepoview;

import android.app.Application;

import com.despectra.githubrepoview.cache.db.DbUtils;
import com.despectra.githubrepoview.cache.db.DbStrategy;
import com.despectra.githubrepoview.cache.db.RealmStrategy;
import com.despectra.githubrepoview.cache.db.SQLiteStrategy;
import com.despectra.githubrepoview.sqlite.DatabaseHelper;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;

/**
 * Application class containing realm instantiation
 */
public class App extends Application {

    private RealmConfiguration mRealmConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        initDatabase();
    }

    private void initDatabase() {
        DbStrategy dbStrategy = DbUtils.getDefaultStrategy();
        if(dbStrategy instanceof RealmStrategy) {
            initRealm();
        }
    }

    private void initRealm() {
        mRealmConfig = new RealmConfiguration.Builder(this)
                .name("repoview.realm")
                .setModules(new RealmSchema())
                .build();
        Realm.setDefaultConfiguration(mRealmConfig);
    }

    public void dropDatabase() {
        DbStrategy dbStrategy = DbUtils.getDefaultStrategy();
        if(dbStrategy instanceof RealmStrategy) {
            Realm.deleteRealm(mRealmConfig);
        } else if (dbStrategy instanceof SQLiteStrategy) {
            deleteDatabase(DatabaseHelper.DATABASE_NAME);
        }
    }

    public RealmConfiguration getDefaultRealmConfiguration() {
        return mRealmConfig;
    }
}
