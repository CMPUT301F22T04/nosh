package com.example.nosh.entity;

import androidx.annotation.NonNull;

import com.example.nosh.utils.DateUtil;

import java.util.ArrayList;
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

    public ArrayList<Meal> getMealsByDay(String date) {
        return Objects.requireNonNull(plans.get(date)).getMeals();
    }

    public void setPlans(HashMap<String, MealPlanComponent> plans) {
        assert plans != null;

        this.plans = new HashMap<>();

        for (Map.Entry<String, MealPlanComponent> pairs:
             plans.entrySet()) {
            this.plans.put(pairs.getKey(), new MealPlanComponent(pairs.getValue()));
        }
    }

    public MealPlanComponent getPlanByDay(String dateString) {
        return new MealPlanComponent(Objects.requireNonNull(plans.get(dateString)));
    }

    public ArrayList<String> getDays() {
        return new ArrayList<>(plans.keySet());
    }

    public ArrayList<String> getDaysWithMeals() {
        ArrayList<String> dates = new ArrayList<>();

        for (String date : plans.keySet()) {
            if (Objects.requireNonNull(plans.get(date)).getSize() > 0) {
                dates.add(date);
            }
        }

        return dates;
    }

    public void scaleMealPlan(int scaling) {
        for (MealPlanComponent mealPlanComponent : plans.values()) {
            mealPlanComponent.scaleMeals(scaling);
        }
    }

    public void scaleDay(String date, int scaling) {
        Objects.requireNonNull(plans.get(date)).scaleMeals(scaling);
    }

    public void scaleMeal(String date, String hashcode, int scaling) {
        Objects.requireNonNull(plans.get(date)).scaleMeal(hashcode, scaling);
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
