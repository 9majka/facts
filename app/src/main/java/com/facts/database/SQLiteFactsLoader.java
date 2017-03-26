package com.facts.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.facts.FactItems;
import com.facts.R;
import com.facts.database.FactsDbSchema.FactsTable;

import com.facts.FactItem;
import com.facts.model.FactsLoaderCallbacks;
import com.facts.model.IFactsLoader;

import java.lang.ref.WeakReference;

public class SQLiteFactsLoader implements IFactsLoader {
    private FactsLoaderCallbacks mObserver = null;
    private static SQLiteFactsLoader sInstance;
    private WeakReference<Context> mContext;

    private SQLiteDatabase mDatabase;
    public static SQLiteFactsLoader getInstance(Context ctx) {
        if (sInstance == null) {
            sInstance = new SQLiteFactsLoader(ctx);
        }
        return sInstance;
    }

    private SQLiteFactsLoader(Context ctx) {
        mContext = new WeakReference<Context> (ctx.getApplicationContext());
        mDatabase = new FactsBaseHelper(mContext.get()).getWritableDatabase();
    }

    public void setObserver(FactsLoaderCallbacks observer) {
        mObserver = observer;
    }

    private static ContentValues getContentValues(FactItem item) {
        ContentValues values = new ContentValues();
        values.put(FactsTable.Cols.UUID, item.getID());
        values.put(FactsTable.Cols.TEXT, item.getContent());
        values.put(FactsTable.Cols.IMAGE_DATA, DbBitmapUtility.getBytes(item.getBitmap()));
        return values;
    }

    public void saveFact(FactItem item) {
        ContentValues values = getContentValues(item);
        mDatabase.insert(FactsTable.NAME, null, values);
    }

    public void deleteFact(FactItem item) {
        mDatabase.delete(FactsTable.NAME, FactsTable.Cols.UUID +  "=" + item.getID(), null);
    }

    private FactCursorWrapper queryCrimes(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                FactsTable.NAME,
                null, // Columns - null all col
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null // orderBy
        );
        return new FactCursorWrapper(cursor);
    }

    public void loadNew(int from) {
        //
        FactItems factItems = new FactItems();
        FactCursorWrapper wrapper  = queryCrimes(null, null);
        try {
            wrapper.moveToFirst();
            while (!wrapper.isAfterLast()) {
                factItems.add(wrapper.getFact());
                wrapper.moveToNext();
            }
        } finally {
            wrapper.close();
        }

        if (mObserver != null) {
            mObserver.onFactsCreated(factItems);
        }
    }

    public void loadOffline(int from) {
        FactItems factItems = new FactItems();
        testData(factItems);
        if (mObserver != null) {
            mObserver.onFactsCreated(factItems);
        }
    }

    static boolean swap = true;
    private void testData(FactItems items) {
        for (int i = 0; i < 25; i++) {
            if(swap) {
                items.add(new FactItem(i, mContext.get().getResources().getString(R.string.Lorem), null));
            }
            else {
                items.add(new FactItem(i, mContext.get().getResources().getString(R.string.Lorem1), null));
            }
            swap = !swap;
        }
    }

}
