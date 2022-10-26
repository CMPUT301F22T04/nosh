package com.example.nosh.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collections;
import java.util.List;


public class DBControllerFactoryInitializer implements Initializer<DBControllerFactory> {

    @NonNull
    @Override
    public DBControllerFactory create(@NonNull Context context) {
        return new DBControllerFactory(FirebaseFirestore.getInstance());
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.singletonList(FirestoreInitializer.class);
    }
}
