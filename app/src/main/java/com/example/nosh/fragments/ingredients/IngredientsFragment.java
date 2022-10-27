package com.example.nosh.fragments.ingredients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.startup.AppInitializer;

import com.example.nosh.R;
import com.example.nosh.controller.IngrStorageController;
import com.example.nosh.database.DBControllerFactory;
import com.example.nosh.database.DBControllerFactoryInitializer;
import com.example.nosh.database.IngrStorageDBController;
import com.example.nosh.entity.ingredient.StoredIngredient;

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
    private StoredIngredientAdapter adapter;
    private IngrStorageController controller;
    private IngredientsFragmentListener listener;
    private ArrayList<StoredIngredient> storedIngredients;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    public static IngredientsFragment newInstance() {
        return new IngredientsFragment();
    }

    private class IngredientsFragmentListener implements StoredIngredientAdapter.RecyclerViewListener, View.OnClickListener {
        @Override
        public void onClick(View v) {

        }

        @Override
        public void onDeleteButtonClick(int pos) {
            if (pos >= 0) {
                controller.delete(storedIngredients.get(pos));
                storedIngredients.remove(pos);

                adapter.notifyItemRemoved(pos);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBControllerFactory factory =
                AppInitializer.getInstance(requireContext()).initializeComponent(DBControllerFactoryInitializer.class);

        controller =
                new IngrStorageController(factory.createAccessController(IngrStorageDBController.class.getSimpleName()), this);
        listener = new IngredientsFragmentListener();

        storedIngredients = controller.retrieve();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ingredients, container, false);


        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        adapter = new StoredIngredientAdapter(listener, getContext(), storedIngredients);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        ImageButton addButton = v.findViewById(R.id.add_btn);
        addButton.setOnClickListener(view -> openAddIngredientDialog());

        addIngredientFragmentListener();

        return v;
    }

    private void openAddIngredientDialog() {
        AddIngredientDialog addIngredientDialog = AddIngredientDialog.newInstance();
        addIngredientDialog.show(getParentFragmentManager(), "ADD_INGREDIENT");
    }

    public void addIngredientFragmentListener() {
        requireActivity().getSupportFragmentManager().setFragmentResultListener("add_ingredient",
                getViewLifecycleOwner(), (requestKey, result) -> {
            controller.add(
                    (Date) result.getSerializable("date"),
                    result.getInt("qty"),
                    result.getDouble("unit"),
                    result.getString("name"),
                    result.getString("description"),
                    result.getString("category"),
                    result.getString("location"));
        });
    }

    @Override
    public void update(Observable o, Object arg) {
        storedIngredients = controller.retrieve();

        adapter.update(storedIngredients);
        adapter.notifyItemRangeChanged(0, storedIngredients.size());
    }

}