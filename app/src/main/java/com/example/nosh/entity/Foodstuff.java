package com.example.nosh.entity;

public abstract class Foodstuff {
    private String name;

    public Foodstuff(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
