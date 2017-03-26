package com.facts.database;


import android.database.Cursor;
import android.database.CursorWrapper;
import android.graphics.Bitmap;

import com.facts.database.FactsDbSchema.FactsTable;

import com.facts.FactItem;

public class FactCursorWrapper extends CursorWrapper {

    public FactCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public FactItem getFact() {
        int id = getInt(getColumnIndex(FactsTable.Cols.UUID));
        String title = getString(getColumnIndex(FactsTable.Cols.TEXT));
        Bitmap img = DbBitmapUtility.getImage(getBlob(getColumnIndex(FactsTable.Cols.IMAGE_DATA)));
        FactItem factItem = new FactItem(id, title, null);
        factItem.setBitmap(img);
        return factItem;
    }
}
