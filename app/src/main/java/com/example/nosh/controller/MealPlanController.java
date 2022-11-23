package com.example.nosh.controller;

import com.example.nosh.entity.Meal;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.repository.MealPlanRepository;
import com.example.nosh.utils.DateUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Observer;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
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
     * @param name
     * @param startDate
     * @param endDate
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

    public void updateMealToDay(Date startDate, Integer dayCount, Meal meal, String hashcode) {
        Calendar date = DateUtil.getCalendar(
                DateUtil.formatDate(startDate)
        );

        date.add(Calendar.DAY_OF_MONTH, dayCount - 1);

        String dateString = DateUtil.formatDate(date.getTime());

        mealPlanRepository.updateMeal(hashcode, dateString, meal);
    }

    /**
     * Delete a recipe
     * @param mealPlan
     */
    public void delete(MealPlan mealPlan) {
        //TODO: Should we even implement this?
        mealPlanRepository.delete(mealPlan);
    }

    public void addObserver(Observer o) {
        mealPlanRepository.addObserver(o);
    }

    public void deleteObserver(Observer o) {
        mealPlanRepository.deleteObserver(o);
    }
}
