package com.example.blindareasecuritysystem;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HiCarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setShowWhenLocked(true);
        setContentView(R.layout.activity_hicar);
    }
}