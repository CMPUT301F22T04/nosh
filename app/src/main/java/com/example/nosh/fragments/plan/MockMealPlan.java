package com.example.nosh.fragments.plan;

public class MockMealPlan {
    private String name;
    private String span;

    public MockMealPlan(String name, String span){
        this.name = name;
        this.span = span;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpan() {
        return span;
    }

    public void setSpan(String span) {
        this.span = span;
    }
}
