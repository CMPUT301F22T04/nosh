package com.example.nosh.database.controller;

import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This factory class is responible for the instantiation of different type 
 * DBController. The main purpose of factory class is to separate and encapsulate 
 * the instance creation of different types of DBControllers out of other classes.
 */
public class DBControllerFactory {

    private final FirebaseFirestore fStore;

    /**
     * Make sure remove public modifier when it is release
     * @param fStore
     */
    public DBControllerFactory(FirebaseFirestore fStore) {
        this.fStore = fStore;
    }

    /**
     * @param name This parameter specify the type of DBController needed to be 
     * instantiate, specifically, it specify the type via class name. For example, 
     * if one wants to create a instance of IngredientDBController (one type of
     * DBController), pass in IngredientDBController.class.getSimpleName()
     *
     * [ClassName].class.getSimpleName() will return a string representation of the class 
     * name
     */
    public DBController createAccessController(String name) {
        if (name.equalsIgnoreCase(IngredientDBController.class.getSimpleName())) {

            return new IngredientDBController(
                    fStore.collection(IngredientDBController.REF_NAME));

        } else if (name.equalsIgnoreCase(RecipeDBController.class.getSimpleName())) {

            return new RecipeDBController(
                    fStore.collection(RecipeDBController.REF_NAME));

        }

        return null;
    }
}
