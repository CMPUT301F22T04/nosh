package com.example.nosh.fragments.plan;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.Nosh;
import com.example.nosh.R;
import com.example.nosh.controller.MealPlanController;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.entity.MealPlanComponent;
import com.example.nosh.fragments.plan.RecyclerViews.PlanDayRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.Objects;

import javax.inject.Inject;

public class MealsOfDayDialog extends DialogFragment {

    @Inject
    MealPlanController mealPlanController;

    private MealPlan mealPlan;

    public static MealsOfDayDialog newInstance(String mealPlanHash) {
        MealsOfDayDialog dialog = new MealsOfDayDialog();

        Bundle args = new Bundle();

        args.putString("hashcode", mealPlanHash);

        dialog.setArguments(args);

        return dialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        ((Nosh) context.getApplicationContext()).getAppComponent().inject(this);

        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        assert getArguments() != null;

        String mealPlanHash = (String) getArguments().getString("hashcode");

        mealPlan = mealPlanController.retrieve(mealPlanHash);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.meals_of_day_layout, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.meals_of_day_recycler);

        ArrayList<Pair<String, MealPlanComponent>> sortedMealPlanComponent =
                mealPlanController.sortMealPlanComponent(mealPlan);

        sortedMealPlanComponent.remove(sortedMealPlanComponent.size() - 1);
        PlanDayRecyclerViewAdapter adapter = new PlanDayRecyclerViewAdapter(
                sortedMealPlanComponent
        );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ImageButton backButton = view.findViewById(R.id.cancel_days);
        backButton.setOnClickListener(v -> dismiss());

        return view;
    }

    /**
     * Will close the fragment dialog
     */
    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        if(window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = Resources.getSystem().getDisplayMetrics().widthPixels;
        window.setAttributes(params);
    }
}
