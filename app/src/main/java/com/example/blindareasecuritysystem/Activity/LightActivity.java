package com.example.blindareasecuritysystem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.blindareasecuritysystem.R;

public class LightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        // 警告提示
        new Thread(() -> {
            
        }).start();
    }
}