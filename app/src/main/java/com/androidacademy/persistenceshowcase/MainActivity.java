package com.androidacademy.persistenceshowcase;

import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.androidacademy.persistenceshowcase.Database.AppDatabase;
import com.androidacademy.persistenceshowcase.Models.Film;
import com.androidacademy.persistenceshowcase.Network.NetworkManager;
import com.androidacademy.persistenceshowcase.Network.StarWarsDataCallback;

import java.util.List;

public class MainActivity extends BaseActivity implements StarWarsDataCallback {

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
        AppDatabase db = AppDatabase.getAppDatabase(this);
        List<Film> filmList = db.filmDao().getAll();
        if (filmList != null && filmList.size() > 0) {
            updateUI(filmList);
        }
    }

    @Override
    public void onDataReady(final List<Film> filmList) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                saveData(filmList);
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
        AppDatabase db = AppDatabase.getAppDatabase(this);
        db.filmDao().deleteAll();
        db.filmDao().insertAll(filmList.toArray(new Film[filmList.size()]));
    }

    @MainThread
    private void showToast(String message) {
        Toast.makeText(MainActivity.this, "Unable to download data with following reason: " + message, Toast.LENGTH_LONG).show();
    }
}
