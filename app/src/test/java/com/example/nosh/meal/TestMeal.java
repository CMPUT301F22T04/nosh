package com.example.nosh.meal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.nosh.entity.Hashable;
import com.example.nosh.entity.Meal;
import com.example.nosh.entity.MealComponent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is a responsible for unit testing Meal entity class.
 * @version 1.0
 */
public class TestMeal {

    ArrayList<MealComponent> mockMealComponentsA;
    ArrayList<MealComponent> mockMealComponentsB;

    @BeforeEach
    public void setup() {
        mockMealComponentsA = new ArrayList<>();

       MockMealComponent componentA =  new MockMealComponent("A");
       MockMealComponent componentB =  new MockMealComponent("B");

       componentB.setHashcode(Hashable.doubleHashing(componentB.getHashcode()));

       mockMealComponentsA.add(componentA);
       mockMealComponentsA.add(componentB);

       mockMealComponentsB = new ArrayList<>();
       MockMealComponent componentC = new MockMealComponent("C");
       MockMealComponent componentD = new MockMealComponent("D");

       componentD.setHashcode(Hashable.doubleHashing(componentD.getHashcode()));

       mockMealComponentsB.add(componentC);
       mockMealComponentsB.add(componentD);
    }

    @Test
    void testCreation() {
        Meal breakfast = new Meal(2, "2022-11-26", "hash", "breakfast");
        Meal dinner = new Meal(5,"2022-11-26", "hash", "dinner");

        assert breakfast.getName().equals("breakfast");
        assert breakfast.getServings() == 2;

        assert dinner.getName().equals("dinner");
        assert dinner.getServings() == 5;
    }

    @Test
    void testAddMealComponent() {
        Meal breakfast = new Meal();

        for (MealComponent mockMealComponent : mockMealComponentsA) {
            breakfast.addMealComponent(mockMealComponent);
        }

        MealComponent ingredient = new MockMealComponent();
        MealComponent recipe = new MockMealComponent();

        breakfast.addMealComponent(ingredient);
        breakfast.addMealComponent(recipe);

        assertThrows(AssertionError.class, () -> breakfast.addMealComponent(null));
    }

    @Test
    void testRemoveMealComponent() {
        Meal breakfast = new Meal();

        for (MealComponent mockMealComponent : mockMealComponentsA) {
            breakfast.addMealComponent(mockMealComponent);
        }

        assertDoesNotThrow(() -> {
            for (MealComponent mockMealComponent : mockMealComponentsA) {
                breakfast.removeMealComponent(mockMealComponent);
            }

            assertEquals(0, breakfast.getMealComponents().size());
        });

        assertEquals(0, breakfast.getMealComponents().size());
        assertThrows(AssertionError.class, () -> breakfast.removeMealComponent(null));
    }

    @Test
    void testMealCopy() {
        MockMeal breakfast = new MockMeal(2, "2022-11-26", "hash","breakfast");

        for (MealComponent mealComponent : mockMealComponentsA) {
            breakfast.addMealComponent(mealComponent);
        }

        MockMeal breakfastCopy = new MockMeal(breakfast);

        assertNotEquals(breakfast, breakfastCopy);
        assertEquals(breakfast.getServings(), breakfastCopy.getServings());
        assertEquals(breakfast.getName(), breakfastCopy.getName());
        assertEquals(breakfast.getHashcode(), breakfastCopy.getHashcode());
        assertEquals(breakfast.getMealComponents().size(),
                breakfastCopy.getMealComponents().size());

        for (MealComponent mealComponent : mockMealComponentsA) {
            assert breakfast.hasMealComponent(mealComponent);
            assert breakfastCopy.hasMealComponent(mealComponent);

            assertEquals(breakfast.getMealComponent(mealComponent),
                    breakfastCopy.getMealComponent(mealComponent));
        }
    }

    @Test
    void testSetMealComponents() {
        MockMeal breakfast = new MockMeal(2, "2022-11-26", "hash","breakfast");

        breakfast.setMealComponents(mockMealComponentsA);

        for (MealComponent mealComponent :
                mockMealComponentsA) {
            assertEquals(breakfast.getMealComponent(mealComponent), mealComponent);
        }
    }

    @Test
    void testGetMealComponents() {
        MockMeal breakfast = new MockMeal(2, "2022-11-26", "hash","breakfast");

        HashMap<String, MealComponent> mealHashmap = new HashMap<>();

        for (MealComponent mealComponent : mockMealComponentsA) {
            mealHashmap.put(mealComponent.getHashcode(), mealComponent);
        }

        breakfast.setMealComponents(mealHashmap);

        for (MealComponent mealComponent : breakfast.getMealComponents()) {
            assert mealHashmap.containsKey(mealComponent.getHashcode());
        }
    }

    // Setters and getters tests

    @Test
    void testSetGetServings(){
        Meal testMeal = new Meal();
        long serving = 1;
        testMeal.setServings(serving);
        assertEquals(testMeal.getServings(), serving);
    }

    @Test
    void testSetGetName(){
        Meal testMeal = new Meal();
        String name = "Healthy Meal";
        testMeal.setName(name);
        assertEquals(testMeal.getName(), name);
    }

    @Test
    void testSetGetUsedDate(){
        Meal testMeal = new Meal();
        String date = "2022-11-26";
        testMeal.setUsedDate(date);
        assertEquals(testMeal.getUsedDate(), date);
    }
}
