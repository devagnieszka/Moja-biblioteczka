package com.example.android.mybookskeeper;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class EditModeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    EditText editTitle;
    EditText editAuthor;
    TextView textViewRating;
    Switch switchIsRead;
    RatingBar ratingBar;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mode);

        uri = getIntent().getData();

        if(uri!=null) {
            setTitle(R.string.editBook);
            getSupportLoaderManager().initLoader(0, null, this);

        }
        else {
            setTitle(R.string.addBook);

        }

       editTitle = findViewById(R.id.edit_book_title);
       editAuthor = findViewById(R.id.edit_book_author);


       switchIsRead =  findViewById(R.id.isReadSwitch);

        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveBook();

            }
        });

        textViewRating =  findViewById(R.id.textViewRating);
        CheckSwitch();

        switchIsRead.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            CheckSwitch();
            }
        });

        ratingBar= findViewById(R.id.ratingBar);


    }

    private void CheckSwitch() {
        if (switchIsRead.isChecked())
            textViewRating.setText(getString(R.string.rating));
        else     textViewRating.setText(getString(R.string.rating2));
    }



    private void SaveBook() {
        String sEditTitle = editTitle.getText().toString().trim();
        String sEditAuthor = editAuthor.getText().toString().trim();
        if (TextUtils.isEmpty(sEditAuthor) || TextUtils.isEmpty(sEditTitle) ) {
            Toast.makeText(this, getString(R.string.fill), Toast.LENGTH_SHORT).show();
            return;
        }
        int isRead;
        if (switchIsRead.isChecked())
            isRead = BookContract.BookEntry.ISREAD_YES;
        else   isRead = BookContract.BookEntry.ISREAD_NO;

        int rating = (int)ratingBar.getRating();
        ContentValues values = new ContentValues();
        values.put(BookContract.BookEntry.COLUMN_BOOK_TITLE, sEditTitle);
        values.put(BookContract.BookEntry.COLUMN_BOOK_AUTHOR, sEditAuthor);
        values.put(BookContract.BookEntry.COLUMN_BOOK_ISREAD, isRead);
        values.put(BookContract.BookEntry.COLUMN_BOOK_RATING, rating);

        if(uri!=null) {
            getContentResolver().update(uri, values, null, null);
        }
        else {
            uri = getContentResolver().insert(BookContract.BookEntry.CONTENT_URI, values);
        }



        finish();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,  uri,
                null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        String author="";
        String title="";
        int rating=0;
        int isRead=0;

        if(cursor != null && cursor.moveToFirst()) {
            int titleColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_TITLE);
            int authorColumnIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_AUTHOR);
            int isReadIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_ISREAD);
            int ratingIndex = cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOK_RATING);

            title = cursor.getString(titleColumnIndex);
            author = cursor.getString(authorColumnIndex);
            isRead=cursor.getInt(isReadIndex);
            rating = cursor.getInt(ratingIndex);

        }

        editAuthor.setText(author);
        editTitle.setText(title);

        if (isRead==1) switchIsRead.setChecked(true);
        ratingBar.setRating((float)rating);



    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        editAuthor.setText("");
        editTitle.setText("");
        switchIsRead.setChecked(false);

    }
}
