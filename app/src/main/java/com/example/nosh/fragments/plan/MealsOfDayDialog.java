package com.example.nosh.fragments.plan;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;
import com.example.nosh.entity.Meal;
import com.example.nosh.fragments.plan.RecyclerViews.PlanDayRecyclerViewAdapter;

import java.util.ArrayList;

public class MealsOfDayDialog extends DialogFragment {
    public static MealsOfDayDialog newInstance() {
        return new MealsOfDayDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.meals_of_day_layout, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.meals_of_day_recycler);

        ArrayList<Meal> mealDays = new ArrayList<>();

        PlanDayRecyclerViewAdapter adapter = new PlanDayRecyclerViewAdapter(mealDays);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

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
        Window window = getDialog().getWindow();
        if(window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = Resources.getSystem().getDisplayMetrics().widthPixels;
        window.setAttributes(params);
    }
}