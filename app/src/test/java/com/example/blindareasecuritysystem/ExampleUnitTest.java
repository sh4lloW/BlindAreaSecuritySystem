package com.example.blindareasecuritysystem;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.blindareasecuritysystem.Entity.Point;
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
        // 双盲区的坐标数据
        List<Point> rightAreaList = new ArrayList<>();
        List<Point> leftAreaList = new ArrayList<>();
        // 填充数据
        LoadData.loadRightBlindAreaData(rightAreaList);
        LoadData.loadLeftBlindAreaData(leftAreaList);
        System.out.println(rightAreaList.size());
        System.out.println(leftAreaList.size());
    }
}