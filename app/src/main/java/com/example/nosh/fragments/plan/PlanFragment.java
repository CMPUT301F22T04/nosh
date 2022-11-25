package com.example.nosh.fragments.plan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.Nosh;
import com.example.nosh.R;
import com.example.nosh.activities.NewMealPlanActivity;
import com.example.nosh.controller.MealPlanController;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.fragments.plan.RecyclerViews.MealPlanRecyclerViewAdapter;
import com.example.nosh.fragments.plan.RecyclerViews.MealPlanRecyclerViewInterface;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanFragment extends Fragment implements
        MealPlanRecyclerViewInterface, Observer {

    private MealPlanRecyclerViewAdapter adapter;

    private ArrayList<MealPlan> mealPlans = new ArrayList<>();

    @Inject
    MealPlanController controller;

    private ActivityResultLauncher<Intent> activityResultLauncher;
    private FragmentListener fragmentListener;

    private class FragmentListener implements View.OnClickListener,
            ActivityResultCallback<ActivityResult> {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.new_meal_plan_button) {
                controller.deleteObserver(PlanFragment.this);

                Intent intent = new Intent(
                        getActivity(),
                        NewMealPlanActivity.class
                );

                activityResultLauncher.launch(intent);
            }
        }

        @Override
        public void onActivityResult(ActivityResult result) {
            controller.addObserver(PlanFragment.this);
            refreshAdapter();
        }
    }

    public PlanFragment() {
        // Required empty public constructor
    }

    public static PlanFragment newInstance(String param1, String param2) {
        return new PlanFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        ((Nosh) context.getApplicationContext())
                .getAppComponent()
                .inject(this);

        controller.addObserver(this);

        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mealPlans = controller.retrieve();

        fragmentListener = new FragmentListener();
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                fragmentListener
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_plan, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.plan_recycler_view);

        adapter = new MealPlanRecyclerViewAdapter(
                getContext(),
                mealPlans,
                this
        );

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Button newMealPlanButton = v.findViewById(R.id.new_meal_plan_button);

        newMealPlanButton.setOnClickListener(fragmentListener);

        return v;
    }

    @Override
    public void onDestroy() {
        controller.deleteObserver(this);

        super.onDestroy();
    }

    @Override
    public void onItemClick(int position) {
        openMealsOfDayDialog(position);
    }

    private void openMealsOfDayDialog(int position) {
        MealsOfDayDialog mealsOfDayDialog = MealsOfDayDialog.newInstance(
                mealPlans.get(position).getHashcode()
        );
        mealsOfDayDialog.show(getParentFragmentManager(), "MEALS_OF_DAY");
    }

    @Override
    public void update(Observable o, Object arg) {
        refreshAdapter();
    }

    private void refreshAdapter() {
        mealPlans = controller.retrieve();

        adapter.update(mealPlans);
        adapter.notifyItemRangeChanged(0, mealPlans.size());
    }
}