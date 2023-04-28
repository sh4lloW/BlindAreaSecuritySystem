package com.example.blindareasecuritysystem.Activity;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.blindareasecuritysystem.Entity.Point;
import com.example.blindareasecuritysystem.R;
import com.example.blindareasecuritysystem.Utils.LoadData;

import java.util.ArrayList;
import java.util.List;

public class LightActivity extends AppCompatActivity {

    private ImageView imageView;

    private LinearLayout container;
    private LayoutTransition transition;

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
            textView.setTextSize(15);
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