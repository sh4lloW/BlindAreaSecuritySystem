package com.example.blindareasecuritysystem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.LayoutTransition;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.VideoView;

import com.example.blindareasecuritysystem.R;

public class BlindAreaDisplayActivity extends AppCompatActivity {

    private VideoView videoView;

    private LinearLayout container;
    private LayoutTransition transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blind_area_display);

    }
}