package com.example.nosh.injection;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import dagger.Module;
import dagger.Provides;


@Module
public class FirebaseModule {

    @Provides
    public StorageReference provideStorageReference() {
        return FirebaseStorage.getInstance().getReference(
                Objects.requireNonNull(FirebaseAuth.getInstance().getUid())
        );
    }
}
