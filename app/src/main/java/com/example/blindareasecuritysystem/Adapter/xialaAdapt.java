package com.example.blindareasecuritysystem.Adapter;

public class xialaAdapt {
    public String getScore(String feature) {
        String result;
        switch (feature) {
            case "高数":
                result = "您的高数成绩: 150分";
                break;
            case "英语":
                result = "您的英语成绩: 99分";
                break;
            case "政治":
                result = "您的政治成绩: 100分";
                break;
            case "计算机":
                result = "您的计算机成绩: 149分";
                break;
            default:
                result = "您未参加考试！！！";
        }
        return result;
    }
}
