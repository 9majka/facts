package com.facts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.facts.database.FactsDbSchema.FactsTable;

public class FactsBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 2;
    private static final String DATABASE_NAME = "factsFavoriteBase.db";

    public FactsBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + FactsTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                FactsTable.Cols.UUID + ", " +
                FactsTable.Cols.TEXT + " TEXT, " +
                FactsTable.Cols.IMAGE_DATA + " BLOB" +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FactsTable.NAME);

        // create new table
        onCreate(sqLiteDatabase);
    }
}
