package com.example.nosh.database.Initializer;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.google.firebase.BuildConfig;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Collections;
import java.util.List;


public class FirebaseStorageInitializer implements Initializer<FirebaseStorage> {

    @NonNull
    @Override
    public FirebaseStorage create(@NonNull Context context) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        if (BuildConfig.DEBUG) {
            storage.useEmulator("10.0.2.2", 9199);
        }

        return storage;
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.emptyList();
    }
}
