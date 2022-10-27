package com.example.nosh.database;

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
     * if one wants to create a instance of IngrStorageDBController (one type of 
     * DBController), pass in IngrStorageDBController.class.getSimpleName()
     *
     * [ClassName].class.getSimpleName() will return a string representation of the class 
     * name
     */
    public DBController createAccessController(String name) {
        if (name.equalsIgnoreCase(IngrStorageDBController.class.getSimpleName())) {
            return new IngrStorageDBController(fStore.collection(IngrStorageDBController.REF_NAME));
        }

        return null;
    }
}
