package com.facts.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.facts.FactItems;
import com.facts.database.FactCursorWrapper;
import com.facts.database.FactsDbSchema.FactsTable;

import com.facts.FactItem;
import com.facts.database.FactsBaseHelper;

public class SQLiteFactsLoader implements IFactsLoader {
    private FactsLoaderCallbacks mObserver = null;
    private static SQLiteFactsLoader sInstance;
    private Context mContext;

    private SQLiteDatabase mDatabase;
    public static SQLiteFactsLoader getInstance(Context ctx) {
        if (sInstance == null) {
            sInstance = new SQLiteFactsLoader(ctx);
        }
        return sInstance;
    }

    private SQLiteFactsLoader(Context ctx) {
        mContext = ctx.getApplicationContext();
        mDatabase = new FactsBaseHelper(mContext).getWritableDatabase();
    }

    public void setObserver(FactsLoaderCallbacks observer) {
        mObserver = observer;
    }

    private static ContentValues getContentValues(FactItem item) {
        ContentValues values = new ContentValues();
        values.put(FactsTable.Cols.UUID, item.getID());
        values.put(FactsTable.Cols.TEXT, item.getContent());
        return values;
    }

    public void saveFact(FactItem item) {
        ContentValues values = getContentValues(item);
        mDatabase.insert(FactsTable.NAME, null, values);
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
}
