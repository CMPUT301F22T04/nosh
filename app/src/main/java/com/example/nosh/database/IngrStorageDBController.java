package com.example.nosh.database;

import android.util.Log;

import com.example.nosh.entity.ingredient.StoredIngredient;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;


public class IngrStorageDBController extends DBController {

    static final String REF_NAME = "ingredient_storage";

    IngrStorageDBController(CollectionReference ref) {
        super(ref);
    }

    @Override
    public void create(Object o) {
        assertType(o);

        ref.document(((StoredIngredient) o).getHashcode()).set(o)
                .addOnSuccessListener(unused ->
                        Log.i("CREATE", "DocumentSnapshot written with ID: " +
                                ((StoredIngredient) o).getHashcode()))
                .addOnFailureListener(e ->
                        Log.w("CREATE", "Error adding document", e));
    }

    @Override
    public Object retrieve(Object o) {
        assertType(o);

        return null;
    }

    @Override
    public void retrieve() {
        ref
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        StoredIngredient[] storedIngredients =
                                new StoredIngredient[task.getResult().size()];

                        int i = 0;
                        for (DocumentSnapshot doc :
                                task.getResult()) {
                            storedIngredients[i++] = doc.toObject(StoredIngredient.class);
                        }

                        setChanged();
                        notifyObservers(storedIngredients);
                    } else {
                        Log.w("retrieve", "Cached get failed: ",
                                task.getException());
                    }
                });
    }

    @Override
    public void update(Object o) {
        assertType(o);
    }

    @Override
    public void delete(Object o) {
        ref.document(((StoredIngredient) o).getHashcode())
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
