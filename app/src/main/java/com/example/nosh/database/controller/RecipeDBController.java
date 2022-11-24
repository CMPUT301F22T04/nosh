package com.example.nosh.database.controller;


import android.util.Log;

import com.example.nosh.entity.Hashable;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Recipe;
import com.example.nosh.utils.EntityUtil;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import java.util.Map;
import java.util.Objects;

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
                        Log.i("CREATE", "DocumentSnapshot written with ID: " +
                                o.getHashcode()))
                .addOnFailureListener(e ->
                        Log.w("CREATE", "Error adding document)"));

        for (Ingredient ingredient :
                recipe.getIngredients()) {
            doc
                    .update("ingredients", FieldValue
                            .arrayUnion(EntityUtil.ingredientToMap(ingredient)));
        }
    }

    @Override
    public void retrieve() {
        ref
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Recipe[] recipes =
                                new Recipe[task.getResult().size()];

                        int i = 0;
                        for (DocumentSnapshot doc :
                                task.getResult()) {
                            recipes[i++] = EntityUtil
                                    .mapToRecipe(Objects.requireNonNull(doc.getData()));
                        }

                        setChanged();
                        notifyObservers(recipes);
                    } else {
                        Log.w("RETRIEVE", "Cached get failed: ",
                                task.getException());
                    }
                });
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
                        "photograph", recipe.getPhotographRemote(),
                        "title", recipe.getName()
                )
                .addOnSuccessListener(unused ->
                        Log.i("UPDATE", "DocumentSnapshot " +
                                recipe.getHashcode() + "successfully updated"))
                .addOnFailureListener(e ->
                        Log.w("UPDATE", "Error updating document", e));


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
                        Log.i("REMOVE", "DocumentSnapshot " + o.getHashcode() +
                                "successfully deleted!"))
                .addOnFailureListener(e ->
                        Log.w("REMOVE", "Error deleting document", e));
    }

    private void assertType(Object o) {
        assert o instanceof Recipe;
    }
}
