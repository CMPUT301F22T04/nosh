package com.example.nosh;

import androidx.annotation.NonNull;

import com.example.nosh.database.DBController;
import com.example.nosh.database.DBControllerFactory;
import com.example.nosh.database.IngrStorageDBController;
import com.example.nosh.entity.ingredient.StoredIngredient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Date;


public class IngrStorageDBControllerTest {

    DBController accessController;

    IngrStorageDBControllerTest() {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        if (BuildConfig.DEBUG) {
            fStore.useEmulator("10.0.2.2", 8080);
        }

        DBControllerFactory factory = new DBControllerFactory(fStore);

        accessController =
                factory.createAccessController(IngrStorageDBController.class.getSimpleName());
    }

    FirebaseFirestore getFirestore() {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        if (BuildConfig.DEBUG) {
            fStore.useEmulator("10.0.2.2", 8080);
        }

        return fStore;
    }

    ArrayList<StoredIngredient> genStoredIngredients() {
        ArrayList<StoredIngredient> storedIngredients = new ArrayList<>();

        storedIngredients.add(
                new StoredIngredient(new Date(), 1, 8, "STARBUCKS Iced Coffee",
                        "1.18 ~ 1.42 L", "Dairy", "Fridge")
        );

        storedIngredients.add(
                new StoredIngredient(new Date(), 1, 4, "COCA-COLA Mini Cans",
                        "6x222 mL", "Drinks", "Fridge")
        );

        storedIngredients.add(
                new StoredIngredient(new Date(), 1, 5, "KRAFT Peanut Butter",
                        "750g", "Pantry", "Pantry")
        );

        return storedIngredients;
    }

    @Test
    void testAdd() {
        FirebaseFirestore firestore = getFirestore();
        ArrayList<StoredIngredient> storedIngredients = genStoredIngredients();

        for (StoredIngredient item :
                storedIngredients) {
            accessController.create(item);
        }

        firestore.collection("ingredient_storage")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            assertEquals(storedIngredients.size(),
                                    task.getResult().size());

                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                assertTrue(storedIngredients.contains(doc.toObject(StoredIngredient.class)));
                            }
                        }
                    }
                });
    }
}
