package com.example.nosh.injection;

import com.example.nosh.BuildConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


// @Module is used for dependencies that are not done by class instantiation
// Example, to instantiate an instance of FirebaseAuth, you need to use
// FirebaseAuth.getInstance()
@Module
public class FirebaseModule {

    @Provides
    CollectionReference provideCollectionReference(FirebaseFirestore firestore, String uid) {
        return firestore.collection(uid);
    }

    @Provides
    StorageReference provideStorageReference(FirebaseStorage firebaseStorage, String uid) {
        return firebaseStorage.getReference(uid);

    }

    @Singleton
    @Provides
    FirebaseAuth provideFirebaseAuth() {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();

        if (BuildConfig.DEBUG) {
            fAuth.useEmulator("10.0.2.2", 9099);
        }

        return fAuth;
    }

    @Provides
    String provideUid(FirebaseAuth firebaseAuth) {
       return firebaseAuth.getUid();
    }

    @Singleton
    @Provides
    FirebaseFirestore provideFirebaseFirestore() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();

        if (BuildConfig.DEBUG) {
            firestore.useEmulator("10.0.2.2", 8080);
        }

        firestore.clearPersistence();

        return firestore;
    }

    @Singleton
    @Provides
    FirebaseStorage provideFirebaseStorage() {
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();

        if (BuildConfig.DEBUG) {
            firebaseStorage.useEmulator("10.0.2.2", 9199);
        }

        return firebaseStorage;
    }
}
