package com.example.nosh.database;

import com.google.firebase.firestore.FirebaseFirestore;


public class DBControllerFactory {

    private final FirebaseFirestore fStore;

    DBControllerFactory(FirebaseFirestore fStore) {
        this.fStore = fStore;
    }

    public DBController getDBController(String type) {
        if (type.equals(IngredientDBController.class.getSimpleName())) {
            return new IngredientDBController(
                    fStore.collection(IngredientDBController.COLLECTION));
        }

        return null;
    }
}
