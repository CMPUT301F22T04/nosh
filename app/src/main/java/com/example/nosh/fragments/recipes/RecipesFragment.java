package com.example.nosh.fragments.recipes;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.startup.AppInitializer;

import com.example.nosh.R;
import com.example.nosh.controller.RecipeController;
import com.example.nosh.database.Initializer.DBControllerFactoryInitializer;
import com.example.nosh.database.Initializer.FirebaseStorageControllerInitializer;
import com.example.nosh.database.controller.DBControllerFactory;
import com.example.nosh.database.controller.FirebaseStorageController;
import com.example.nosh.database.controller.RecipeDBController;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Recipe;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;


public class RecipesFragment extends Fragment implements Observer {

    private ImageButton addBtn;
    private RecipeAdapter adapter;
    private RecipeController controller;
    private RecipesFragmentListener listener;
    private ArrayList<Recipe> recipes;
    private FirebaseStorageController storageController;

    ActivityResultLauncher<Intent> launcher;

    /**
     * A event listener class. This class listen all events such as click
     */
    private class RecipesFragmentListener implements
            View.OnClickListener, RecipeAdapter.RecyclerViewListener,
            ActivityResultCallback<ActivityResult> {

        @Override
        public void onClick(View v) {
            if (v.getId() == addBtn.getId()) {
                // Transition to Add Recipe UI
                testAddRecipe();
            } else if (v.getId() == R.id.pick_img) {
                testPickImage();
            }
        }

        @Override
        public void onEditClick(int pos) {
            System.out.println("Entry " + pos);
            // Transition to Edit / View recipe
        }

        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();

                if (data != null && data.getData() != null) {
                    Uri selectedImageUri = data.getData();
                    if (null != selectedImageUri) {
                        storageController.add(selectedImageUri);
                    }
                }
            }
        }
    }

    public RecipesFragment() {
        // Required empty public constructor
    }

    private void testAddRecipe() {
        ArrayList<Ingredient> ingredients = new ArrayList<>();

        ingredients.add(new Ingredient(
                1.00,
                1,
                "category",
                "description",
                "name"
        ));

        ingredients.add(new Ingredient(
                2.00,
                2,
                "category",
                "description",
                "name"
        ));

        controller.add(
                2.00,
                1,
                "category A",
                "comments",
                "photo",
                "title",
                ingredients
        );
    }


    private void testPickImage() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launcher.launch(i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBControllerFactory factory = AppInitializer
                .getInstance(requireContext())
                .initializeComponent(DBControllerFactoryInitializer.class);

        controller = new RecipeController(
                factory
                        .createAccessController(
                                RecipeDBController.class.getSimpleName()), this);

        storageController = AppInitializer
                .getInstance(requireContext())
                .initializeComponent(FirebaseStorageControllerInitializer.class);

        listener = new RecipesFragmentListener();

        recipes = controller.retrieve();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipes, container, false);


        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), listener
        );

        RecyclerView recyclerView = v.findViewById(R.id.recipe_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        // Test for picking image
        Button btn = v.findViewById(R.id.pick_img);

        adapter = new RecipeAdapter(recipes, getContext(), listener);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        addBtn = v.findViewById(R.id.add_recipe_btn);

        btn.setOnClickListener(listener);
        addBtn.setOnClickListener(listener);

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