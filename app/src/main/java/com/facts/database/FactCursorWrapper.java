package com.facts.database;


import android.database.Cursor;
import android.database.CursorWrapper;
import com.facts.database.FactsDbSchema.FactsTable;

import com.facts.FactItem;

public class FactCursorWrapper extends CursorWrapper {

    public FactCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public FactItem getFact() {
        int id = getInt(getColumnIndex(FactsTable.Cols.UUID));
        String title = getString(getColumnIndex(FactsTable.Cols.TEXT));

        return new FactItem(id, title, null);
    }
}
