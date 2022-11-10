package com.example.nosh.controller;

import com.example.nosh.entity.MealPlan;
import com.example.nosh.repository.MealPlanRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observer;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class MealPlanController {

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

    /**
     * Return a list of copy references of MealPlan objects
     * @return
     */
    public ArrayList<MealPlan> retrieve() {
        return mealPlanRepository.retrieve();
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
