package com.example.nosh.database;


import com.google.firebase.firestore.CollectionReference;


public class IngredientDBController extends DBController {

    static final String COLLECTION = "ingredients";

    IngredientDBController(CollectionReference ref) {
        super(ref);
    }
}
