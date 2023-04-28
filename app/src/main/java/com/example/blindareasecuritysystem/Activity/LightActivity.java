package com.example.blindareasecuritysystem.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.blindareasecuritysystem.R;

public class LightActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        // 动图要用Glide加载，把下面的注释取消然后在load()中填入gif的路径
        imageView = findViewById(R.id.red_blink);
//        Glide.with(LightActivity.this)
//                .load()
//                .asGif()
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                .into(imageView);
        new Thread(() -> {
            for (int i = 0; i < 4; i++) {

            }
        }).start();
    }
}