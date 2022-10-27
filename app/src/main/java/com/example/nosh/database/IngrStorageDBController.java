package com.example.nosh.database;

import android.util.Log;

import com.example.nosh.entity.Ingredient;
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

        ref.document(((Ingredient) o).getHashcode()).set(o)
                .addOnSuccessListener(unused ->
                        Log.i("CREATE", "DocumentSnapshot written with ID: " +
                                ((Ingredient) o).getHashcode()))
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
                        Ingredient[] ingredients =
                                new Ingredient[task.getResult().size()];

                        int i = 0;
                        for (DocumentSnapshot doc :
                                task.getResult()) {
                            ingredients[i++] = doc.toObject(Ingredient.class);
                        }

                        setChanged();
                        notifyObservers(ingredients);
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
        ref.document(((Ingredient) o).getHashcode())
                .delete()
                .addOnSuccessListener(unused ->
                        Log.d("remove", "DocumentSnapshot successfully deleted!"))
                .addOnFailureListener(e ->
                        Log.w("remove", "Error deleting document", e));
    }

    private void assertType(Object o) {
        assert o instanceof Ingredient;
    }
}
