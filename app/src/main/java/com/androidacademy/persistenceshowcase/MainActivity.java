package com.androidacademy.persistenceshowcase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.androidacademy.persistenceshowcase.Database.FilmContract.FilmEntry;
import com.androidacademy.persistenceshowcase.Database.FilmDbHelper;
import com.androidacademy.persistenceshowcase.Models.Film;
import com.androidacademy.persistenceshowcase.Network.NetworkManager;
import com.androidacademy.persistenceshowcase.Network.StarWarsDataCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements StarWarsDataCallback {

    public static final String SHARED_PREF_NAME = "MY_SHARED_PREF";
    public static final String DATA_KEY = "DATA_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        restoreData();
        NetworkManager.getInstance().getData(this);
    }

    private void restoreData() {
        SQLiteDatabase db = FilmDbHelper.getInstance(this).getReadableDatabase();
        String[] projection = {
                FilmEntry.COLUMN_NAME_TITLE,
                FilmEntry.COLUMN_NAME_DESCRIPTION,
                FilmEntry.COLUMN_NAME_PIC_URL
        };
        Cursor cursor = db.query(
                FilmEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        List<Film> filmList = new ArrayList<>();
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String title = cursor.getString(
                        cursor.getColumnIndexOrThrow(FilmEntry.COLUMN_NAME_TITLE));
                String description = cursor.getString(
                        cursor.getColumnIndexOrThrow(FilmEntry.COLUMN_NAME_DESCRIPTION));
                String url = cursor.getString(
                        cursor.getColumnIndexOrThrow(FilmEntry.COLUMN_NAME_PIC_URL));
                filmList.add(new Film(title, description, url));
            }

            updateUI(filmList);
        }
        cursor.close();
        db.close();
    }

    @Override
    public void onDataReady(final List<Film> filmList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateUI(filmList);
            }
        });

    }


    @Override
    public void onError(final String message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showToast(message);
            }
        });
    }


    @MainThread
    private void updateUI(List<Film> filmList) {

        saveData(filmList);

        findViewById(R.id.pb_am_loading).setVisibility(View.GONE);

        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);

        MyAdapter adapter = new MyAdapter(filmList, MainActivity.this);
        recyclerView.setAdapter(adapter);
    }

    private void saveData(List<Film> filmList) {
        SQLiteDatabase db = FilmDbHelper.getInstance(this).getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (Film file : filmList) {
                values.put(FilmEntry.COLUMN_NAME_TITLE, file.getTitle());
                values.put(FilmEntry.COLUMN_NAME_DESCRIPTION, file.getDescription());
                values.put(FilmEntry.COLUMN_NAME_PIC_URL, file.getUrl());
                db.insert(FilmEntry.TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        db.close();
    }

    @MainThread
    private void showToast(String message) {
        Toast.makeText(MainActivity.this, "Unable to download data with following reason: " + message, Toast.LENGTH_LONG).show();
    }
}
