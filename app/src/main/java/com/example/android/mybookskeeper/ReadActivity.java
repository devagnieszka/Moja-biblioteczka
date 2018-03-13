package com.example.android.mybookskeeper;

import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ReadActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private BookAdapter bookAdapter;
    private Uri uri;
    private String isRead="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        Intent i= getIntent();
        Bundle b = i.getExtras();


        if(b!=null)
        {
            isRead =(String) b.get("isRead");
        }


        if(isRead.equals("1")) {
            setTitle(R.string.read);
        }
        else {
            setTitle(R.string.wannaRead);

        }

        ListView bookItems = findViewById(R.id.bookItems);


        FloatingActionButton addBook = (FloatingActionButton) findViewById(R.id.add);
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReadActivity.this, EditModeActivity.class);
                startActivity(intent);
            }
        });

        bookItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent = new Intent(ReadActivity.this, EditModeActivity.class);
                uri = ContentUris.withAppendedId(BookContract.BookEntry.CONTENT_URI, id);
                intent.setData(uri);
                startActivity(intent);
            }
        });


        bookAdapter = new BookAdapter(this, null );
        bookItems.setAdapter(bookAdapter);
        registerForContextMenu(bookItems);

        getSupportLoaderManager().initLoader(0, null, this);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String []  projection = {BookContract.BookEntry._ID, BookContract.BookEntry.COLUMN_BOOK_TITLE, BookContract.BookEntry.COLUMN_BOOK_AUTHOR};
        String selection = BookContract.BookEntry.COLUMN_BOOK_ISREAD+ "=?";
        String [] selectionArgs;
        if(isRead.equals("1")) {
            selectionArgs=  new String[] { String.valueOf(BookContract.BookEntry.ISREAD_YES)};
        }
        else {
            selectionArgs=  new String[] { String.valueOf(BookContract.BookEntry.ISREAD_NO)};

        }

        String sOrderBy;
        Cursor cursor;
        switch (item.getItemId()) {

            case R.id.options_menu_delete_all:
                DeleteAllBooks();
                return true;

            case R.id.by_title:
                sOrderBy = BookContract.BookEntry.COLUMN_BOOK_TITLE + " ASC";
                cursor = getContentResolver().query(BookContract.BookEntry.CONTENT_URI, projection, selection, selectionArgs, sOrderBy);
                bookAdapter.swapCursor(cursor);
                return true;

            case R.id.by_author:
                sOrderBy = BookContract.BookEntry.COLUMN_BOOK_AUTHOR + " ASC";
                cursor = getContentResolver().query(BookContract.BookEntry.CONTENT_URI, projection, selection, selectionArgs, sOrderBy);
                bookAdapter.swapCursor(cursor);
                return true;

            case R.id.by_rating:
               sOrderBy = BookContract.BookEntry.COLUMN_BOOK_RATING + " DESC";
                cursor = getContentResolver().query(BookContract.BookEntry.CONTENT_URI, projection, selection, selectionArgs, sOrderBy);
                bookAdapter.swapCursor(cursor);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);



    }



    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        uri = ContentUris.withAppendedId(BookContract.BookEntry.CONTENT_URI, info.id);
        switch (item.getItemId()) {
            case R.id.context_menu_edit:
                EditBook();
                return true;
            case R.id.context_menu_delete:
                DeleteBook();
                return true;
            case R.id.context_menu_share:
                ShareSMS();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    public void EditBook() {
        Intent intent = new Intent(ReadActivity.this, EditModeActivity.class);
        intent.setData(uri);
        startActivity(intent);

    }

    public void DeleteBook() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.deleteConfirm);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if (uri != null) {
                    getContentResolver().delete(uri, null, null);
                }
            }
        });
        builder.setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void DeleteAllBooks() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.deleteConfirm);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String selection = BookContract.BookEntry.COLUMN_BOOK_ISREAD+ "=?";
                String [] selectionArgs;
                if(isRead.equals("1")) {
                    selectionArgs=  new String[] { String.valueOf(BookContract.BookEntry.ISREAD_YES)};
                }
                else {
                    selectionArgs=  new String[] { String.valueOf(BookContract.BookEntry.ISREAD_NO)};

                }
                getContentResolver().delete(BookContract.BookEntry.CONTENT_URI, selection, selectionArgs);

            }
        });
        builder.setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }



    public void ShareSMS() {
        String []  projection = {BookContract.BookEntry._ID, BookContract.BookEntry.COLUMN_BOOK_TITLE, BookContract.BookEntry.COLUMN_BOOK_AUTHOR};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        String author="";
        String title="";

        if(cursor != null && cursor.moveToFirst()) {
            int titleColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_TITLE);
            int authorColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_AUTHOR);

            title = cursor.getString(titleColumnIndex);
            author = cursor.getString(authorColumnIndex);

        }

        String message = getString(R.string.recommend)  + title + " " + author;

        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setData(Uri.parse("smsto:"));
        intent.putExtra("sms_body",message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        cursor.close();

    }
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String []  projection = {BookContract.BookEntry._ID, BookContract.BookEntry.COLUMN_BOOK_TITLE, BookContract.BookEntry.COLUMN_BOOK_AUTHOR};
        String selection = BookContract.BookEntry.COLUMN_BOOK_ISREAD+ "=?";
        String [] selectionArgs;
        if(isRead.equals("1")) {
            selectionArgs=  new String[] { String.valueOf(BookContract.BookEntry.ISREAD_YES)};
        }
        else {
            selectionArgs=  new String[] { String.valueOf(BookContract.BookEntry.ISREAD_NO)};

        }





        return new CursorLoader(this,  BookContract.BookEntry.CONTENT_URI,
                projection, selection, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
       bookAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        bookAdapter.swapCursor(null);

    }
}
