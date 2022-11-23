package com.example.nosh.repository;

import com.example.nosh.database.controller.MealPlanDBController;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.entity.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;
import java.util.Observable;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class MealPlanRepository extends Repository {

    public static final String CREATE_NEW_MEAL_PLAN = "create_new_meal_plan";
    public static final String MEAL_PLAN_HASHCODE = "meal_plan_hashcode";

    private final HashMap<String, MealPlan> mealPlans;

    @Inject
    public MealPlanRepository(MealPlanDBController dbController) {
        super(dbController);

        mealPlans = new HashMap<>();

//        sync();
    }

    public void add(String name, Date startDate, Date endDate) {
        // TODO: Should we call this at the very end of the creation process or at the start?
        // At the end

        MealPlan mealPlan = new MealPlan(name, startDate, endDate);
        mealPlans.put(mealPlan.getHashcode(), mealPlan);

        dbController.create(mealPlan);

        Transaction transaction = new Transaction(CREATE_NEW_MEAL_PLAN);

        transaction.putContents(MEAL_PLAN_HASHCODE, mealPlan.getHashcode());

        super.notifyObservers(transaction);
    }

    public void add(MealPlan mealPlan) {
        mealPlans.put(mealPlan.getHashcode(), new MealPlan(mealPlan));

        super.add(mealPlan);
    }

    public MealPlan retrieve(String hashcode) {
        return new MealPlan(Objects.requireNonNull(mealPlans.get(hashcode)));
    }

    public void update(MealPlan mealPlan) {

    }

    public void delete(MealPlan mealPlan) {

    }

    public ArrayList<MealPlan> retrieve() {
        return new ArrayList<>(mealPlans.values());
    }

    /**
     * Retrieve all Meal Plan Documents
     * Retrieve all Meal Documents for a single Meal Plan
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        assert arg instanceof Transaction;

        Transaction transaction = (Transaction) arg;
    }
}
