package com.example.android.mybookskeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Agnieszka on 15.02.2018.
 */

public class BooksDbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + BookContract.BookEntry.TABLE_NAME + " (" +
                    BookContract.BookEntry._ID + " INTEGER PRIMARY KEY," +
                    BookContract.BookEntry.COLUMN_BOOK_TITLE + TEXT_TYPE + COMMA_SEP +
                    BookContract.BookEntry.COLUMN_BOOK_AUTHOR + TEXT_TYPE + COMMA_SEP +
                    BookContract.BookEntry.COLUMN_BOOK_ISREAD + TEXT_TYPE + COMMA_SEP +
                    BookContract.BookEntry.COLUMN_BOOK_RATING + TEXT_TYPE +
                    " );";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + BookContract.BookEntry.TABLE_NAME;



    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "books.db";

    public BooksDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
