package com.example.blindareasecuritysystem;

import org.junit.Test;

import static org.junit.Assert.*;

import android.animation.Animator;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.util.Pools;

import com.example.blindareasecuritysystem.Activity.SystemActivity;
import com.example.blindareasecuritysystem.Entity.Point;
import com.example.blindareasecuritysystem.Utils.BaiduMap;
import com.example.blindareasecuritysystem.Utils.LoadData;

import java.util.ArrayList;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testBlindAreaData() {

    }
}