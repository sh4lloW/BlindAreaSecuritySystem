package com.example.blindareasecuritysystem.Activity;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blindareasecuritysystem.Adapter.VedioAdapter;
import com.example.blindareasecuritysystem.R;
import com.example.blindareasecuritysystem.Utils.Vedio;

import java.util.ArrayList;
import java.util.List;

public class VedioActivity extends AppCompatActivity  {
    private RecyclerView recyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private List<Vedio> mFruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vedio);
        //导入模拟数据
        initFruits();
        //获取布局组件
        recyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mLinearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        //添加adapter适配器
        VedioAdapter adapter = new VedioAdapter(mFruitList);
        recyclerView.setAdapter(adapter);



    }


    //模拟数据
    private void initFruits() {
        int i=1;
        Vedio T1 = new Vedio("时间:2001.12.1", R.drawable._is_looking,"危险分数:28",i++);
        mFruitList.add(T1);
        Vedio T2 = new Vedio("时间:2001.12.12",R.drawable.is_danger,"危险分数:30",i++);
        mFruitList.add(T2);

    }
}
