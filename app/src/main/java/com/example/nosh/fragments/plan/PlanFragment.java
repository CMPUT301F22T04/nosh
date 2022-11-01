package com.example.nosh.fragments.plan;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nosh.R;
import com.example.nosh.fragments.plan.RecyclerViews.MealSpan_RecyclerViewAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanFragment extends Fragment {
    //ArrayList<MockMealPlan> mealPlans = new ArrayList<>();
    ArrayList<MealDay> mealDays = new ArrayList<>();

    public PlanFragment() {
        // Required empty public constructor
    }

    public static PlanFragment newInstance(String param1, String param2) {
        PlanFragment fragment = new PlanFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_plan, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.plan_recycler_view);

        setUpTestData();

        //MealPlan_RecyclerViewAdapter adapter = new MealPlan_RecyclerViewAdapter(getContext(), mealPlans);
        //recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MealSpan_RecyclerViewAdapter adapter = new MealSpan_RecyclerViewAdapter(getContext(), mealDays);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    private void setUpTestData(){
        //String[] planNames = {"Vegan Diet", "Uni Meal Prep", "Mom's Recipes"};
        //String[] planSpans = {"Dec 10 2022 - Dec 14 2022", "Dec 15 2022 - Dec 24 2022", "Jan 1 2022 - Jan 30 2022"};
        String[] days = {"Dec 10 2022", "Dec 15 2022", "Jan 1 2022"};

        for (int i = 0; i < days.length; i++){
            mealDays.add(new MealDay(days[i]));
        }
    }
}