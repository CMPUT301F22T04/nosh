package com.example.nosh.entity;

import androidx.annotation.NonNull;

import com.example.nosh.utils.DateUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * This is the MealPlan class, each MealPlan will have a name, a start/end date and a Map
 * The Map will hold a list of Meals that belong to a specific MealPlan day
 */
public class MealPlan extends Hashable
        implements Iterable<Map.Entry<String, MealPlanComponent>> {

    private Date startDate;
    private Date endDate;
    // TODO: Should not be an integer but a string of the date corresponding to a day
    // TODO: Notice a user should be able define multiple meal per days
    protected HashMap<String, MealPlanComponent> plans;
    private long totalDays;
    private String name;

    public MealPlan() {
        super();
    }

    public MealPlan(String name, Date startDate, Date endDate) {
        this();

        this.startDate = (Date) startDate.clone();
        this.endDate = (Date) endDate.clone();
        plans = new HashMap<>();
        totalDays = DateUtil.dayDifferences(startDate, endDate) + 1;

        Calendar start = Calendar.getInstance();
        start.setTime(this.startDate);

        // create a new day entry for every day
        for (int i = 0; i <= totalDays; i++) {
            start.add(Calendar.DAY_OF_MONTH, i);

            plans.put(DateUtil.formatDate(start.getTime()), new MealPlanComponent());

            start.add(Calendar.DAY_OF_MONTH, -i);
        }

        this.name = name;
    }

    public MealPlan(MealPlan mealPlan) {
        setStartDate(mealPlan.getStartDate());
        setEndDate(mealPlan.getEndDate());
        setPlans(mealPlan.getPlans());
        setTotalDays(mealPlan.getTotalDays());
        setName(mealPlan.getName());
        setHashcode(mealPlan.getHashcode());
    }

    public void addMealToDay(String date, Meal meal) {
        Objects.requireNonNull(plans.get(date)).addMeal(meal);
    }

    public void updateMealToDay(String date, Meal meal) {
        Objects.requireNonNull(plans.get(date)).updateMeal(meal);
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
        assert plans != null;

        this.plans = new HashMap<>();

        for (Map.Entry<String, MealPlanComponent> pairs:
             plans.entrySet()) {
            this.plans.put(pairs.getKey(), new MealPlanComponent(pairs.getValue()));
        }
    }

    public long getTotalDays() {
        return totalDays;
    }

    public void setTotalDays(long totalDays) {
        this.totalDays = totalDays;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
