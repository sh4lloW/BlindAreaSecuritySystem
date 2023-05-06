package com.example.blindareasecuritysystem.Utils;

public class Vedio {
    private String name;
    private String score;
    private int head;
    private int position;
    public Vedio(String name, int head, String score,int position) {
        this.name = name;
        this.head = head;
        this.score = score;
        this.position = position;
    }

    public String getName() {
        return name;
    }
    public String getScore() {return score;}
    public int getHead(){
        return  head;
    }
    public int getposition() { return position;}
}