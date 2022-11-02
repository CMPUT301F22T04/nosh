package com.example.nosh.database.Initializer;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.example.nosh.database.controller.FirebaseStorageController;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class FirebaseStorageControllerInitializer implements
        Initializer<FirebaseStorageController> {

    @NonNull
    @Override
    public FirebaseStorageController create(@NonNull Context context) {
        return new FirebaseStorageController(context, FirebaseStorage.getInstance().getReference(
                Objects.requireNonNull(FirebaseAuth.getInstance().getUid()
        )));

    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.singletonList(FirebaseStorageInitializer.class);
    }
}
