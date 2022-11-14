package com.example.nosh.repository;

import com.example.nosh.database.controller.DBController;
import com.example.nosh.entity.MealPlan;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Observable;

import javax.inject.Inject;

public class MealPlanRepository extends Repository {

    private final HashMap<String, MealPlan> mealPlans;

    @Inject
    public MealPlanRepository(DBController dbController) {
        super(dbController);

        mealPlans = new HashMap<>();

        sync();
    }

    public void add(String name, Date startDate, Date endDate) {
        // TODO: Should we call this at the very end of the creation process or at the start?
        // At the end

        MealPlan mealPlan = new MealPlan(name, startDate, endDate);
        mealPlans.put(mealPlan.getHashcode(), mealPlan);

        super.add(mealPlan);
    }

    public void update(String name, Date startDate, Date endDate) {

    }

    public void delete(MealPlan mealPlan) {
    }

    public ArrayList<MealPlan> retrieve() {
        return new ArrayList<>(mealPlans.values());
    }

    @Override
    public void update(Observable o, Object arg) {
        assert (arg instanceof MealPlan[]);

        MealPlan[] mealPlans = (MealPlan[]) arg;
        for (MealPlan mealPlan : mealPlans) {
            this.mealPlans.put(mealPlan.getHashcode(), mealPlan);
        }

        notifyObservers();
    }
}
