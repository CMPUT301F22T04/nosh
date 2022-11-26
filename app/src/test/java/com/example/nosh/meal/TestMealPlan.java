package com.example.nosh.meal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.nosh.entity.Meal;
import com.example.nosh.entity.MealComponent;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.entity.MealPlanComponent;
import com.example.nosh.utils.DateUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class TestMealPlan {

    ArrayList<Meal> meals;

    TestMeal testMeal;

    @BeforeEach
    public void setup() {
        testMeal = new TestMeal();
        testMeal.setup();

        meals = new ArrayList<>();

        Meal mealA = new Meal(5, "2022-11-26", "hash","breakfast");
        Meal mealB = new Meal(10, "2022-11-26", "hash","lunch");

        mealA.setMealComponents(testMeal.mockMealComponentsA);
        mealB.setMealComponents(testMeal.mockMealComponentsB);

        meals.add(mealA);
        meals.add(mealB);
    }

    @Test
    void testDate() {
        MealPlan mealPlan = new MealPlan(
                "Meal Plan 1",
                DateUtil.getCalendar("2022-11-17").getTime(),
                DateUtil.getCalendar("2022-11-27").getTime()
        );

        // TODO: a better solution to generate test case
        ArrayList<String> expected = new ArrayList<>();

        expected.add("2022-11-17");
        expected.add("2022-11-18");
        expected.add("2022-11-19");
        expected.add("2022-11-20");
        expected.add("2022-11-21");
        expected.add("2022-11-22");
        expected.add("2022-11-23");
        expected.add("2022-11-24");
        expected.add("2022-11-25");
        expected.add("2022-11-26");
        expected.add("2022-11-27");


        for (String dateString :
                expected) {
            assert mealPlan.getPlans().containsKey(dateString);
        }

        assertEquals(mealPlan.getTotalDays(), 11);
    }

    @Test
    void test() {
        MealPlan mealPlan = new MealPlan(
                "Meal Plan 1",
                DateUtil.getCalendar("2022-11-17").getTime(),
                DateUtil.getCalendar("2022-11-27").getTime()
        );

        mealPlan.addMealToDay("2022-11-17", meals.get(0));
        mealPlan.addMealToDay("2022-11-27", meals.get(1));

        for (Map.Entry<String, MealPlanComponent> entry : mealPlan) {
            System.out.println(entry.getKey());

            for (Meal meal : entry.getValue()) {
                System.out.println(meal.getName());
               for (MealComponent mealComponent : meal) {
                   System.out.println(mealComponent.getName());
               }
            }
        }
    }

    @Test
    void testAddMeal() {
        MealPlan mealPlan = new MealPlan(
                "Meal Plan 1",
                DateUtil.getCalendar("2022-11-17").getTime(),
                DateUtil.getCalendar("2022-11-27").getTime()
        );

        mealPlan.addMealToDay("2022-11-17", meals.get(0));
        mealPlan.addMealToDay("2022-11-27", meals.get(1));
    }

    @Test
    void testUpdateMeal() {
        MockMealPlan mealPlan = new MockMealPlan(
                "Meal Plan 1",
                DateUtil.getCalendar("2022-11-15").getTime(),
                DateUtil.getCalendar("2022-11-20").getTime()
        );

        mealPlan.addMealToDay("2022-11-15", meals.get(0));
        mealPlan.updateMealToDay("2022-11-15", meals.get(1));
    }

    @Test
    void testGetPlans() {
        MockMealPlan mealPlan = new MockMealPlan(
                "Meal Plan 2",
                DateUtil.getCalendar("2022-11-25").getTime(),
                DateUtil.getCalendar("2022-11-30").getTime()
        );

        mealPlan.addMealToDay("2022-11-25", meals.get(0));
        mealPlan.addMealToDay("2022-11-30", meals.get(1));

        HashMap<String, MealPlanComponent> plans = mealPlan.getPlans();
    }

    @Test
    void testSetPlans() {
        MockMealPlan mealPlan = new MockMealPlan(
                "Meal Plan 3",
                DateUtil.getCalendar("2022-12-05").getTime(),
                DateUtil.getCalendar("2022-12-10").getTime()
        );

        MealPlanComponent componentA = new MockMealPlanComponent();
        componentA.addMeal(meals.get(0));


        MealPlanComponent componentB = new MockMealPlanComponent();
        componentB.addMeal(meals.get(1));

        HashMap<String, MealPlanComponent> plans = new HashMap<>();
        plans.put("2022-12-05", componentA);
        plans.put("2022-12-10", componentB);

        mealPlan.setPlans(plans);
    }
}
