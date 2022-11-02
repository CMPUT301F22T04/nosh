package com.example.nosh.fragments.ingredients;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.startup.AppInitializer;

import com.example.nosh.MainActivity;
import com.example.nosh.R;
import com.example.nosh.controller.IngredientStorageController;
import com.example.nosh.database.controller.DBControllerFactory;
import com.example.nosh.database.Initializer.DBControllerFactoryInitializer;
import com.example.nosh.database.controller.IngredientDBController;
import com.example.nosh.entity.Ingredient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientsFragment extends Fragment implements Observer {

    private ImageButton addButton;
    private StoredIngredientAdapter adapter;
    private IngredientStorageController controller;
    private IngredientsFragmentListener listener;
    private ArrayList<Ingredient> ingredients;
    private ImageButton sortbutton;
    private String flag = "s";




    public IngredientsFragment() {

    }

    public static IngredientsFragment newInstance() {
        return new IngredientsFragment();
    }

    private class IngredientsFragmentListener
            implements StoredIngredientAdapter.RecyclerViewListener,
            View.OnClickListener, FragmentResultListener {

        @Override
        public void onClick(View v) {



            if (v.getId() == addButton.getId()) {
                openAddIngredientDialog();
            }

            if (v.getId() == sortbutton.getId()) {
                openSortIngredientDialog();
            }
        }

        @Override
        public void onDeleteButtonClick(int pos) {
            if (pos >= 0) {
                controller.delete(ingredients.get(pos));

                adapter.notifyItemRemoved(pos);
            }
        }

        @Override
        public void onEditClick(int pos) {
            Ingredient ingredient = ingredients.get(pos);
            openEditIngredientDialog(ingredient);
        }

        private void openEditIngredientDialog(Ingredient ingredient) {
            EditIngredientDialog editIngredientDialog = EditIngredientDialog.newInstance(ingredient);
            editIngredientDialog.show(getParentFragmentManager(), "EDIT_INGREDIENT");
        }

        private void openAddIngredientDialog() {
            AddIngredientDialog addIngredientDialog = AddIngredientDialog.newInstance();
            addIngredientDialog.show(getParentFragmentManager(), "ADD_INGREDIENT");
        }

        private void openSortIngredientDialog() {
            SortIngredientDialog sortIngredientDialog = new SortIngredientDialog();
            sortIngredientDialog.show(getChildFragmentManager(),"SORT_INGREDIENT");
        }


            @Override
        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
            if (requestKey.equals("add_ingredient")) {
                controller.add(
                        (Date) result.getSerializable("date"),
                        result.getInt("qty"),
                        result.getDouble("unit"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("category"),
                        result.getString("location"));
            }
            if (requestKey.equals("edit_ingredient")) {
                flag = "true";
                controller.update(
                        result.getString("hashcode"),
                        (Date) result.getSerializable("date"),
                        result.getInt("qty"),
                        result.getDouble("unit"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("category"),
                        result.getString("location"));


            }

            if (requestKey.equals("sort_description")) {
                ingredients = controller.retrieve();
                Collections.sort(ingredients, Ingredient.DescriptionComparator);
                adapter.update(ingredients);
                adapter.notifyItemRangeChanged(0, ingredients.size());
            }
            if (requestKey.equals("sort_date")) {
                ingredients = controller.retrieve();
                Collections.sort(ingredients, Ingredient.DateComparator);
                adapter.update(ingredients);
                adapter.notifyItemRangeChanged(0, ingredients.size());
            }
            if (requestKey.equals("sort_location")) {
                ingredients = controller.retrieve();
                Collections.sort(ingredients, Ingredient.LocationComparator);
                adapter.update(ingredients);
                adapter.notifyItemRangeChanged(0, ingredients.size());
            }
            if (requestKey.equals("sort_category")) {
                ingredients = controller.retrieve();
                Collections.sort(ingredients, Ingredient.CategoryComparator);
                adapter.update(ingredients);
                adapter.notifyItemRangeChanged(0, ingredients.size());
            }

                /**Descending
                 *
                 */
            if (requestKey.equals("sort_descriptionD")) {
                ingredients = controller.retrieve();
                Collections.sort(ingredients, Ingredient.DescriptionComparatorD);
                adapter.update(ingredients);
                adapter.notifyItemRangeChanged(0, ingredients.size());
            }
            if (requestKey.equals("sort_dateD")) {
                ingredients = controller.retrieve();
                Collections.sort(ingredients, Ingredient.DateComparatorD);
                adapter.update(ingredients);
                adapter.notifyItemRangeChanged(0, ingredients.size());
            }
            if (requestKey.equals("sort_locationD")) {
                ingredients = controller.retrieve();
                Collections.sort(ingredients, Ingredient.LocationComparatorD);
                adapter.update(ingredients);
                adapter.notifyItemRangeChanged(0, ingredients.size());
            }
            if (requestKey.equals("sort_categoryD")) {
                ingredients = controller.retrieve();
                Collections.sort(ingredients, Ingredient.CategoryComparatorD);
                adapter.update(ingredients);
                adapter.notifyItemRangeChanged(0, ingredients.size());
            }

        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBControllerFactory factory =
                AppInitializer.getInstance(requireContext()).initializeComponent(DBControllerFactoryInitializer.class);

        controller =
                new IngredientStorageController(factory.createAccessController(IngredientDBController.class.getSimpleName()), this);
        listener = new IngredientsFragmentListener();

        ingredients = controller.retrieve();
        Collections.sort(ingredients, Ingredient.DescriptionComparator);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ingredients, container, false);


        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        adapter = new StoredIngredientAdapter(listener, getContext(), ingredients);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        addButton = v.findViewById(R.id.add_btn);
        addButton.setOnClickListener(listener);

        sortbutton = v.findViewById(R.id.sort_button);
        sortbutton.setOnClickListener(listener);

        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        "add_ingredient",
                        getViewLifecycleOwner(),
                        listener);
        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        "edit_ingredient",
                        getViewLifecycleOwner(),
                        listener);

        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        "sort_description",
                        getViewLifecycleOwner(),
                        listener);
        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        "sort_date",
                        getViewLifecycleOwner(),
                        listener);
        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        "sort_location",
                        getViewLifecycleOwner(),
                        listener);
        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        "sort_category",
                        getViewLifecycleOwner(),
                        listener);
        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        "sort_descriptionD",
                        getViewLifecycleOwner(),
                        listener);
        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        "sort_dateD",
                        getViewLifecycleOwner(),
                        listener);
        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        "sort_locationD",
                        getViewLifecycleOwner(),
                        listener);
        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        "sort_categoryD",
                        getViewLifecycleOwner(),
                        listener);

        return v;
    }

    @Override
    public void update(Observable o, Object arg) {
        //flag = "true";

        ingredients = controller.retrieve();
        if(flag == "true"){
            System.out.println(2);
            Collections.sort(ingredients, Ingredient.DescriptionComparator);

        }


        adapter.update(ingredients);
        adapter.notifyItemRangeChanged(0, ingredients.size());


    }

}
