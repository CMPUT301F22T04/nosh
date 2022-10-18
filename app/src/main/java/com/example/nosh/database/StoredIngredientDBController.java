package com.example.nosh.database;


import android.util.Log;

import com.example.nosh.entity.StoredIngredient;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;


public class StoredIngredientDBController {

    final private CollectionReference ref;

    public StoredIngredientDBController(CollectionReference ref) {
        this.ref = ref;
    }

    public ArrayList<StoredIngredient> retrieveAll() {
        ArrayList<StoredIngredient> ingredients = new ArrayList<>();

        ref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d("RetrieveAll", document.getId() + " => " +
                            document.getData());
                    ingredients.add(document.toObject(StoredIngredient.class));
                }
            } else {
                Log.d("RetrieveAll", "Error getting documents",
                        task.getException());
            }
        });

        return ingredients;
    }
}
