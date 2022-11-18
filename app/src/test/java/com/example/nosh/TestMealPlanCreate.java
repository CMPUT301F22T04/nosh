package com.example.nosh;

import com.example.nosh.entity.MealPlan;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class TestMealPlanCreate {

    MealPlan mockMealPlan;

    FirebaseFirestore firestore;

    TestMealPlan testMealPlan;

    @Before
    public void setup() {
        firestore = Mockito.mock(FirebaseFirestore.class);

        testMealPlan = new TestMealPlan();
        testMealPlan.setup();
    }

    @Test
    public void testCreate() {

    }
}
