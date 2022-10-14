package com.example.nosh;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.nosh.entity.Ingredient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


// For demoing emulator
public class FirestoreEmulatorDemo {
    static final int FIRESTORE_EMULATOR_PORT = 8080;
    static final String FIRESTORE_EMULATOR_HOST = "10.0.2.2";
    static final String INGREDIENT_COLLECTION = "ingredients";
    static final String DEBUG = "DEBUG";
    static final String WARNING = "WARNING";

    static final FirestoreListener fStoreListener = new FirestoreListener();

    static class FirestoreListener implements OnSuccessListener<DocumentReference>,
            OnFailureListener, OnCompleteListener<QuerySnapshot> {

        @Override
        public void onSuccess(DocumentReference documentReference) {
            Log.d(DEBUG, "DocumentSnap written with ID: " +
                    documentReference.getId());
        }

        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Log.d(DEBUG, document.getId() + "=> " + document.getData());
                }
            } else {
                Log.w(WARNING, "Error getting documents: ", task.getException());
            }
        }

        @Override
        public void onFailure(@NonNull Exception e) {
            Log.w(WARNING, "Error adding document", e);
        }
    }

    static void test() {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();
        CollectionReference ref = fStore.collection(INGREDIENT_COLLECTION);

        fStore.useEmulator(FIRESTORE_EMULATOR_HOST, FIRESTORE_EMULATOR_PORT);

        // Add data to emulator
        Ingredient eggs = new Ingredient("Eggs", 1);
        Ingredient apple = new Ingredient("Apple", 2);

        ref.add(eggs)
                .addOnSuccessListener(fStoreListener)
                .addOnFailureListener(fStoreListener);
        ref.add(apple)
                .addOnSuccessListener(fStoreListener)
                .addOnFailureListener(fStoreListener);

        ref.get()
                .addOnCompleteListener(fStoreListener);
    }
}
