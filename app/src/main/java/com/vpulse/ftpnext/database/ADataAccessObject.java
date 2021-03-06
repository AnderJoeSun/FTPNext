package com.vpulse.ftpnext.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;

import com.vpulse.ftpnext.core.LogManager;

import java.util.ArrayList;
import java.util.List;

public abstract class ADataAccessObject<T extends ABaseTable> extends ADataBaseSQLiteHelper {

    private final String TAG = "DATABASE : Data Access Object";
    protected ContentValues mContentValues = null;
    protected Cursor mCursor = null;

    public ADataAccessObject(SQLiteDatabase iDataBase) {
        super(iDataBase);
    }

    public abstract T fetchById(int iId);

    public abstract List<T> fetchAll();

    public abstract int add(T iObject);

    public abstract boolean update(T iObject);

    public abstract boolean deleteAll();

    public abstract boolean delete(int iId);

    public abstract boolean delete(T iObject);

    public abstract void onUpgradeTable(int iOldVersion, int iNewVersion);

    protected abstract void setContentValue(T iObject);

    protected abstract T cursorToEntity(Cursor iCursor);

    protected T fetchById(String iTable, int iId, String iColumnDataBaseId, String[] iColumns) {
        final String[] lSelectionArgs = {String.valueOf(iId)};
        final String lSelection = iColumnDataBaseId + " = ?";
        T lObject = null;

        mCursor = super.query(iTable, iColumns, lSelection, lSelectionArgs, iColumnDataBaseId);
        if (mCursor != null) {
            mCursor.moveToFirst();
            while (!mCursor.isAfterLast()) {
                lObject = cursorToEntity(mCursor);
                mCursor.moveToNext();
            }
            mCursor.close();
        }
        return lObject;
    }

    protected List<T> fetchAll(String iTable, String[] iColumns, String iColumnDataBaseId) {
        List<T> lList = new ArrayList<>();

        mCursor = super.query(iTable, iColumns, null, null, iColumnDataBaseId);
        if (mCursor != null) {
            mCursor.moveToFirst();
            while (!mCursor.isAfterLast()) {
                lList.add(cursorToEntity(mCursor));
                mCursor.moveToNext();
            }
            mCursor.close();
        }
        return lList;
    }

    protected int add(T iObject, String iTable) {
        setContentValue(iObject);

        try {
            int lNewId = (int) super.insert(iTable, mContentValues);
            iObject.setDataBaseId(lNewId);
            return lNewId;
        } catch (SQLiteConstraintException iEx) {
            LogManager.error(TAG, "Add error: " + iEx.getMessage());
            return -1;
        }
    }

    protected boolean delete(int iId, String iTable, String iColumnDataBaseId) {
        final String selection = " " + iColumnDataBaseId + " =" + iId;
        return super.delete(iTable, selection, null) > 0;
    }

    protected boolean update(T iObject, int iId, String iTable, String iColumnDataBaseId) {
        synchronized (ADataAccessObject.class) {
            setContentValue(iObject);

            try {
                final String lSelection = iColumnDataBaseId + " = ?";
                final String[] lSelectionArgs = {String.valueOf(iId)};

                return super.update(iTable, mContentValues, lSelection, lSelectionArgs) > 0;
            } catch (SQLiteConstraintException iEx) {
                return LogManager.error(TAG, "Update error :\nThread : " +
                        Thread.currentThread().getId() + "\n" + iEx.getMessage() + "\n" + iObject.toString()); //error
            }
        }
    }
}
