package com.example.nosh.fragments.plan;

import java.util.List;

public class MealDay {
    private String day;
    private List<String> meals;
    private boolean isExpandable;

    public MealDay(String day, List<String> itemList) {
        this.day = day;
        this.meals = itemList;
        isExpandable = false;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public boolean isExpandable() {
        return isExpandable;
    }

    public void setExpandable(boolean expandable) {
        isExpandable = expandable;
    }

    public List<String> getMeals() {
        return meals;
    }

    public void setMeals(List<String> meals) {
        this.meals = meals;
    }
}
