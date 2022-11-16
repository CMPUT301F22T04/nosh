package com.example.nosh.entity;

import androidx.annotation.NonNull;

import com.example.nosh.utils.DateUtil;
import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.function.Consumer;

/**
 * This is the MealPlan class, each MealPlan will have a name, a start/end date and a Map
 * The Map will hold a list of Meals that belong to a specific MealPlan day
 */
public class MealPlan implements Serializable, Hashable,
        Iterable<Map.Entry<String, MealPlanComponent>> {


    // TODO: Should not be an integer but a string of the date corresponding to a day
    // TODO: Notice a user should be able define multiple meal per days
    private HashMap<String, MealPlanComponent> plans;

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

        totalDays = DateUtil.dayDifferences(startDate, endDate);

        // create a new day entry for every day

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

    public HashMap<String, MealPlanComponent> getPlans() {
        HashMap<String, MealPlanComponent> plans = new HashMap<>();

        for (Map.Entry<String, MealPlanComponent> pairs :
                this.plans.entrySet()) {
            plans.put(pairs.getKey(), new MealPlanComponent(pairs.getValue()));
        }

        return plans;
    }

    public void setPlans(HashMap<String, MealPlanComponent> plans) {
        this.plans = new HashMap<>();

        for (Map.Entry<String, MealPlanComponent> pairs:
             plans.entrySet()) {
            this.plans.put(pairs.getKey(), new MealPlanComponent(pairs.getValue()));
        }
    }

    public Long getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(Integer totalDays) {
        this.totalDays = totalDays;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }

    @NonNull
    @Override
    public Iterator<Map.Entry<String, MealPlanComponent>> iterator() {
        return getPlans().entrySet().iterator();
    }

    @Override
    public void forEach(@NonNull Consumer<? super Map.Entry<String, MealPlanComponent>> action) {
        Iterable.super.forEach(action);
    }
}
