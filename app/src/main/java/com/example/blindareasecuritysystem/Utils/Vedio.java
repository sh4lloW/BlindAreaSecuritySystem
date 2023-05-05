package com.example.blindareasecuritysystem.Utils;

public class Vedio {
    private String name;
    private String score;
    private int head;
    public Vedio(String name, int head, String score) {
        this.name = name;
        this.head = head;
        this.score = score;
    }

    public String getName() {
        return name;
    }
    public String getScore() {return score;}
    public int getHead(){
        return  head;
    }
}