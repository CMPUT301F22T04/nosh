package com.example.nosh.repository;

import static com.example.nosh.controller.MealPlanController.ADD_MEAL_TO_DAY;
import static com.example.nosh.controller.MealPlanController.CREATE_NEW_MEAL_PLAN;
import static com.example.nosh.controller.MealPlanController.MEAL_PLAN_HASHCODE;
import static com.example.nosh.database.controller.MealPlanDBController.COLLECTION_NAME;
import static com.example.nosh.database.controller.MealPlanDBController.MEALS;
import static com.example.nosh.database.controller.MealPlanDBController.SYNC_COMPLETE;

import com.example.nosh.database.controller.MealPlanDBController;
import com.example.nosh.entity.Meal;
import com.example.nosh.entity.MealComponent;
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

    private final HashMap<String, MealPlan> mealPlans;
    private final ArrayList<MealComponent> mealComponents;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;

    @Inject
    public MealPlanRepository(IngredientRepository ingredientRepository,
                              RecipeRepository recipeRepository,
                              MealPlanDBController dbController) {
        super(dbController);

        this.mealComponents = new ArrayList<>();
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;

        mealPlans = new HashMap<>();

        sync();
    }

    public void add(String name, Date startDate, Date endDate) {
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

    public ArrayList<MealComponent> retrieveUsedMealComponents() {
        ArrayList<MealComponent> usedMealComponents = new ArrayList<>();

        for (MealComponent mealComponent : mealComponents) {
            try {
                usedMealComponents.add((MealComponent) mealComponent.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        return usedMealComponents;
    }

    public void update(MealPlan mealPlan) {
        mealPlans.replace(mealPlan.getHashcode(), new MealPlan(mealPlan));

        dbController.update(mealPlan);
    }

    public void addMealToDay(String hashcode, String date, Meal meal) {
        for (MealComponent mealComponent : meal) {
            mealComponents.add(mealComponent);
        }

        Objects.requireNonNull(mealPlans.get(hashcode)).addMealToDay(date, meal);

        dbController.update(mealPlans.get(hashcode));

        Transaction transaction = new Transaction(ADD_MEAL_TO_DAY);

        transaction.putContents(MEAL_PLAN_HASHCODE, hashcode);

        notifyObservers(transaction);
    }

    public void delete(MealPlan mealPlan) {
        mealPlans.remove(mealPlan.getHashcode());

        super.delete(mealPlan);
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

        if (transaction.getTag().compareTo(SYNC_COMPLETE) == 0) {
            Object content = transaction
                    .getContents()
                    .get(COLLECTION_NAME);

            assert content instanceof MealPlan[];

            MealPlan[] mealPlans = (MealPlan[]) content;

            for (MealPlan mealPlan : mealPlans) {
                this.mealPlans.put(mealPlan.getHashcode(), new MealPlan(mealPlan));
            }

            content = transaction.getContents().get(MEALS);

            assert content instanceof Meal[];

            Meal[] meals = (Meal[]) content;

            for (Meal meal : meals) {
                for (MealComponent mealComponent : meal) {
                    String hash = mealComponent.getHashcode();

                    if (ingredientRepository.retrieve(hash) != null) {
                        meal.addMealComponent(ingredientRepository.retrieve(hash));
                        mealComponents.add(ingredientRepository.retrieve(hash));
                    } else if (recipeRepository.retrieve(hash) != null) {
                        meal.addMealComponent(recipeRepository.retrieve(hash));
                        mealComponents.add(recipeRepository.retrieve(hash));
                    }
                }

                Objects
                        .requireNonNull(this.mealPlans.get(meal.getUsedPlanHash()))
                        .addMealToDay(meal.getUsedDate(), meal);
            }

            super.notifyObservers();
        }
    }
}
