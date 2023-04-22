package com.example.blindareasecuritysystem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.blindareasecuritysystem.R;

public class HiCarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setShowWhenLocked(true);
        setContentView(R.layout.activity_hicar);
    }
}