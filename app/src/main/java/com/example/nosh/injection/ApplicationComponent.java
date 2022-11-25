package com.example.nosh.injection;

import com.example.nosh.MainActivity;
import com.example.nosh.activities.AddMealsToDaysActivity;
import com.example.nosh.activities.NewMealPlanActivity;
import com.example.nosh.auth.Login;
import com.example.nosh.auth.Register;
import com.example.nosh.fragments.ingredients.IngredientsFragment;
import com.example.nosh.fragments.plan.MealsOfDayDialog;
import com.example.nosh.fragments.plan.PlanFragment;
import com.example.nosh.fragments.recipes.RecipesFragment;

import javax.inject.Singleton;

import dagger.Component;


// Definition of the Application dependencies graph
@Singleton
@Component(modules = FirebaseModule.class)
public interface ApplicationComponent {

    // Include application context (and Activity context)

    // Tells Dagger that Login requests injection so that graph needs to
    // satisfy all the dependencies of the fields that Login is injecting.
    void inject(Login loginActivity);

    // Tells Dagger that Register requests injection so that graph needs to
    // satisfy all the dependencies of the fields that Register is injecting.
    void inject(Register registerActivity);

    // ...
    void inject(MainActivity mainActivity);

    void inject(NewMealPlanActivity newMealPlanActivity);

    void inject(AddMealsToDaysActivity addMealsToDaysActivity);

    // ...
    void inject(IngredientsFragment ingredientsFragment);

    // ...
    void inject(RecipesFragment recipesFragment);

    void inject(PlanFragment planFragment);

    void inject(MealsOfDayDialog mealsOfDayDialog);
}
