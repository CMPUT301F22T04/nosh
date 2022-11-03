package com.example.nosh.fragments.recipes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.startup.AppInitializer;

import com.example.nosh.R;
import com.example.nosh.controller.RecipeController;
import com.example.nosh.database.Initializer.DBControllerFactoryInitializer;
import com.example.nosh.database.controller.DBControllerFactory;
import com.example.nosh.database.controller.RecipeDBController;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Recipe;

import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipesFragment extends Fragment implements Observer {
    //Initalize some needed variables
    private ImageButton addBtn;

    private RecipeController controller;

    private StoredRecipeAdapter adapter;

    private RecipesFragmentListener listener;

    private ArrayList<Recipe> recipes;



    private class RecipesFragmentListener implements View.OnClickListener, FragmentResultListener,
    StoredRecipeAdapter.RecyclerViewListener{

        @Override
        public void onClick(View v) {
            if (v.getId() == addBtn.getId()) {
                AddRecipeDialog addRecipeDialog = AddRecipeDialog.newInstance();
                addRecipeDialog.show(getParentFragmentManager(),"ADD_RECIPE");
            }
        }
        @Override
        public void onEditClick(int pos) {
            Recipe recipe = recipes.get(pos);
//            openEditIngredientDialog(ingredient);
        }
        @Override
        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
            if (requestKey.equals("add_recipe")) {
                ArrayList<Ingredient> ing = new ArrayList<Ingredient>();
               controller.add(result.getDouble("prep"),
                       result.getInt("servings") ,
                        result.getString("category"),
                        result.getString("comments"),
                        result.getString("photo"),
                        result.getString("name"),
                        ing
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
     * @return A new instance of fragment RecipesFragment.
     */
    public static RecipesFragment newInstance() {
        RecipesFragment fragment = new RecipesFragment();
        return fragment;
    }

    @Override
    /**
     * on creation of fragment we run this class to inialtize
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initalize the db
        DBControllerFactory factory = AppInitializer
                .getInstance(requireContext())
                .initializeComponent(DBControllerFactoryInitializer.class);

        //intialize recipecontroller
        controller = new RecipeController(
                factory
                        .createAccessController(RecipeDBController.class.getSimpleName()), this);
        //initalize Fragment listener.
        listener = new RecipesFragmentListener();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recipes, container, false);

        adapter = new StoredRecipeAdapter(listener,getContext(),recipes);

        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        addBtn = v.findViewById(R.id.add_recipe_btn);

        addBtn.setOnClickListener(listener);
        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        "add_recipe",
                        getViewLifecycleOwner(),
                        listener);

        return v;
    }

    @Override
    public void update(Observable o, Object arg) {
        recipes = controller.retrieve();
        adapter.update(recipes);
        adapter.notifyItemRangeChanged(0, recipes.size());

    }
}