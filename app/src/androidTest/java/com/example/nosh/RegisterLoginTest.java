package com.example.nosh;

import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.example.nosh.auth.Login;
import com.example.nosh.auth.Register;
import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;


@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RegisterLoginTest {
    private Solo solo;
    int pause = 1000;

    String email = "testing@gmail.com";
    String password = "password12345";

    /*Establishes test rules*/
    @Rule
    public ActivityTestRule<Register> rule =
            new ActivityTestRule<>(Register.class, true, true);

    @Before
    public void setUp(){
        solo = new Solo(InstrumentationRegistry.getInstrumentation(),rule.getActivity());
    }

    @Test
    public void test1RegisterTest(){
        // Check if current activity is Register
        solo.assertCurrentActivity("Wrong activity", Register.class);
        solo.waitForActivity("Timeout", pause);

        // Enter credentials and register
        solo.enterText((EditText) solo.getView(R.id.register_email), email);
        solo.enterText((EditText) solo.getView(R.id.register_password), password);
        solo.enterText((EditText) solo.getView(R.id.confirmPassword), password);
        solo.clickOnButton("Register");
        solo.waitForActivity("Timeout", pause * 2);

    }

    @Test
    public void test2LoginTest(){
        // Go to Login activity and then check
        solo.clickOnText("Login");
        solo.assertCurrentActivity("Wrong activity", Login.class);
        solo.waitForActivity("Timeout", pause);

        // Enter credentials and login
        solo.enterText((EditText) solo.getView(R.id.login_email), email);
        solo.enterText((EditText) solo.getView(R.id.login_password), password);
        solo.clickOnButton("Login");
        solo.waitForActivity("Timeout", pause);

        // Check if current activity is MainActivity
        solo.assertCurrentActivity("Wrong activity", MainActivity.class);
        solo.waitForActivity("Timeout", pause * 2);

    }

    @After
    public void tearDown(){
        solo.finishOpenedActivities();
    }
}
