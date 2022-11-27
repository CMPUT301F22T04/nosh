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

import java.util.ArrayList;
import java.util.List;
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
    static final String MEAL_PLAN_COMPONENTS = "meal_plan_components";

    public static final String COLLECTION_NAME = "meal_plans";
    public static final String MEALS = "meals";
    public static final String SYNC_COMPLETE = "sync_complete";

    private final ExecutorService executorService;

    private class RetrieveMealTask implements Callable<ArrayList<Meal>> {
        private final String hashcode;
        private final String date;

        public RetrieveMealTask(String hashcode, String date) {
            this.hashcode = hashcode;
            this.date = date;
        }

        @Override
        public ArrayList<Meal> call() throws Exception {
            CollectionReference mealCollection = ref
                    .document(hashcode)
                    .collection(MEAL_PLAN_COMPONENTS)
                    .document(date)
                    .collection(MEALS);

            Task<QuerySnapshot> task = mealCollection.get();

            QuerySnapshot snapshots = Tasks.await(task);

            if (task.isSuccessful()) {

                ArrayList<Meal> meals = new ArrayList<>();

                if (task.getResult().size() == 0) {
                    return meals;
                }

                for (DocumentSnapshot doc : snapshots) {
                    Log.i(
                            RETRIEVE,
                            "Meal document snapshot returned with ID: " + doc.getId()
                    );

                    meals.add(EntityUtil.mapToMeal(Objects.requireNonNull(doc.getData())));
                }

                return meals;
            } else {
                Log.e(RETRIEVE, "Failed to cache in Meal document snapshot");

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
                        unused -> Log.i(
                                CREATE,
                                "Meal Plan DocumentSnapshot written with ID: " +
                                        mealPlan.getHashcode()
                        )
                )
                .addOnFailureListener(e -> Log.e(CREATE, e.toString()));
    }

    @Override
    public void retrieve() {
        try {
            Future<MealPlan[]> mealPlansFuture =
                    executorService.submit(this::retrieveMealPlans);

            // Retrieve all meal plan document from Firestore and
            // convert them to MealPlan objects
            MealPlan[] mealPlans = mealPlansFuture.get();

            // Skip unnecessary procedure if there's no meal plan document in Firestore
            assert mealPlans != null;
            if (mealPlans.length == 0) {
                return;
            }

            ArrayList<Meal> mealArrayList = retrieveMeals(mealPlans);

            Transaction transaction = new Transaction(SYNC_COMPLETE);

            Meal[] meals = new Meal[mealArrayList.size()];

            transaction.putContents(COLLECTION_NAME, mealPlans);
            transaction.putContents(MEALS, mealArrayList.toArray(meals));

            setChanged();
            notifyObservers(transaction);

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieve all documents of meal in Firestore and convert them to Meal Plan objects
     */
    private MealPlan[] retrieveMealPlans() throws
            ExecutionException, InterruptedException {
        Task<QuerySnapshot> task = ref.get();

        QuerySnapshot snapshots = Tasks.await(task);

        if (task.isSuccessful()) {
                MealPlan[] mealPlans = new MealPlan[snapshots.size()];

                if (snapshots.size() == 0) {
                    return mealPlans;
                }

                int counter = 0;
                for (DocumentSnapshot doc :
                        snapshots) {
                    Log.i(
                            RETRIEVE,
                            "Meal Plan DocumentSnapshot returned with ID: " + doc.getId());

                    mealPlans[counter++] = EntityUtil
                            .mapToMealPlan(Objects.requireNonNull(doc.getData()));
                }

                return mealPlans;
        }

        return null;
    }

    /**
     * Retrieve all documents of meal in Firestore and convert them to Meal Objects
     */
    private ArrayList<Meal> retrieveMeals(MealPlan[] mealPlans) throws
            ExecutionException, InterruptedException {

        ArrayList<Meal> mealArrayList = new ArrayList<>();

        for (MealPlan mealPlan : mealPlans) {
            List< Future< ArrayList<Meal> > > futures = new ArrayList<>();

            for (String date : mealPlan.getPlans().keySet()) {
                futures.add(
                        executorService.submit(new RetrieveMealTask(mealPlan.getHashcode(), date)))
                ;
            }

            for (Future< ArrayList<Meal> > future : futures) {
                ArrayList<Meal> mealForDay = future.get();

                mealArrayList.addAll(mealForDay);
            }
        }

        return mealArrayList;
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
                                unused -> Log.i(
                                        UPDATE,
                                        "Meal document snapshot updated with ID: " +
                                                mealDoc.getId()
                                )
                        )
                        .addOnFailureListener(e -> Log.w(UPDATE, e.toString()));
            }
        }
    }

    @Override
    public void delete(Hashable o) {
        assertType(o);

        ref.document(o.getHashcode())
                .delete()
                .addOnSuccessListener(unused ->
                        Log.i(
                                DELETE,
                                "Meal Plan document snapshot deleted with ID: " +
                                        o.getHashcode()
                        )
                )
                .addOnFailureListener(e -> Log.e(DELETE, e.toString()));

        // TODO : Out of scope, a schedule function (not in this app) should clean up the nesting
    }

    private void assertType(Object o) {
        assert o instanceof MealPlan;
    }
}
