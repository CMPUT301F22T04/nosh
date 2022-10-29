package com.example.nosh.fragments.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.startup.AppInitializer;

import com.example.nosh.R;
import com.example.nosh.controller.RecipeController;
import com.example.nosh.database.Initializer.DBControllerFactoryInitializer;
import com.example.nosh.database.controller.DBControllerFactory;
import com.example.nosh.database.controller.RecipeDBController;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Recipe;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipesFragment extends Fragment implements Observer {

    private ImageButton addBtn;

    private RecipeController controller;

    private RecipesFragmentListener listener;

    private ArrayList<Recipe> recipes;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private class RecipesFragmentListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == addBtn.getId()) {
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
        }
    }

    public RecipesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipesFragment newInstance(String param1, String param2) {
        RecipesFragment fragment = new RecipesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBControllerFactory factory = AppInitializer
                .getInstance(requireContext())
                .initializeComponent(DBControllerFactoryInitializer.class);

        controller = new RecipeController(
                factory
                        .createAccessController(RecipeDBController.class.getSimpleName()), this);

        listener = new RecipesFragmentListener();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipes, container, false);

        addBtn = v.findViewById(R.id.add_recipe_btn);

        addBtn.setOnClickListener(listener);

        return v;
    }

    @Override
    public void update(Observable o, Object arg) {
        recipes = controller.retrieve();

        for (Recipe recipe: recipes) {
            System.out.println(recipe.getHashcode());
            for (Ingredient ingredient: recipe.getIngredients()) {
                System.out.println(ingredient.getAmount());
            }
        }
    }
}