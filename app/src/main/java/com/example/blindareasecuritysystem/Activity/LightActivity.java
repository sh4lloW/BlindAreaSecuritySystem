package com.example.blindareasecuritysystem.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pools;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.blindareasecuritysystem.Entity.Point;
import com.example.blindareasecuritysystem.R;
import com.example.blindareasecuritysystem.Utils.LoadData;

import java.util.ArrayList;
import java.util.List;

public class LightActivity extends AppCompatActivity {

    private VideoView videoView;

    private ImageView imageView;

    private LinearLayout container;
    private LayoutTransition transition;

    private Toolbar toolbar;
    private ActionBar actionBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        // 绑定
        videoView = findViewById(R.id.video_view_inside);
        toolbar = findViewById(R.id.back_button);

        // 显示返回按钮
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // 加载指定路径的视频
        // 如果要修改的话就把视频文件拖进raw包下，然后修改下行最后的视频名字
        String path = "android.resource://" + getPackageName() + "/" + R.raw.inside;
        videoView.setVideoPath(path);
        // 视频撑满
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        videoView.setLayoutParams(layoutParams);

        // 创建MediaController，建立关联
        MediaController mediaController = new MediaController(LightActivity.this);
        videoView.setMediaController(mediaController);
        // 获取焦点
        videoView.requestFocus();
        videoView.start();

        // 动图要用Glide加载，把下面的注释取消然后在load()中填入gif的路径
//        imageView = findViewById(R.id.red_blink);
//        Glide.with(LightActivity.this)
//                .load()
//                .asGif()
//                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//                .into(imageView);

        // LinearLayout用来装底部滑出式弹幕
        container = findViewById(R.id.barrier_container_light);
        transition = new LayoutTransition();

        loadAnimation();
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

    private String[] texts = new String[]{
            "障碍物坐标为：" + loadWarningPoint(5).toString(),
            "障碍物坐标为：" + loadWarningPoint(6).toString(),
            "障碍物坐标为：" + loadWarningPoint(7).toString(),
            "障碍物坐标为：" + loadWarningPoint(8).toString(),
            "障碍物坐标为：" + loadWarningPoint(9).toString(),
    };

    Pools.SimplePool<TextView> textViewSimplePool = new Pools.SimplePool<>(texts.length);
    // 用以停止显示循环
    int text_index = 0;

    @SuppressLint("ResourceAsColor")
    private TextView obtainTextView() {
        TextView textView = textViewSimplePool.acquire();
        text_index++;
        if (textView == null) {
            textView = new TextView(LightActivity.this);
            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setPadding(dp2px(10), dp2px(5), dp2px(10), dp2px(5));
            textView.setMaxLines(1);
            textView.setEllipsize(TextUtils.TruncateAt.END);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(20);
        }
        // textView.setBackgroundDrawable(ContextCompat.getDrawable(SystemActivity.this, R.drawable.text_view_border));
        switch (index) {
            case 1:
                textView.setBackground(ContextCompat.getDrawable(LightActivity.this, R.drawable.text_view_border_blue));
                break;
            case 2:
                textView.setBackground(ContextCompat.getDrawable(LightActivity.this, R.drawable.text_view_border_green));
                break;
            case 3:
                textView.setBackground(ContextCompat.getDrawable(LightActivity.this, R.drawable.text_view_border_red));
                break;
            default:
                textView.setBackground(ContextCompat.getDrawable(LightActivity.this, R.drawable.text_view_border_yellow));
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

    private Point loadWarningPoint(int index) {
        List<Point> rightAreaList = new ArrayList<>();
        LoadData.loadRightBlindAreaData(rightAreaList);
        return rightAreaList.get(index);
    }

    // 标题栏按钮监听
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //返回按钮点击事件
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.sendEmptyMessage(0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.sendEmptyMessage(0);
    }
}