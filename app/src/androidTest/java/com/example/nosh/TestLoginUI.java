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

import com.example.nosh.auth.Login;

import org.junit.Rule;
import org.junit.Test;

/**
 * Tests the functionality of the login activity
 */
public class TestLoginUI {
    @Rule
    public ActivityScenarioRule<Login> activityRule =
            new ActivityScenarioRule<Login>(Login.class);

    /**
     * Tests a successful user login
     */
    @Test
    public void user_login() {
        // TODO: Mock firebase valid login
        // check we are in the correct activity
        onView(withId(R.id.login_activity)).check(matches(isDisplayed()));
        // type email address
        onView(withId(R.id.login_email)).perform(typeText("userTest@example.com"));
        // type and con
        onView(withId(R.id.login_password)).perform(typeText("123"));
        // click register
        onView(withId(R.id.login_button)).perform(click());
    }

    /**
     * Tests a failed login attempt
     */
    @Test
    public void failed_login() {
        // TODO: Mock firebase invalid login
        // check we are in the correct activity
        onView(withId(R.id.login_activity)).check(matches(isDisplayed()));
        // type email address
        onView(withId(R.id.login_email)).perform(typeText("userTest@example.com"));
        // type password and enter wrong confirmation
        onView(withId(R.id.login_password)).perform(typeText("123"));
        // click register
        onView(withId(R.id.login_button)).perform(click());
        // assert activity did not switch
        onView(withId(R.id.login_activity)).check(matches(isDisplayed()));
    }

    /**
     * Tests navigation to register page
     */
    @Test
    public void register_page_navigation() {
        // check we are in the correct activity
        onView(withId(R.id.login_activity)).check(matches(isDisplayed()));
        // click Register
        onView(withId(R.id.register_page_btn)).perform(click());
        onView(withText("Register")).check(matches(notNullValue()));
        onView(withText("Register")).perform(click());
        // check activity switched
        onView(withId(R.id.register_activity)).check(matches(isDisplayed()));
    }
}
