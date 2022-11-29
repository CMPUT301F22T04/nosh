package com.example.nosh.database.controller;


import android.util.Log;

import com.example.nosh.entity.Hashable;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Recipe;
import com.example.nosh.utils.EntityUtil;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class RecipeDBController extends DBController {

    static final String DOC_NAME = "recipe_storage";
    static final String COLLECTION_NAME = "recipes";

    @Inject
    RecipeDBController(CollectionReference ref) {
        super(ref.document(DOC_NAME).collection(COLLECTION_NAME));
    }

    @Override
    public void create(Hashable o) {
        assertType(o);

        Recipe recipe = (Recipe) o;

        Map<String, Object> data = EntityUtil.recipeToMap(recipe);

        DocumentReference doc = ref.document(o.getHashcode());

        doc
                .set(data)
                .addOnSuccessListener(unused ->
                        Log.i(
                                CREATE,
                                "Recipe document snapshot written with ID: " + doc.getId()))
                .addOnFailureListener(e -> Log.e(CREATE, e.toString()));

        for (Ingredient ingredient :
                recipe.getIngredients()) {
            doc
                    .update("ingredients", FieldValue
                            .arrayUnion(EntityUtil.ingredientToMap(ingredient)));
        }
    }

    @Override
    public void retrieve() {
        try {
            Future<Recipe[]> future = Executors.newSingleThreadExecutor()
                    .submit(this::retrieveRecipe);

            Recipe[] recipes = future.get();

            if (recipes == null) {
               Log.e(RETRIEVE, "Failed to cache in Recipe document snapshot");
            } else {
                setChanged();
                notifyObservers(recipes);
            }

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Recipe[] retrieveRecipe() throws ExecutionException, InterruptedException {
        Task<QuerySnapshot> task = ref.get();

        QuerySnapshot snapshots = Tasks.await(task);

        if (task.isSuccessful()) {
            Recipe[] recipes = new Recipe[snapshots.size()];
            if (task.getResult().size() == 0) {
                return recipes;
            }

            int i = 0;
            for (DocumentSnapshot doc : snapshots) {
                Log.i(
                        RETRIEVE,
                        "Recipe DocumentSnapshot returned with ID: " + doc.getId()
                );

                recipes[i++] = EntityUtil
                        .mapToRecipe(Objects.requireNonNull(doc.getData()));
            }

            return recipes;
        }

        return null;
    }

    @Override
    public void update(Hashable o) {
        assertType(o);

        Recipe recipe = (Recipe) o;

        DocumentReference doc = ref.document(recipe.getHashcode());

        doc
                .update(
                    "preparationTime", recipe.getPreparationTime(),
                        "servings", recipe.getServings(),
                        "category", recipe.getCategory(),
                        "comments", recipe.getComments(),
                        "photographRemote", recipe.getPhotographRemote(),
                        "title", recipe.getName()
                )
                .addOnSuccessListener(unused -> Log.i(
                        UPDATE,
                        "Recipe document snapshot updated with ID: " + doc.getId()
                ))
                .addOnFailureListener(e -> Log.e(CREATE, e.toString()));

        // TODO : a better way to update ingredients instead of replace
        //  all of entirely ?
        doc.update("ingredients", FieldValue.delete());

        for (Ingredient ingredient : recipe.getIngredients()) {
            doc.update("ingredients",
                    FieldValue.arrayUnion(EntityUtil.ingredientToMap(ingredient)));
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
                                "Recipe document snapshot deleted with ID: "
                                        + o.getHashcode())
                )
                .addOnFailureListener(e -> Log.w(DELETE, e.toString()));
    }

    private void assertType(Object o) {
        assert o instanceof Recipe;
    }
}
