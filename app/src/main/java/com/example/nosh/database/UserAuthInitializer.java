package com.example.nosh.database;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;


import java.util.Collections;
import java.util.List;


public class UserAuthInitializer implements Initializer<UserAuth> {

    @NonNull
    @Override
    public UserAuth create(@NonNull Context context) {
        return new UserAuth();
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return Collections.singletonList(FirebaseAuthInitializer.class);
    }
}
