package com.example.nosh.controller;

import android.content.Context;

import com.example.nosh.database.controller.DBController;
import com.example.nosh.database.controller.FirebaseStorageController;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.repository.MealPlanRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observer;

public class MealPlanController {
    private final MealPlanRepository mealPlanRepository;

    public MealPlanController(Context context, DBController dbController,
                            FirebaseStorageController storageController, Observer o) {
        mealPlanRepository = new MealPlanRepository(dbController);
        mealPlanRepository.addObserver(o);
        mealPlanRepository.sync();
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
}
