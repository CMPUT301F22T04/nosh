package com.example.nosh.database;

import android.util.Log;

import com.example.nosh.entity.StoredIngredient;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;


public class IngredientStorageAccess extends DatabaseAccessController {

    static final String REF_NAME = "ingredient_storage";

    IngredientStorageAccess(CollectionReference ref) {
        super(ref);
    }

    @Override
    public void create(Object o) {
        assertType(o);

        ref.document(String.valueOf(o.hashCode())).set(o)
                .addOnSuccessListener(unused ->
                        Log.i("CREATE", "DocumentSnapshot written with ID: " +
                                o.hashCode()))
                .addOnFailureListener(e ->
                        Log.w("CREATE", "Error adding document", e));

    }

    @Override
    public void retrieve(Object o) {
        assertType(o);
    }

    @Override
    public ArrayList<Object> retrieve() {
        ArrayList<Object> storeIngredients = new ArrayList<>();

        ref
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot doc :
                                task.getResult()) {
                            storeIngredients.add(doc.toObject(StoredIngredient.class));
                        }
                    } else {
                        Log.w("retrieve", "Cached get failed: ",
                                task.getException());
                    }
                });

        return storeIngredients;
    }

    @Override
    public void update(Object o) {
        assertType(o);
    }

    @Override
    public void remove(Object o) {
        ref.document(String.valueOf(o.hashCode()))
                .delete()
                .addOnSuccessListener(unused ->
                        Log.d("remove", "DocumentSnapshot successfully deleted!"))
                .addOnFailureListener(e ->
                        Log.w("remove", "Error deleting document", e));
    }

    private void assertType(Object o) {
        assert o instanceof StoredIngredient;
    }
}
