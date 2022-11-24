package com.example.nosh.database.controller;

import android.util.Log;

import com.example.nosh.entity.Hashable;
import com.example.nosh.entity.Meal;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.entity.MealPlanComponent;
import com.example.nosh.entity.Transaction;
import com.example.nosh.utils.EntityUtil;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class MealPlanDBController extends DBController {

    static final String DOC_NAME = "meal_plan_storage";
    public static final String COLLECTION_NAME = "meal_plans";
    static final String MEAL_PLAN_COMPONENTS = "meal_plan_components";
    static final String MEALS = "meals";

    public static final String SYNC_COMPLETE = "sync_complete";

    private final ExecutorService executorService;

    private class RetrieveMealTask implements Callable<Meal[]> {
        private final String hashcode;
        private final String date;

        public RetrieveMealTask(String hashcode, String date) {
            this.hashcode = hashcode;
            this.date = date;
        }

        @Override
        public Meal[] call() throws Exception {
            CollectionReference mealCollection = ref
                    .document(hashcode)
                    .collection(MEAL_PLAN_COMPONENTS)
                    .document(date)
                    .collection(MEALS);

            Task<QuerySnapshot> task = mealCollection.get();

            QuerySnapshot snapshots = Tasks.await(task);

            if (task.isSuccessful()) {
                Log.d("RETRIEVE", "Successfully cache in Meal documents");

                Meal[] meals = new Meal[task.getResult().size()];

                if (task.getResult().size() == 0) {
                    return meals;
                }

                int counter = 0;
                for (DocumentSnapshot doc : snapshots) {
                    meals[counter++] = EntityUtil.mapToMeal(
                            Objects.requireNonNull(doc.getData())
                    );
                }

                return meals;
            } else {
                Log.w("RETRIEVE_MEAL", "Failed to cache");

                return null;
            }
        }
    }

    @Inject
    MealPlanDBController(CollectionReference ref) {
        super(ref.document(DOC_NAME).collection(COLLECTION_NAME));

        executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void create(Hashable o) {
        assertType(o);

        MealPlan mealPlan = (MealPlan) o;

        Map<String, Object> mealPlanData = EntityUtil.mealPlanToMap(mealPlan);

        // Create basic information about a meal plan (start and end date, name)
        DocumentReference mealPlanDoc = ref.document(mealPlan.getHashcode());
        mealPlanDoc
                .set(mealPlanData)
                .addOnSuccessListener(
                        unused -> Log.i("CREATE",
                                "DocumentSnapshot written with ID: " +
                                        mealPlan.getHashcode()
                        )
                )
                .addOnFailureListener(
                        e -> Log.w("CREATE", "Error adding document")
                );
    }

    @Override
    public void retrieve() {
        try {
            Future<MealPlan[]> mealPlansFuture =
                    executorService.submit(this::retrieveMealPlans);

            MealPlan[] mealPlans = mealPlansFuture.get();

            assert mealPlans != null;

            if (mealPlans.length == 0) {
                return;
            }

            for (MealPlan mealPlan : mealPlans) {
                for (String date : mealPlan.getPlans().keySet()) {
                    Future<Meal[]> mealsFuture = executorService
                            .submit(new RetrieveMealTask(mealPlan.getHashcode(), date));

                    Meal[] meals = mealsFuture.get();

                    for (Meal meal :
                            meals) {
                        mealPlan.addMealToDay(date, meal);
                    }
                }
            }

            Transaction transaction = new Transaction(SYNC_COMPLETE);

            transaction.putContents(COLLECTION_NAME, mealPlans);

            setChanged();
            notifyObservers(transaction);
        } catch (ExecutionException | InterruptedException e) {
            Log.w("RETRIEVE", e);
        }
    }

    private MealPlan[] retrieveMealPlans() throws ExecutionException, InterruptedException {
        Task<QuerySnapshot> task = ref.get();

        QuerySnapshot snapshots = Tasks.await(task);

        if (task.isSuccessful()) {
                MealPlan[] mealPlans = new MealPlan[task.getResult().size()];

                if (task.getResult().size() == 0) {
                    return mealPlans;
                }

                int counter = 0;
                for (DocumentSnapshot doc :
                        task.getResult()) {
                    mealPlans[counter++] = EntityUtil
                            .mapToMealPlan(Objects.requireNonNull(doc.getData()));
                }

                return mealPlans;
        }

        return null;
    }

    @Override
    public void update(Hashable o) {
        assertType(o);

        MealPlan mealPlan = (MealPlan) o;

        // Getting a sub collection to contain meal plan components for each day
        CollectionReference mealPlanComponentsCol = ref
                .document(o.getHashcode())
                .collection(MEAL_PLAN_COMPONENTS);

        for (Map.Entry<String, MealPlanComponent> entry : mealPlan) {
            DocumentReference mealPlanComponentDoc = mealPlanComponentsCol
                    .document(entry.getKey());

            CollectionReference mealsCol = mealPlanComponentDoc.collection(MEALS);

            for (Meal meal : entry.getValue()) {
                DocumentReference mealDoc = mealsCol.document(meal.getHashcode());

                Map<String, Object> mealData = EntityUtil.mealToMap(meal);

                mealDoc
                        .set(mealData)
                        .addOnSuccessListener(
                                unused -> Log.i("UPDATE",
                                        "DocumentSnapshot written with ID: " +
                                                meal.getHashcode()
                                )
                        )
                        .addOnFailureListener(
                                e -> Log.w("UPDATE", "Error updating document", e)
                        );
            }
        }
    }

    @Override
    public void delete(Hashable o) {
        assertType(o);

        ref.document(o.getHashcode())
                .delete()
                .addOnSuccessListener(unused ->
                        Log.i("REMOVE", "DocumentSnapshot " + o.getHashcode() +
                                "successfully deleted!"))
                .addOnFailureListener(e ->
                        Log.w("REMOVE", "Error deleting document", e));
    }

    private void assertType(Object o) {
        assert o instanceof MealPlan;
    }
}
