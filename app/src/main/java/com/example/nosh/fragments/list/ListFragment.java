package com.example.nosh.fragments.list;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nosh.Nosh;
import com.example.nosh.R;
import com.example.nosh.controller.IngredientSorting;
import com.example.nosh.controller.IngredientStorageController;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.fragments.ingredients.AddIngredientDialog;
import com.example.nosh.fragments.ingredients.EditIngredientDialog;
import com.example.nosh.fragments.ingredients.IngredientAdapter;
import com.example.nosh.fragments.ingredients.IngredientsFragment;
import com.example.nosh.fragments.ingredients.SortIngredientDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

public class ListFragment extends Fragment implements Observer {
    private Button addButton;
    private Button sortButton;
    private ListFragmentListener listener;

    /**
     * Required empty constructor
     */
    public ListFragment() {}

    private class ListFragmentListener implements View.OnClickListener, FragmentResultListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == addButton.getId()) {
                // add to shopping list
            }

            if (v.getId() == sortButton.getId()) {
                openSortIngredientDialog();
            }
        }

        private void openSortIngredientDialog () {
            SortListDialog sortListDialog = new SortListDialog();
            sortListDialog.show(getChildFragmentManager(),"SORT_LIST");
        }

        /**
         * This method handles the results from the child fragments spawned
         */
        @Override
        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
            switch (requestKey) {
                case "sort_list":
                    // TODO: sort and update the shopping list
                    // adapter.update(ingredients);
                    // adapter.notifyItemRangeChanged(0, ingredients.size());
                    break;
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        listener = new ListFragmentListener();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        "sort_list",
                        getViewLifecycleOwner(),
                        listener
                );
        return v;
    }

    @Override
    public void update(Observable observable, Object o) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}