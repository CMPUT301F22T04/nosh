package com.example.nosh.entity;

import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * This is the MealPlan class, each MealPlan will have a name, a start/end date and a Map
 * The Map will hold a list of Meals that belong to a specific MealPlan day
 */
public class MealPlan implements Serializable, Hashable {
    private Date startDate; // start date of the meal plan
    private Date endDate; // end date of the meal plan
    private Integer totalDays; // total number of days that the plan lasts
    private String name; // name of the meal plan
    private LinkedHashMap<String, ArrayList<Meal>> planDays; // days of the meal plan

    private String hashcode;

    public MealPlan(String name, Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.totalDays = ((int) TimeUnit.DAYS.convert(
                startDate.getTime() - endDate.getTime(), TimeUnit.MILLISECONDS)) * -1;

        // init a hash map with number of days that span the plan
        this.planDays = new LinkedHashMap<>();
        for (int i = 0; i < this.totalDays; i++){
            planDays.put("Day " + (i + 1), new ArrayList<>());
        }

        this.hashcode = Hashing.sha256().hashInt(new Timestamp(new Date()).getNanoseconds())
                .toString();
    }

    // Getters and Setters
    public void addMealToDay(Integer day, Meal foodstuff){
        Objects.requireNonNull(this.planDays.get(day)).add(foodstuff);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public LinkedHashMap<String, ArrayList<Meal>> getPlanDays() {
        return planDays;
    }

    public void setPlanDays(LinkedHashMap<String, ArrayList<Meal>> planDays) {
        this.planDays = planDays;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }
}
