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
import com.example.nosh.controller.RecipeController;
import com.example.nosh.controller.RecipeSorting;
import com.example.nosh.controller.ShoppingListSorting;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Recipe;
import com.example.nosh.fragments.Shopping.ShoppingAdapter;
import com.example.nosh.fragments.Shopping.ShoppingFragment;
import com.example.nosh.fragments.recipes.SortRecipeDialog;
import com.example.nosh.repository.IngredientRepository;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment implements Observer {

    private Button sortButtonL;

    private ShoppingAdapter adapter;
    private IngredientRepository x;
    // IngredientsFragment depends on controller. Use Dagger to manager dependency injection
    
    @Inject
    //IngredientStorageController controller;
    RecipeController controller2;

    private ShoppingFragmentListener listener;
    private ArrayList<Ingredient> ingredients;
    private ArrayList<Recipe> recipes;
    private Button sortButton;
    private Object ShoppingFragment;

    public ListFragment() {
        // Required empty public constructor
    }
    
    private class ShoppingFragmentListener
            implements ShoppingAdapter.RecyclerViewListener,
            View.OnClickListener, FragmentResultListener {

        @Override
        public void onClick(View v) {
            adapter.update(ingredients);

            if (v.getId() == sortButtonL.getId()) {
                openSortListDialog();
            }

        }

        private void openSortListDialog() {
            SortingListDialog sortingListDialog = new SortingListDialog();
            sortingListDialog.show(getChildFragmentManager(),"SORT_LIST");
        }

        @Override
        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
            if(requestKey.equals("sort_list")){
                ShoppingListSorting.sort(
                        ingredients,
                        result.getString("type")
                );
                adapter.update(ingredients);
                adapter.notifyItemRangeChanged(0, ingredients.size());
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {

        // Inject an instance of IngredientStorageController into IngredientFragment
        ;
        ((Nosh) context.getApplicationContext())
                .getAppComponent()
                .inject(this);

        controller2.addObserver(this);
        
        super.onAttach(context);
        
        listener = new ShoppingFragmentListener();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ingredients = controller.retrieve();
        ingredients = new ArrayList<Ingredient>();
        recipes = controller2.retrieve();
        getIngredient();
       // ingredients = recipes.get(0).getIngredients();
        //System.out.println(ingredients.get(1).getDescription());
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);

        sortButtonL = v.findViewById(R.id.sort_list_button);
        sortButtonL.setOnClickListener(listener);

        RecyclerView recyclerView = v.findViewById(R.id.list_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        
        adapter = new ShoppingAdapter(listener, getContext(), ingredients);
        
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        "sort_list",
                        getViewLifecycleOwner(),
                        listener
                );

        return v;
    }


    /**
     * Receive notification from Ingredient Repository that there are new changes in
     * data / entity objects. Retrieve the latest copy of the data
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {
        getIngredient();
        //recipes = controller2.retrieve();
        adapter.update(ingredients);
        adapter.notifyItemRangeChanged(0, ingredients.size());
    }

    @Override
    public void onDestroy() {
        //controller.deleteObserver(this);
        controller2.deleteObserver(this);

        super.onDestroy();
    }


    public void getIngredient(){
        for (Recipe r : recipes){
            ArrayList<Ingredient> ing;
            ing = r.getIngredients();
            for (Ingredient i : ing){
                ingredients.add(i);
            }
        }
    }
}
