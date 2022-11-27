package com.example.nosh.meal;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.example.nosh.entity.Hashable;
import com.example.nosh.entity.Meal;
import com.example.nosh.entity.MealComponent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class TestMealPlanComponent {
    TestMeal testMeal;

    @BeforeEach
    void setup() {
        testMeal = new TestMeal();

        testMeal.setup();
    }

    @Test
    void testAddMeal() {
        Meal breakfast = new MockMeal(2, "breakfast");
        Meal lunch = new MockMeal(4, "lunch");

        lunch.setHashcode(Hashable.doubleHashing(lunch.getHashcode()));

        breakfast.setMealComponents(testMeal.mockMealComponentsA);
        lunch.setMealComponents(testMeal.mockMealComponentsB);

        MockMealPlanComponent mealPlanComponent = new MockMealPlanComponent();

        mealPlanComponent.addMeal(breakfast);
        mealPlanComponent.addMeal(lunch);

        assertNotEquals(breakfast, mealPlanComponent.getMeal(breakfast.getHashcode()));
        assertNotEquals(lunch, mealPlanComponent.getMeal(lunch.getHashcode()));

        for (MealComponent i : breakfast) {
            for (MealComponent j : mealPlanComponent.getMeal(breakfast.getHashcode())) {
                if (i.getHashcode().equals(j.getHashcode())) {
                    assertEquals(i, j);
                }
            }
        }

        for (MealComponent i : lunch) {
            for (MealComponent j : mealPlanComponent.getMeal(lunch.getHashcode())) {
                if (i.getHashcode().equals(j.getHashcode())) {
                    assertEquals(i, j);
                }
            }
        }
    }

    @Test
    void testRemoveMeal() {
        Meal breakfast = new MockMeal(2, "breakfast");
        Meal lunch = new MockMeal(4, "lunch");

        lunch.setHashcode(Hashable.doubleHashing(lunch.getHashcode()));

        breakfast.setMealComponents(testMeal.mockMealComponentsA);
        lunch.setMealComponents(testMeal.mockMealComponentsB);

        MockMealPlanComponent mealPlanComponent = new MockMealPlanComponent();

        mealPlanComponent.addMeal(breakfast);
        mealPlanComponent.addMeal(lunch);

        mealPlanComponent.removeMeal(breakfast);
        assert !mealPlanComponent.hasMeal(breakfast);

        mealPlanComponent.removeMeal(lunch);
        assert !mealPlanComponent.hasMeal(lunch);
    }

    @Test
    void testCopyConstructor() {
        Meal breakfast = new MockMeal(2, "breakfast");
        Meal lunch = new MockMeal(4, "lunch");

        lunch.setHashcode(Hashable.doubleHashing(lunch.getHashcode()));

        breakfast.setMealComponents(testMeal.mockMealComponentsA);
        lunch.setMealComponents(testMeal.mockMealComponentsB);

        MockMealPlanComponent component = new MockMealPlanComponent();

        component.addMeal(breakfast);

        MockMealPlanComponent copy = new MockMealPlanComponent(component);

        assertNotEquals(component, copy);
        assertNotEquals(
                component.getMeal(breakfast.getHashcode()),
                copy.getMeal(breakfast.getHashcode())
        );
        assertEquals(component.getHashcode(), copy.getHashcode());

        for (MealComponent i :
                component.getMeal(breakfast.getHashcode())) {
            for (MealComponent j : copy.getMeal(breakfast.getHashcode())) {
                if (Objects.equals(i.getHashcode(), j.getHashcode())) {
                    assertEquals(i, j);
                }
            }
        }

        copy.addMeal(lunch);

        assert !component.hasMeal(lunch);
    }

    @Test
    void testGetMeals() {
        Meal breakfast = new MockMeal(2, "breakfast");
        Meal lunch = new MockMeal(4, "lunch");

        ArrayList<Meal> meals = new ArrayList<>();
        meals.add(breakfast);
        meals.add(lunch);

        lunch.setHashcode(Hashable.doubleHashing(lunch.getHashcode()));

        breakfast.setMealComponents(testMeal.mockMealComponentsA);
        lunch.setMealComponents(testMeal.mockMealComponentsB);

        MockMealPlanComponent component = new MockMealPlanComponent();
        component.addMeal(breakfast);
        component.addMeal(lunch);

        for (Meal i : meals) {
            for (Meal j : component) {
                if (i.getHashcode().equals(j.getHashcode())) {
                    assertNotEquals(i, j);
                }
            }
        }
    }

    @Test
    void testSetMeals() {
        Meal breakfast = new MockMeal(2, "breakfast");
        Meal lunch = new MockMeal(4, "lunch");

        ArrayList<Meal> meals = new ArrayList<>();
        HashMap<String, Meal> mealHashMap = new HashMap<>();

        meals.add(breakfast);
        meals.add(lunch);

        mealHashMap.put(breakfast.getHashcode(), breakfast);
        mealHashMap.put(lunch.getHashcode(), lunch);

        lunch.setHashcode(Hashable.doubleHashing(lunch.getHashcode()));

        breakfast.setMealComponents(testMeal.mockMealComponentsA);
        lunch.setMealComponents(testMeal.mockMealComponentsB);

        MockMealPlanComponent component = new MockMealPlanComponent();

        component.setMeals(meals);

        for (Meal meal : meals) {
            assertNotEquals(meal, component.getMeal(meal.getHashcode()));
            assertDoesNotThrow(
                    () -> {
                        for (MealComponent i : meal) {
                            for (MealComponent j : component.getMeal(meal.getHashcode())) {
                                if (i.getHashcode().equals(j.getHashcode())) {
                                    assertEquals(i, j);
                                }
                            }
                        }
                    }
            );

            component.removeMeal(meal);
        }

        component.setMeals(mealHashMap);

        for (Map.Entry<String, Meal> entry : mealHashMap.entrySet()) {
            assertNotEquals(entry.getValue(), component.getMeal(entry.getKey()));
        }
    }
}
