package com.androidacademy.persistenceshowcase;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("XXX", Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("XXX", Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("XXX", Thread.currentThread().getStackTrace()[2].getMethodName());
    }

    @Override
    protected void onRestart() {
        Log.d("XXX", Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onRestart();
    }

    @Override
    protected void onStop() {
        Log.d("XXX", Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("XXX", Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Log.d("XXX", Thread.currentThread().getStackTrace()[2].getMethodName());
        super.onPause();
    }
}
