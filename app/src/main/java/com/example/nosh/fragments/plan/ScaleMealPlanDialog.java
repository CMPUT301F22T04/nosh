package com.example.nosh.fragments.plan;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.nosh.Nosh;
import com.example.nosh.R;
import com.example.nosh.controller.MealPlanController;
import com.example.nosh.entity.Meal;
import com.example.nosh.entity.MealPlan;

import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;


public class ScaleMealPlanDialog extends DialogFragment {

    static final String TAG = "SCALE_MEAL_PLAN";
    static final String PLAN_LEVEL = "PLAN";
    static final String DAY_LEVEL = "DAY";
    static final String MEAL_LEVEL = "MEAL";

    @Inject
    MealPlanController mealPlanController;

    private Spinner mealPlansSpinner;
    private Spinner mealPlanDaySpinner;
    private Spinner mealSpinner;
    private EditText servingEditText;

    private ArrayList<MealPlan> mealPlans;
    private ArrayList<Meal> meals;
    private MealPlan selectedMealPlan = null;
    private Meal selectedMeal = null;

    private String scalingType = PLAN_LEVEL;

    private final class DialogListener implements View.OnClickListener,
            AdapterView.OnItemSelectedListener {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.confirm_scaling) {
                if (inputVerification()) {

                    Bundle bundle = new Bundle();

                    bundle.putString("scalingType", scalingType);
                    bundle.putInt("scaling", Integer.parseInt(servingEditText.getText().toString()));
                    bundle.putString("mealPlanHash", selectedMealPlan.getHashcode());

                    if (scalingType.compareTo(DAY_LEVEL) == 0 &&
                            mealPlanDaySpinner.getSelectedItemPosition() != 0) {
                        bundle.putString("date", (String) mealPlanDaySpinner.getSelectedItem());
                    } else if (scalingType.compareTo(MEAL_LEVEL) == 0) {
                        bundle.putString("date", (String) mealPlanDaySpinner.getSelectedItem());
                        bundle.putString("mealHash", selectedMeal.getHashcode());
                    }

                    requireActivity()
                            .getSupportFragmentManager()
                            .setFragmentResult(TAG, bundle);

                    dismiss();
                }
            }
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if (parent.getId() == mealPlansSpinner.getId()) {
                onMealPlanSpinnerItemSelected(position);
            } else if (parent.getId() == mealPlanDaySpinner.getId()) {
                onMealPlanDaySpinnerItemSelected(position);
            } else if (parent.getId() == mealSpinner.getId()) {
                selectedMeal = meals.get(position);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }

        private void onMealPlanSpinnerItemSelected(int position) {
            if (position > 0) {

                selectedMealPlan = mealPlans.get(position - 1);

                ArrayList<String> dateString = selectedMealPlan.getDaysWithMeals();

                Collections.sort(dateString);
                dateString.add(0, "Scale all meals in this meal plan");

                ArrayAdapter<String> mealPlanDayAdapter = new ArrayAdapter<>(
                        getContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        dateString
                );

                selectedMeal = null;
                meals = null;

                mealPlanDaySpinner.setAdapter(mealPlanDayAdapter);
                mealPlanDaySpinner.setEnabled(true);
            } else {
                scalingType = PLAN_LEVEL;

                selectedMealPlan = null;
                selectedMeal = null;
                meals = null;

                mealPlanDaySpinner.setAdapter(null);
                mealPlanDaySpinner.setEnabled(false);
            }
        }

        private void onMealPlanDaySpinnerItemSelected(int position) {
            if (position > 0) {
                meals = selectedMealPlan.getMealsByDay(
                        (String) mealPlanDaySpinner.getSelectedItem()
                );

                ArrayList<String> mealNames = new ArrayList<>();
                for (Meal meal : meals) {
                    mealNames.add(meal.getName());
                }

                ArrayAdapter<String> mealAdapter = new ArrayAdapter<>(
                        requireContext(),
                        android.R.layout.simple_spinner_dropdown_item,
                        mealNames
                );

                scalingType = MEAL_LEVEL;
                selectedMeal = meals.get(0);
                mealSpinner.setAdapter(mealAdapter);
                mealSpinner.setEnabled(true);
            } else {
                scalingType = PLAN_LEVEL;
                meals = null;
                selectedMeal = null;
                mealSpinner.setAdapter(null);
                mealSpinner.setEnabled(false);
            }
        }
    }

    public static ScaleMealPlanDialog newInstance() {
        return new ScaleMealPlanDialog();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        ((Nosh) context.getApplicationContext()).getAppComponent().inject(this);

        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mealPlans = mealPlanController.retrieve();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.scale_meal_plan_dialog, container, false);

        mealPlansSpinner = view.findViewById(R.id.meal_plan_spinner);
        mealPlanDaySpinner = view.findViewById(R.id.meal_plan_day_spinner);
        mealSpinner = view.findViewById(R.id.meal_spinner);
        servingEditText = view.findViewById(R.id.scale_field);

        Button confirmButton = view.findViewById(R.id.confirm_scaling);

        DialogListener dialogListener = new DialogListener();

        ArrayList<String> mealPlanName = new ArrayList<>();

        mealPlanName.add("");

        for (MealPlan mealPlan : mealPlans) {
            mealPlanName.add(mealPlan.getName());
        }

        ArrayAdapter<String> mealPlanNameAdapter = new ArrayAdapter<>(
                getContext(),
                android.R.layout.simple_spinner_dropdown_item,
                mealPlanName);

        mealPlansSpinner.setAdapter(mealPlanNameAdapter);

        mealPlanDaySpinner.setEnabled(false);
        mealSpinner.setEnabled(false);

        confirmButton.setOnClickListener(dialogListener);
        mealPlansSpinner.setOnItemSelectedListener(dialogListener);
        mealPlanDaySpinner.setOnItemSelectedListener(dialogListener);
        mealSpinner.setOnItemSelectedListener(dialogListener);

        return view;
    }

    private boolean inputVerification() {
        if (servingEditText.getText().toString().compareTo("") == 0) {
            Toast.makeText(
                    requireContext(),
                    "Servings cannot be empty",
                    Toast.LENGTH_SHORT
            ).show();

            return false;
        }

        if (mealPlansSpinner.getSelectedItemPosition() <= 0) {
            Toast.makeText(
                    requireContext(),
                    "Please select a meal plan",
                    Toast.LENGTH_SHORT
            ).show();

            return false;
        }

        return true;
    }
}
