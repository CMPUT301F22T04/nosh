package com.example.nosh;

import com.example.nosh.entity.ingredient.StoredIngredient;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;


public class DatabaseAccessTest {


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
        ArrayList<StoredIngredient> storedIngredients = genStoredIngredients();
    }

}
