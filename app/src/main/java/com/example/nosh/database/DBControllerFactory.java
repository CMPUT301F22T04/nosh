package com.example.nosh.database;

import com.google.firebase.firestore.FirebaseFirestore;


public class DBControllerFactory {

    private final FirebaseFirestore fStore;

    /**
     * Make sure remove public modifier when it is release
     * @param fStore
     */
    public DBControllerFactory(FirebaseFirestore fStore) {
        this.fStore = fStore;
    }

    public DBController createAccessController(String name) {
        if (name.equalsIgnoreCase(IngrStorageDBController.class.getSimpleName())) {
            return new IngrStorageDBController(fStore.collection(IngrStorageDBController.REF_NAME));
        }

        return null;
    }
}
