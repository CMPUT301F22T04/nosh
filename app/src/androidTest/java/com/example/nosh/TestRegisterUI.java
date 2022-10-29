package com.example.nosh;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import static org.hamcrest.CoreMatchers.notNullValue;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.nosh.auth.Register;

import org.junit.Rule;
import org.junit.Test;

/**
 * Tests the functionality if the register activity
 */
public class TestRegisterUI {
    @Rule
    public ActivityScenarioRule<Register> activityRule =
            new ActivityScenarioRule<Register>(Register.class);

    /**
     * Tests a successful user registration
     */
    @Test
    public void user_registration() {
        // TODO: Mock firebase valid registration
        // check we are in the correct activity
        onView(withId(R.id.register_activity)).check(matches(isDisplayed()));
        // type email address
        onView(withId(R.id.register_email)).perform(typeText("userTest@example.com"));
        // type and confirm password
        onView(withId(R.id.register_password)).perform(typeText("123"));
        onView(withId(R.id.confirmPassword)).perform(typeText("123"));
        // click register
        onView(withId(R.id.register_btn)).perform(click());
    }

    /**
     * Tests a failed password confirmation
     */
    @Test
    public void invalid_password() {
        // check we are in the correct activity
        onView(withId(R.id.register_activity)).check(matches(isDisplayed()));
        // type email address
        onView(withId(R.id.register_email)).perform(typeText("userTest@example.com"));
        // type password and enter wrong confirmation
        onView(withId(R.id.register_password)).perform(typeText("123"));
        onView(withId(R.id.confirmPassword)).perform(typeText("321"));
        // click register
        onView(withId(R.id.register_btn)).perform(click());
        // assert activity did not switch
        onView(withId(R.id.register_activity)).check(matches(isDisplayed()));
    }

    /**
     * Tests navigation to login page
     */
    @Test
    public void login_page_navigation() {
        // check we are in the correct activity
        onView(withId(R.id.register_activity)).check(matches(isDisplayed()));
        // click Login
        onView(withId(R.id.login_page_btn)).perform(click());
        onView(withText("Login")).check(matches(notNullValue()));
        onView(withText("Login")).perform(click());
        // check activity switched
        onView(withId(R.id.login_activity)).check(matches(isDisplayed()));
    }
}
