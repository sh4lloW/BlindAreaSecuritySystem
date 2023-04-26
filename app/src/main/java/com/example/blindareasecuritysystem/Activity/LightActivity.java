package com.example.blindareasecuritysystem.Activity;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blindareasecuritysystem.Entity.Point;
import com.example.blindareasecuritysystem.R;
import com.example.blindareasecuritysystem.Utils.LoadData;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

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

        // 判定任务在子线程中完成
        new Thread(() -> {
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
                    // Looper这两行必须加，这样才能创建消息队列让toast在线程中完成
                    Looper.prepare();
                    // 弹出警告信息
                    Toast.makeText(LightActivity.this, "Warning!", Toast.LENGTH_LONG).show();
                    Looper.loop();
                    break;
                }
                // 每3s对数据进行一次检测，这里直接让线程睡眠3s
                // 更为精确的做法是用Timer和TimeTask，但这不适合后台定时
                try {
                    Thread.sleep(3000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}