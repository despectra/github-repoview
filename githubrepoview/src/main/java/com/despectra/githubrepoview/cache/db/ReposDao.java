package com.despectra.githubrepoview.cache.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.despectra.githubrepoview.models.Repo;
import com.despectra.githubrepoview.sqlite.ReposTable;

/**
 * Concrete DatabaseDao implementation for repos table
 */
public class ReposDao extends DatabaseDao<Repo> {

    public ReposDao(Context context) {
        super(context);
    }

    @Override
    protected void bindToInsert(Repo repo, SQLiteStatement insertStmt) {
        insertStmt.bindLong(1, repo.getId());
        insertStmt.bindLong(2, repo.getOwnerId());
        insertStmt.bindString(3, repo.getName());
        insertStmt.bindString(4, repo.getDescription());
        insertStmt.bindString(5, String.valueOf(repo.getForksCount()));
        insertStmt.bindString(6, String.valueOf(repo.getStargazersCount()));
        insertStmt.bindString(7, String.valueOf(repo.getWatchersCount()));
    }

    @Override
    protected void bindToUpdate(Repo oldRepo, Repo newRepo, SQLiteStatement updateStmt) {
        updateStmt.bindLong(1, newRepo.getOwnerId());
        updateStmt.bindString(2, newRepo.getName());
        updateStmt.bindString(3, newRepo.getDescription());
        updateStmt.bindString(4, String.valueOf(newRepo.getForksCount()));
        updateStmt.bindString(5, String.valueOf(newRepo.getStargazersCount()));
        updateStmt.bindString(6, String.valueOf(newRepo.getWatchersCount()));
        updateStmt.bindLong(7, oldRepo.getId());
    }

    @Override
    protected void bindToDelete(Repo repo, SQLiteStatement deleteStmt) {
        deleteStmt.bindLong(1, repo.getId());
    }

    @Override
    protected String getTableName() {
        return ReposTable.NAME;
    }

    @Override
    protected String[] getTableColumns() {
        return ReposTable.ALL_COLUMNS;
    }

    @Override
    protected Repo getItemFromCursor(Cursor cursor) {
        Repo repo = new Repo();
        repo.setId(cursor.getLong(cursor.getColumnIndexOrThrow(ReposTable.COLUMN_ID)));
        repo.setOwnerId(cursor.getLong(cursor.getColumnIndexOrThrow(ReposTable.COLUMN_OWNER_ID)));
        repo.setName(cursor.getString(cursor.getColumnIndexOrThrow(ReposTable.COLUMN_NAME)));
        repo.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(ReposTable.COLUMN_DESCRIPTION)));
        repo.setForksCount(cursor.getInt(cursor.getColumnIndexOrThrow(ReposTable.COLUMN_FORKS_COUNT)));
        repo.setStargazersCount(cursor.getInt(cursor.getColumnIndexOrThrow(ReposTable.COLUMN_STARS_COUNT)));
        repo.setWatchersCount(cursor.getInt(cursor.getColumnIndexOrThrow(ReposTable.COLUMN_WATCHERS_COUNT)));
        return repo;
    }

    @Override
    protected String getInsertSql() {
        return ReposTable.SQL_INSERT;
    }

    @Override
    protected String getUpdateSql() {
        return ReposTable.SQL_UPDATE;
    }

    @Override
    protected String getDeleteSql() {
        return ReposTable.SQL_DELETE;
    }
}
