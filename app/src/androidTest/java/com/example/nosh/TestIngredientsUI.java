package com.example.nosh;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.nosh.auth.Login;
import com.example.nosh.fragments.ingredients.IngredientsFragment;

import org.junit.Rule;
import org.junit.Test;

/**
 * Tests all the functionality of ingredients
 */
public class TestIngredientsUI {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<MainActivity>(MainActivity.class);

    /**
     * Tests a successful user login
     */
    @Test
    public void add_ingredient() {
        //launchFragmentInContainer
    }
}
