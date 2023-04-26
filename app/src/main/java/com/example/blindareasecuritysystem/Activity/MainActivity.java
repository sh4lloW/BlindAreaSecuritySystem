package com.example.blindareasecuritysystem.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.blindareasecuritysystem.Adapter.MyAdapter;
import com.example.blindareasecuritysystem.Fragment.ConnectionFragment;
import com.example.blindareasecuritysystem.Fragment.UserFragment;
import com.example.blindareasecuritysystem.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> fragmentList;

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private BottomNavigationView bottomNavigationView;

    private MenuItem menuItem;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 绑定
        viewPager = findViewById(R.id.content_view_pager);
        tabLayout = findViewById(R.id.content_table_layout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // 添加fragment
        fragmentList = new ArrayList<>();
        fragmentList.add(new ConnectionFragment());
        fragmentList.add(new UserFragment());

        // 实例化适配器，绑定
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_look:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.navigation_my:
                    viewPager.setCurrentItem(1);
                    break;
            }
            return false;
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void toLight(View view) {
        Intent intent = new Intent(MainActivity.this, LightActivity.class);
        startActivity(intent);
    }


}