package com.example.blindareasecuritysystem.Activity;

import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blindareasecuritysystem.Entity.Point;
import com.example.blindareasecuritysystem.R;
import com.example.blindareasecuritysystem.Utils.LoadData;

import java.util.ArrayList;
import java.util.List;

public class LightActivity extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        // 绑定
        videoView = findViewById(R.id.video_view);

        // 加载指定路径的视频
        // 如果要修改的话就把视频文件拖进raw包下，然后修改下行最后的视频名字
        String path = "android.resource://" + getPackageName() + "/" + R.raw.test;
        videoView.setVideoPath(path);
        // 创建MediaController，建立关联
        MediaController mediaController = new MediaController(LightActivity.this);
        videoView.setMediaController(mediaController);
        // 获取焦点
        videoView.requestFocus();
        videoView.start();

        // 双盲区的坐标数据
        List<Point> rightAreaList = new ArrayList<>();
        List<Point> leftAreaList = new ArrayList<>();
        // 填充数据
        LoadData.loadRightBlindAreaData(rightAreaList);
        LoadData.loadLeftBlindAreaData(leftAreaList);
        // 进行判定
        for (int i = 0; i < rightAreaList.size() && i < leftAreaList.size(); i++) {
            // 如果左右盲区超过一定范围
            if (rightAreaList.get(i).getX() > 2.5 && rightAreaList.get(i).getY() > 5.8 && leftAreaList.get(i).getX() > 2.5 && leftAreaList.get(i).getY() > 5.8) {
                // 弹出警告信息
                Toast.makeText(LightActivity.this, "Warning!", Toast.LENGTH_LONG).show();
                break;
            }
            // 每3秒进行一次盲区鉴定，让线程睡3秒
            // 这里如果为了精确且保证竞争到资源，可以用Timer +TimeTask结合的形式，但在后台运行会有影响
            new Thread(() -> {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }

}