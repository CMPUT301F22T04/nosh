package com.example.nosh.fragments.recipes;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.nosh.controller.RecipeSorting;
import com.example.nosh.Nosh;
import com.example.nosh.R;
import com.example.nosh.controller.RecipeController;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Recipe;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

/**
 * This class is responsible for the recipe fragment.
 */

public class RecipesFragment extends Fragment implements Observer {
    //Initalize some needed variables
    private Button addBtn;
    private RecipeAdapter adapter;

    @Inject
    RecipeController controller;

    private RecipesFragmentListener listener;
    private HashMap<String, StorageReference> recipeImagesRemote;
    private ArrayList<Recipe> recipes;
    private Button sortButtonR;

    /**
     * A event listener class. This class listen all events such as click
     */
    private class RecipesFragmentListener implements
            View.OnClickListener, RecipeAdapter.RecyclerViewListener,
            FragmentResultListener {

        @Override
        public void onDeleteButtonClick(int pos) {
            if (pos >= 0) {
                controller.delete(recipes.get(pos));

                adapter.notifyItemRemoved(pos);
            }
        }


        @Override
        public void onClick(View v) {
            if (v.getId() == addBtn.getId()) {
                openAddRecipeDialog();
            }
            if (v.getId() == sortButtonR.getId()) {
                openSortRecipeDialog();
            }

        }

        @Override
        public void onEditClick(int pos) {
            Recipe recipe = recipes.get(pos);
            openEditRecipeDialog(recipe,pos);
        }

        private void openEditRecipeDialog(Recipe recipe,int pos){
            EditRecipeDialog editRecipeDialog  =  EditRecipeDialog.newInstance(recipe,pos);
            editRecipeDialog.show(getChildFragmentManager(),"EDIT_RECIPE");

        }
        private void openSortRecipeDialog() {
            SortRecipeDialog sortRecipeDialog = new SortRecipeDialog();
            sortRecipeDialog.show(getChildFragmentManager(),"SORT_RECIPE");
        }

        @Override
        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
            if (requestKey.equals("add_recipe")) {
//                ArrayList<Ingredient> ing = new ArrayList<>();
//                Ingredient ing1 = new Ingredient(2,3,"3","peen","word");
//                ing.add(ing1);
               controller.add(result.getDouble("prep"),
                       result.getInt("servings") ,
                       result.getString("category"),
                       result.getString("comments"),
                       result.getParcelable("photoUri"),
                       result.getString("name"),
                       (ArrayList<Ingredient>) result.getSerializable("ingredients")
                        );
            }
            if(requestKey.equals("sort_recipe")){
                RecipeSorting.sort(
                        recipes,
                        result.getString("type"),
                        result.getBoolean("order")
                );
                adapter.update(recipes,recipeImagesRemote);
                adapter.notifyItemRangeChanged(0, recipes.size());
                //break;
            }
            if(requestKey.equals("edit_recipe")){
                String res = result.getString("Code");
                if (res.equalsIgnoreCase("updateImage")) {
                    controller.updateNewImage(
                            result.getString("hashcode"),
                            result.getDouble("prep"),
                            result.getLong("servings"),
                            result.getString("category"),
                            result.getString("comments"),
                            result.getParcelable("photoUri"),
                            result.getString("name"),
                            (ArrayList<Ingredient>) result.getSerializable("ingredients")
                    );
                }
                else{
                    controller.update(
                            result.getString("hashcode"),
                            result.getDouble("prep"),
                            result.getLong("servings"),
                            result.getString("category"),
                            result.getString("comments"),
                            result.getString("photoUri"),
                            result.getString("name"),
                            (ArrayList<Ingredient>) result.getSerializable("ingredients")
                    );
                }
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

    @Override
    public void onAttach(@NonNull Context context) {
        ((Nosh) context.getApplicationContext())
                .getAppComponent()
                        .inject(this);

        controller.addObserver(this);

        super.onAttach(context);

        listener = new RecipesFragmentListener();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recipes = controller.retrieve();
        recipeImagesRemote = controller.getRecipeImagesRemote();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipes, container, false);


        RecyclerView recyclerView = v.findViewById(R.id.recipe_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        adapter = new RecipeAdapter(recipes, getContext(), recipeImagesRemote, listener);

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
        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        "edit_recipe",
                        getViewLifecycleOwner(),
                        listener
                );

        return v;
    }

    @Override
    public void onDestroy() {
        controller.deleteObserver(this);

        super.onDestroy();
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
        recipeImagesRemote = controller.getRecipeImagesRemote();

        adapter.update(recipes, recipeImagesRemote);
        adapter.notifyItemRangeChanged(0, recipes.size());
    }
}