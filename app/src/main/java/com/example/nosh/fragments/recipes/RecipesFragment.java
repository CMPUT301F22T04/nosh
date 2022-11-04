package com.example.nosh.fragments.recipes;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.startup.AppInitializer;

import com.example.nosh.R;
import com.example.nosh.controller.IngredientSorting;
import com.example.nosh.controller.RecipeController;
import com.example.nosh.controller.RecipeSorting;
import com.example.nosh.database.Initializer.DBControllerFactoryInitializer;
import com.example.nosh.database.Initializer.FirebaseStorageControllerInitializer;
import com.example.nosh.database.controller.DBControllerFactory;
import com.example.nosh.database.controller.FirebaseStorageController;
import com.example.nosh.database.controller.RecipeDBController;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Recipe;
import com.example.nosh.fragments.ingredients.SortIngredientDialog;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class RecipesFragment extends Fragment implements Observer {
    //Initalize some needed variables
    private ImageButton addBtn;
    private RecipeAdapter adapter;
    private RecipeController controller;
    private RecipesFragmentListener listener;
    private ArrayList<Recipe> recipes;
    private ImageButton sortButtonR;

    /**
     * A event listener class. This class listen all events such as click
     */
    private class RecipesFragmentListener implements
            View.OnClickListener, RecipeAdapter.RecyclerViewListener,
            FragmentResultListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == addBtn.getId()) {
                openAddRecipeDialog();
                //openSortRecipeDialog();
            }
            if (v.getId() == sortButtonR.getId()) {
                openSortRecipeDialog();
            }
        }

        @Override
        public void onEditClick(int pos) {
            Recipe recipe = recipes.get(pos);
//            openEditIngredientDialog(ingredient);
        }

        @Override
        public void onDeleteButtonClick(int pos) {
            if (pos >= 0) {
                controller.delete(recipes.get(pos));

                adapter.notifyItemRemoved(pos);
            }
        }

        private void openSortRecipeDialog() {
            SortRecipeDialog sortRecipeDialog = new SortRecipeDialog();
            sortRecipeDialog.show(getChildFragmentManager(),"SORT_RECIPE");
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
            if (requestKey.equals("add_recipe")) {
                ArrayList<Ingredient> ing = new ArrayList<>();
               controller.add(result.getDouble("prep"),
                       result.getInt("servings") ,
                        result.getString("category"),
                        result.getString("comments"),
                        result.getString("photo"),
                        result.getString("name"),
                        ing
                        );
            }
            if(requestKey.equals("sort_recipe")){
                RecipeSorting.sort(
                        recipes,
                        result.getString("type"),
                        result.getBoolean("order")
                );
                adapter.update(recipes);
                adapter.notifyItemRangeChanged(0, recipes.size());
                //break;
            }
        }

        private void openAddRecipeDialog() {
            AddRecipeDialog addRecipeDialog = AddRecipeDialog.newInstance();
            addRecipeDialog.show(getParentFragmentManager(), "ADD_RECIPE");
        }
    }

    public RecipesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment RecipesFragment.
     */
    public static RecipesFragment newInstance() {
        return new RecipesFragment();
    }

    /**
     * on creation of fragment we run this class to inialtize
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBControllerFactory factory = AppInitializer
                .getInstance(requireContext())
                .initializeComponent(DBControllerFactoryInitializer.class);

        FirebaseStorageController storageController = AppInitializer
                .getInstance(requireContext())
                .initializeComponent(FirebaseStorageControllerInitializer.class);

        controller = new RecipeController(
                requireContext(),
                factory.createAccessController(RecipeDBController.class.getSimpleName()),
                storageController,
                this);

        listener = new RecipesFragmentListener();

        recipes = controller.retrieve();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipes, container, false);


        RecyclerView recyclerView = v.findViewById(R.id.recipe_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        adapter = new RecipeAdapter(recipes, getContext(), listener);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        addBtn = v.findViewById(R.id.add_recipe_btn);
        sortButtonR = v.findViewById(R.id.sort_buttonR);
        sortButtonR.setOnClickListener(listener);
        addBtn.setOnClickListener(listener);

        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        "add_recipe",
                        getViewLifecycleOwner(),
                        listener);

        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        "sort_recipe",
                        getViewLifecycleOwner(),
                        listener
                );

        return v;
    }

    /**
     * Receive notification from Recipe Repository that there are new changes in
     * data / entity objects. Retrieve the latest copy of the data
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        recipes = controller.retrieve();

        adapter.update(recipes);
        adapter.notifyItemRangeChanged(0, recipes.size());
    }
}