package com.example.nosh.database.controller;

import android.util.Log;

import com.example.nosh.entity.Hashable;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.utils.EntityUtil;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;

public class MealPlanDBController extends DBController{
    static final String DOC_NAME = "meal_plan_storage";
    static final String COLLECTION_NAME = "mealPlans";

    MealPlanDBController(CollectionReference ref) {
        super(ref);
    }

    @Override
    public void create(Hashable o) {
        assertType(o);

        MealPlan mealPlan = (MealPlan) o;
        Map<String, Object> data = EntityUtil.mealPlanToMap(mealPlan);
        DocumentReference doc = ref.document(o.getHashcode());

        doc.set(data)
                .addOnSuccessListener(unused ->
                        Log.i("CREATE", "DocumentSnapshot written with ID: " +
                                o.getHashcode()))
                .addOnFailureListener(e ->
                        Log.w("CREATE", "Error adding document)"));

        // TODO: This should be for ingredients and recipes
        /*
        for (Ingredient ingredient :
                mealPlan.getIngredients()) {
            doc.update("ingredients", FieldValue
                            .arrayUnion(EntityUtil.ingredientToMap(ingredient)));
        }
        */
    }

    @Override
    public Object retrieve(Hashable o) {
        return null;
    }

    @Override
    public void retrieve() {
        ref.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        MealPlan[] mealPlans =
                                new MealPlan[task.getResult().size()];

                        int i = 0;
                        for (DocumentSnapshot doc :
                                task.getResult()) {
                            mealPlans[i] = doc.toObject(MealPlan.class);
                            mealPlans[i++].setHashcode(doc.getId());
                        }
                        setChanged();
                        notifyObservers(mealPlans);
                    } else {
                        Log.w("RETRIEVE", "Cached get failed: ",
                                task.getException());
                    }
                });
    }

    @Override
    public void update(Hashable o) {
        assertType(o);

        MealPlan mealPlan = (MealPlan) o;
        DocumentReference doc = ref.document(mealPlan.getHashcode());

        doc.update("name", mealPlan.getName(), "start", mealPlan.getStartDate(),
                        "end", mealPlan.getEndDate())
                .addOnSuccessListener(unused ->
                        Log.i("UPDATE", "DocumentSnapshot " +
                                mealPlan.getHashcode() + "successfully updated"))
                .addOnFailureListener(e ->
                        Log.w("UPDATE", "Error updating document", e));
        /**
         * Update foodStuffs will be added later
         */
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
