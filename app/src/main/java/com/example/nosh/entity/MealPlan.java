package com.example.nosh.entity;

import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This is the MealPlan class, each MealPlan will have a name, a start/end date and a Map
 * The Map will hold a list of Meals that belong to a specific MealPlan day
 */
public class MealPlan implements Serializable, Hashable {

    private Date startDate; // start date of the meal plan
    private Date endDate; // end date of the meal plan
    private Integer totalDays; // total number of days that the plan lasts
    private String name; // name of the meal plan

    // TODO: Should not be an integer but a string of the date corresponding to a day
    // TODO: Notice a user should be able define multiple meal per days
    private Map<Integer, ArrayList<Meal>> planDays; // days of the meal plan

    private String hashcode; // id

    public MealPlan() {
        hashcode = Hashing
                .sha256()
                .hashInt(new Timestamp(new Date()).getNanoseconds())
                .toString();
    }

    public MealPlan(String name, Date startDate, Date endDate) {
        this();

        this.startDate = startDate;
        this.endDate = endDate;

        this.totalDays = 5; // endDate - startDate

        this.planDays = new HashMap<>();
        for (int i = 0; i < 5; i++){ // create a new day entry for every day
            planDays.put(i, new ArrayList<>());
        }

        this.name = name;
    }

    // Getters and Setters
    public void addMealToDay(Integer day, Meal meal)
            throws CloneNotSupportedException {

        // add a meal to one of the plan days
        Objects.requireNonNull(planDays.get(day)).add(new Meal(meal));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return (Date) startDate.clone();
    }

    public void setStartDate(Date startDate) {
        if (startDate != null) {
            this.startDate = (Date) startDate.clone();
        }
    }

    public Date getEndDate() {
        return (Date) endDate.clone();
    }

    public void setEndDate(Date endDate) {
        if (endDate != null) {
            this.endDate = (Date) endDate.clone();
        }
    }

    public Integer getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    // TODO: return a deep copy instead of a shallow copy
    public Map<Integer, ArrayList<Meal>> getPlanDays() {
        return planDays;
    }

    // TODO: return a deep copy instead of a shallow copy
    public void setPlanDays(Map<Integer, ArrayList<Meal>> planDays) {
        this.planDays = planDays;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }
}
