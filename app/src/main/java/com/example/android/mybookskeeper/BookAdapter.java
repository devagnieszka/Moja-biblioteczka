package com.example.android.mybookskeeper;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by Agnieszka on 15.02.2018.
 */

public class BookAdapter extends CursorAdapter {

    public BookAdapter(Context context, Cursor c) {
        super(context, c, 0 /* flags */);
    }


    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);

    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        TextView bookTitle = view.findViewById(R.id.bookTitle);
        TextView bookAuthor= view.findViewById(R.id.bookAuthor);

        String sBookTitle= cursor.getString(cursor.getColumnIndexOrThrow(BookContract.BookEntry.COLUMN_BOOK_TITLE));
        String sBookAuthor = cursor.getString(cursor.getColumnIndexOrThrow(BookContract.BookEntry.COLUMN_BOOK_AUTHOR));

        bookTitle.setText(sBookTitle);
        bookAuthor.setText(sBookAuthor);

    }


}
