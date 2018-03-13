package com.example.android.mybookskeeper;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Agnieszka on 15.02.2018.
 */

public class BookContract {

    private BookContract() {}
    public static final String CONTENT_AUTHORITY = "com.example.android.mybookskeeper";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_BOOKS = "books";


    public static final class BookEntry implements BaseColumns {

        public static final String TABLE_NAME = "books";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BOOKS);

        public static final String _ID= BaseColumns._ID;
        public static final String COLUMN_BOOK_TITLE= "title";
        public static final String COLUMN_BOOK_AUTHOR= "author";
        public static final String COLUMN_BOOK_ISREAD= "isread";
        public static final String COLUMN_BOOK_RATING= "rating";


        public static final int ISREAD_YES = 1;
        public static final int ISREAD_NO = 0;

        public static final int RATING_0 = 0;
        public static final int RATING_1 = 1;
        public static final int RATING_2 = 2;
        public static final int RATING_3 = 3;
        public static final int RATING_4 = 4;
        public static final int RATING_5 = 5;






        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;


        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BOOKS;

    }

}
