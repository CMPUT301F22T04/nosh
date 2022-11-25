package com.example.nosh.database.controller;

import android.util.Log;

import com.example.nosh.entity.Hashable;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.utils.EntityUtil;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class IngredientDBController extends DBController {

    static final String DOC_NAME = "ingredient_storage";
    static final String COLLECTION_NAME = "ingredients";

    @Inject
    public IngredientDBController(CollectionReference ref) {
        super(ref.document(DOC_NAME).collection(COLLECTION_NAME));
    }

    @Override
    public void create(Hashable o) {
        assertType(o);

        Ingredient ingredient = (Ingredient) o;

        Map<String, Object> data = EntityUtil.ingredientToMap(ingredient);

        DocumentReference doc = ref.document(o.getHashcode());

        doc
                .set(data)
                .addOnSuccessListener(
                        unused ->
                                Log.i(
                                        CREATE,
                                        "Ingredient document snapshot written with ID: " +
                                                doc.get()
                                )
                )
                .addOnFailureListener(e ->
                        Log.e(
                                CREATE,
                                e.toString()
                        )
                );
    }

    @Override
    public void retrieve() {
        try {
            Future<Ingredient[]> future = Executors.newSingleThreadExecutor()
                    .submit(this::retrieveIngredients);

            Ingredient[] ingredients = future.get();

            if (ingredients == null) {
                Log.e(
                        RETRIEVE,
                        "Failed to cache"
                );
            } else {
                setChanged();
                notifyObservers(ingredients);
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Ingredient[] retrieveIngredients() throws ExecutionException, InterruptedException {
        Task<QuerySnapshot> task = ref.get();

        QuerySnapshot snapshots = Tasks.await(task);

        if (task.isSuccessful()) {
            Ingredient[] ingredients = new Ingredient[snapshots.size()];
            if (task.getResult().size() == 0) {
                return ingredients;
            }

            int i = 0;
            for (DocumentSnapshot doc : snapshots) {
                Log.i(
                        RETRIEVE,
                        "Ingredient document snapshot returned with ID: " + doc.getId()
                );

                ingredients[i++] = EntityUtil
                        .mapToIngredient(Objects.requireNonNull(doc.getData()));
            }

            return ingredients;
        }

        return null;
    }

    @Override
    public void update(Hashable o) {
        assertType(o);

        Ingredient ingredient = (Ingredient) o;

        DocumentReference doc = ref.document(ingredient.getHashcode());

        doc
                .update(
                "inStorage", ingredient.isInStorage(),
                        "bestBeforeDate", ingredient.getBestBeforeDate(),
                        "unit", ingredient.getUnit(),
                        "amount", ingredient.getAmount(),
                        "category", ingredient.getCategory(),
                        "description", ingredient.getDescription(),
                        "location", ingredient.getLocation(),
                        "name", ingredient.getName()
                )
                .addOnSuccessListener(
                        unused -> Log.i(
                                UPDATE,
                                "Ingredient document snapshot updated with ID: " + doc.getId()
                        )
                )
                .addOnFailureListener(e -> Log.e(
                        UPDATE,
                        e.toString()
                ));
    }

    @Override
    public void delete(Hashable o) {
        assertType(o);

        ref.document(o.getHashcode())
                .delete()
                .addOnSuccessListener(unused -> Log.i(
                        DELETE,
                        "Ingredient document snapshot removed with ID" + o.getHashcode()))
                .addOnFailureListener(e -> Log.e(DELETE, e.toString()));
    }

    private void assertType(Object o) {
        assert o instanceof Ingredient;
    }
}
