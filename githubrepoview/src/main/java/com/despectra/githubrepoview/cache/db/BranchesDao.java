package com.despectra.githubrepoview.cache.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import com.despectra.githubrepoview.models.Branch;
import com.despectra.githubrepoview.sqlite.BranchesTable;

/**
 * Concrete DatabaseDao implementation for branches table
 */
public class BranchesDao extends DatabaseDao<Branch> {

    public BranchesDao(Context context) {
        super(context);
    }

    @Override
    protected void bindToInsert(Branch branch, SQLiteStatement insertStmt) {
        insertStmt.bindLong(1, branch.getRepoId());
        insertStmt.bindString(2, branch.getName());
    }

    @Override
    protected void bindToUpdate(Branch oldBranch, Branch newBranch, SQLiteStatement updateStmt) {
        updateStmt.bindString(1, newBranch.getName());
        updateStmt.bindLong(2, oldBranch.getRepoId());
        updateStmt.bindString(3, oldBranch.getName());
    }

    @Override
    protected void bindToDelete(Branch branch, SQLiteStatement deleteStmt) {
        deleteStmt.bindLong(1, branch.getId());
    }

    @Override
    protected String getTableName() {
        return BranchesTable.NAME;
    }

    @Override
    protected String[] getTableColumns() {
        return BranchesTable.ALL_COLUMNS;
    }

    @Override
    protected Branch getItemFromCursor(Cursor cursor) {
        Branch branch = new Branch();
        branch.setId(cursor.getLong(cursor.getColumnIndexOrThrow(BranchesTable.COLUMN_ID)));
        branch.setRepoId(cursor.getLong(cursor.getColumnIndexOrThrow(BranchesTable.COLUMN_REPO_ID)));
        branch.setName(cursor.getString(cursor.getColumnIndexOrThrow(BranchesTable.COLUMN_NAME)));
        return branch;
    }

    @Override
    protected String getInsertSql() {
        return BranchesTable.SQL_INSERT;
    }

    @Override
    protected String getUpdateSql() {
        return BranchesTable.SQL_UPDATE;
    }

    @Override
    protected String getDeleteSql() {
        return BranchesTable.SQL_DELETE;
    }
}
