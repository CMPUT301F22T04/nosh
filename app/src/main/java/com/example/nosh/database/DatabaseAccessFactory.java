package com.example.nosh.database;

import com.google.firebase.firestore.FirebaseFirestore;


public class DatabaseAccessFactory {

    private final FirebaseFirestore fStore;

    /**
     * Make sure remove public modifier when it is release
     * @param fStore
     */
    public DatabaseAccessFactory(FirebaseFirestore fStore) {
        this.fStore = fStore;
    }

    public DatabaseAccessController createAccessController(String name) {
        if (name.equalsIgnoreCase(IngredientStorageAccess.class.getSimpleName())) {
            return new IngredientStorageAccess(fStore.collection(IngredientStorageAccess.REF_NAME));
        }

        return null;
    }
}
