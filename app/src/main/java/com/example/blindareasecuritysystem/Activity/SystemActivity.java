package com.example.blindareasecuritysystem.Activity;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pools;

import com.example.blindareasecuritysystem.Entity.Point;
import com.example.blindareasecuritysystem.R;
import com.example.blindareasecuritysystem.Utils.BaiduMap;
import com.example.blindareasecuritysystem.Utils.LoadData;

import java.util.ArrayList;
import java.util.List;

public class SystemActivity extends AppCompatActivity {

    private VideoView videoView;

    private LinearLayout container;
    private LayoutTransition transition;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);

        // 绑定
        videoView = findViewById(R.id.video_view);

        // 加载指定路径的视频
        // 如果要修改的话就把视频文件拖进raw包下，然后修改下行最后的视频名字
        String path = "android.resource://" + getPackageName() + "/" + R.raw.test;
        videoView.setVideoPath(path);
        // 创建MediaController，建立关联
        MediaController mediaController = new MediaController(SystemActivity.this);
        videoView.setMediaController(mediaController);
        // 获取焦点
        videoView.requestFocus();
        videoView.start();

        // LinearLayout用来装底部滑出式弹幕
        container = findViewById(R.id.barrier_container_system);
        transition = new LayoutTransition();

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
                if (rightAreaList.get(i).getX() >= 2.5 && rightAreaList.get(i).getY() >= 5.8 && leftAreaList.get(i).getX() >= 2.5 && leftAreaList.get(i).getY() >= 5.8) {
                    // 加载警告信息动画
                    loadAnimation();
                    handler.sendEmptyMessage(0);
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

    private void loadAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(null, "alpha", 0, 1);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                // 展示超过3条后删除
                if (container.getChildCount() == 3) {
                    handler.sendEmptyMessage(1);
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (container.getChildCount() == 4) {
                    handler.sendEmptyMessage(2);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        transition.setAnimator(LayoutTransition.APPEARING, animator);
        // 删除动画
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0, 0);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(null, new PropertyValuesHolder[]{alpha}).setDuration(transition.getDuration(LayoutTransition.DISAPPEARING));

        transition.setAnimator(LayoutTransition.DISAPPEARING, objectAnimator);
        container.setLayoutTransition(transition);
    }

    private String[] texts = BaiduMap.warningMessage;

    Pools.SimplePool<TextView> textViewSimplePool = new Pools.SimplePool<>(texts.length);
    // 用以停止显示循环
    int text_index = 0;

    @SuppressLint("ResourceAsColor")
    private TextView obtainTextView() {
        TextView textView = textViewSimplePool.acquire();
        text_index++;
        if (textView == null) {
            textView = new TextView(SystemActivity.this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setPadding(dp2px(10), dp2px(5), dp2px(10), dp2px(5));
            textView.setMaxLines(1);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(15);
        }
        // textView.setBackgroundDrawable(ContextCompat.getDrawable(SystemActivity.this, R.drawable.text_view_border));
        switch (index) {
            case 1:
                textView.setBackground(ContextCompat.getDrawable(SystemActivity.this, R.drawable.text_view_border_blue));
                break;
            case 2:
                textView.setBackground(ContextCompat.getDrawable(SystemActivity.this, R.drawable.text_view_border_green));
                break;
            case 3:
                textView.setBackground(ContextCompat.getDrawable(SystemActivity.this, R.drawable.text_view_border_red));
                break;
            default:
                textView.setBackground(ContextCompat.getDrawable(SystemActivity.this, R.drawable.text_view_border_yellow));
                break;
        }
        textView.setText(texts[index]);
        return textView;
    }

    private int dp2px(float dp) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }

    int index = 0;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint({"ResourceAsColor"})
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    // 此时text_index的判断条件为提示条数
                    if (text_index < 5) {
                        TextView textView = obtainTextView();
                        container.addView(textView);
                        sendEmptyMessageDelayed(0, 3000);
                        index++;
                        if (index == 5) {
                            index = 0;
                        }
                    }
                    break;
                case 1:
                    //给展示的第一个view增加渐变透明动画
                    container.getChildAt(0).animate().alpha(0).setDuration(transition.getDuration(LayoutTransition.APPEARING)).start();
                    break;
                case 2:
                    //删除顶部view
                    container.removeViewAt(0);
                    break;
            }
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}