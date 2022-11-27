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
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.Nosh;
import com.example.nosh.R;
import com.example.nosh.activities.NewMealPlanActivity;
import com.example.nosh.controller.MealPlanController;
import com.example.nosh.entity.MealPlan;
import com.example.nosh.fragments.plan.RecyclerViews.MealPlanRecyclerViewAdapter;
import com.example.nosh.fragments.plan.RecyclerViews.MealPlanRecyclerViewListener;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlanFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlanFragment extends Fragment implements Observer {

    private MealPlanRecyclerViewAdapter adapter;

    private ArrayList<MealPlan> mealPlans = new ArrayList<>();

    @Inject
    MealPlanController controller;

    private ActivityResultLauncher<Intent> activityResultLauncher;
    private FragmentListener fragmentListener;

    private class FragmentListener implements View.OnClickListener,
            ActivityResultCallback<ActivityResult>, MealPlanRecyclerViewListener,
            FragmentResultListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.new_meal_plan_button) {
                controller.deleteObserver(PlanFragment.this);

                Intent intent = new Intent(
                        getActivity(),
                        NewMealPlanActivity.class
                );

                activityResultLauncher.launch(intent);
            } else if (v.getId() == R.id.scale_recipes_button) {
                ScaleMealPlanDialog scaleMealPlanDialog = ScaleMealPlanDialog.newInstance();
                scaleMealPlanDialog.show(getParentFragmentManager(), "SCALE_MEAL_PLAN");
            }
        }

        @Override
        public void onActivityResult(ActivityResult result) {
            controller.addObserver(PlanFragment.this);
            refreshAdapter();
        }

        @Override
        public void onItemClick(int position) {
            // TODO: put a fragment container in this xml file instead of replacing the main one???
            if (position >= 0) {
                openMealsOfDayDialog(position);
            }
        }

        @Override
        public void onDeleteItemClick(int position) {
            if (position >= 0) {
                controller.delete(mealPlans.get(position));

                adapter.notifyItemRemoved(position);
            }
        }

        @Override
        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
            if (requestKey.compareTo(ScaleMealPlanDialog.TAG) == 0) {
                controller.scaling(
                        result.getString("scalingType"),
                        result.getInt("scaling"),
                        result.getString("mealPlanHash"),
                        result.getString("date"),
                        result.getString("mealHash")
                );
            }
        }
    }

    public PlanFragment() {
        // Required empty public constructor
    }

    public static PlanFragment newInstance() {
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
                fragmentListener
        );

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Button newMealPlanButton = v.findViewById(R.id.new_meal_plan_button);
        Button scaleMealPlanButton = v.findViewById(R.id.scale_recipes_button);

        newMealPlanButton.setOnClickListener(fragmentListener);
        scaleMealPlanButton.setOnClickListener(fragmentListener);

        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        ScaleMealPlanDialog.TAG,
                        getViewLifecycleOwner(),
                        fragmentListener
                );

        return v;
    }

    @Override
    public void onDestroy() {
        controller.deleteObserver(this);

        super.onDestroy();
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