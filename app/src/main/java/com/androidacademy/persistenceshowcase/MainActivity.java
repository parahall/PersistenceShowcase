package com.androidacademy.persistenceshowcase;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.androidacademy.persistenceshowcase.Models.Film;
import com.androidacademy.persistenceshowcase.Network.NetworkManager;
import com.androidacademy.persistenceshowcase.Network.StarWarsDataCallback;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
        SharedPreferences mySharedPref = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Film>>() {
        }.getType();

        String string = mySharedPref.getString(DATA_KEY, "");
        if (!TextUtils.isEmpty(string)) {
            List<Film> films = gson.fromJson(string, listType);
            updateUI(films);
        }
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
        SharedPreferences mySharedPref = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(filmList);
        SharedPreferences.Editor editor = mySharedPref.edit();
        editor.putString(DATA_KEY, json);
        editor.apply();
    }

    @MainThread
    private void showToast(String message) {
        Toast.makeText(MainActivity.this, "Unable to download data with following reason: " + message, Toast.LENGTH_LONG).show();
    }
}
