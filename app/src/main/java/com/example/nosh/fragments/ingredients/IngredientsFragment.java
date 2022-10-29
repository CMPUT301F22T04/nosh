package com.example.nosh.fragments.ingredients;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.startup.AppInitializer;

import com.example.nosh.R;
import com.example.nosh.controller.IngredientStorageController;
import com.example.nosh.database.DBControllerFactory;
import com.example.nosh.database.DBControllerFactoryInitializer;
import com.example.nosh.database.IngrStorageDBController;
import com.example.nosh.entity.Ingredient;
import com.google.gson.Gson;

import java.util.ArrayList;
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
    private ActivityResultLauncher<Intent> launcher;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    public static IngredientsFragment newInstance() {
        return new IngredientsFragment();
    }

    private class IngredientsFragmentListener
            implements StoredIngredientAdapter.RecyclerViewListener,
            View.OnClickListener, FragmentResultListener,
            ActivityResultCallback<ActivityResult> {

        @Override
        public void onClick(View v) {
            if (v.getId() == addButton.getId()) {
                openAddIngredientDialog();
            }
        }

        @Override
        public void onDeleteButtonClick(int pos) {
            if (pos >= 0) {
                controller.delete(ingredients.get(pos));
                ingredients.remove(pos);

                adapter.notifyItemRemoved(pos);
            }
        }

        @Override
        public void onEditClick(int pos) {
            Ingredient ingredient = ingredients.get(pos);

            // Instead of pass gson, pass every attribute of ingredient
            Gson gson = new Gson();
            String digest = gson.toJson(ingredient);

            Intent intent = new Intent(getContext(), ViewIngredient.class);
            intent.putExtra("ingredient", digest);

            launcher.launch(intent);
        }

        private void openAddIngredientDialog() {
            AddIngredientDialog addIngredientDialog = AddIngredientDialog.newInstance();
            addIngredientDialog.show(getParentFragmentManager(), "ADD_INGREDIENT");
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
        }

        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == 0) {
                Intent intent = result.getData();

                if (intent != null) {
                    System.out.println(intent.getStringExtra("name"));
                    System.out.println();

                    controller.update()
                }
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBControllerFactory factory =
                AppInitializer.getInstance(requireContext()).initializeComponent(DBControllerFactoryInitializer.class);

        controller =
                new IngredientStorageController(factory.createAccessController(IngrStorageDBController.class.getSimpleName()), this);
        listener = new IngredientsFragmentListener();

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), listener);

        ingredients = controller.retrieve();
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

        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        "add_ingredient",
                        getViewLifecycleOwner(),
                        listener);

        return v;
    }

    @Override
    public void update(Observable o, Object arg) {
        ingredients = controller.retrieve();

        adapter.update(ingredients);
        adapter.notifyItemRangeChanged(0, ingredients.size());
    }

}