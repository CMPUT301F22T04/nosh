package com.example.nosh.controller;

import android.util.Pair;

import com.example.nosh.entity.Meal;
import com.example.nosh.entity.MealComponent;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.entity.MealPlanComponent;
import com.example.nosh.repository.MealPlanRepository;
import com.example.nosh.utils.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Observer;

import javax.inject.Inject;


public class MealPlanController {

    public static final String CREATE_NEW_MEAL_PLAN = "create_new_meal_plan";
    public static final String MEAL_PLAN_HASHCODE = "meal_plan_hashcode";
    public static final String ADD_MEAL_TO_DAY = "add_meal_to_day";

    final MealPlanRepository mealPlanRepository;

    @Inject
    public MealPlanController(MealPlanRepository mealPlanRepository) {
        this.mealPlanRepository = mealPlanRepository;
    }

    /**
     * Receive all necessary information from users to create a a new mealplan
     */
    public void add(String name, Date startDate, Date endDate) {
        mealPlanRepository.add(name, startDate, endDate);
    }

    public void add(MealPlan mealPlan) {
        mealPlanRepository.add(mealPlan);
    }

    public MealPlan retrieve(String hashcode) {
        return mealPlanRepository.retrieve(hashcode);
    }

    /**
     * Return a list of copy references of MealPlan objects
     * @return
     */
    public ArrayList<MealPlan> retrieve() {
        return mealPlanRepository.retrieve();
    }

    public ArrayList<MealComponent> retrieveUsedMealComponents() {
        return mealPlanRepository.retrieveUsedMealComponents();
    }

    public ArrayList<Pair<String, MealPlanComponent>> sortMealPlanComponent(MealPlan mealPlan) {
        HashMap<String, MealPlanComponent> plans = mealPlan.getPlans();

        List<String> dates = new ArrayList<>(plans.keySet());

        ArrayList<Pair<String, MealPlanComponent>> sortedMealPlanComponents =
                new ArrayList<>();

        Collections.sort(dates);
        for (String date :
                dates) {
            Pair<String, MealPlanComponent> pair = new Pair<>(date, plans.get(date));

            sortedMealPlanComponents.add(pair);
        }

        return sortedMealPlanComponents;
    }

    public void addMealToDay(Date startDate, Integer dayCount, Meal meal, String hashcode) {
        Calendar date = DateUtil.getCalendar(
                DateUtil.formatDate(startDate)
        );

        date.add(Calendar.DAY_OF_MONTH, dayCount - 1);

        String dateString = DateUtil.formatDate(date.getTime());

        meal.setUsedDate(dateString);

        mealPlanRepository.addMealToDay(hashcode, dateString, meal);
    }

    public void scaling(String scalingType, int scaling, String mealPlanHash,
                        String date, String mealHash) {
        if (scalingType.compareTo("PLAN") == 0) {
            mealPlanRepository.scale(scaling, mealPlanHash);
        } else if (scalingType.compareTo("DAY") == 0) {
            mealPlanRepository.scaleDay(scaling, mealPlanHash, date);
        } else if (scalingType.compareTo("MEAL") == 0) {
            mealPlanRepository.scaleMeal(scaling, mealPlanHash, date, mealHash);
        }
    }

    /**
     * Delete a recipe
     * @param mealPlan
     */
    public void delete(MealPlan mealPlan) {
        mealPlanRepository.delete(mealPlan);
    }

    public void addObserver(Observer o) {
        mealPlanRepository.addObserver(o);
    }

    public void deleteObserver(Observer o) {
        mealPlanRepository.deleteObserver(o);
    }
}
