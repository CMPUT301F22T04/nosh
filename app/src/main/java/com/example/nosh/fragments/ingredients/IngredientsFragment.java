package com.example.nosh.fragments.ingredients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.startup.AppInitializer;

import com.example.nosh.R;
import com.example.nosh.controller.IngredientStorageController;
import com.example.nosh.database.DatabaseAccessFactory;
import com.example.nosh.database.DatabaseAccessFactoryInitializer;
import com.example.nosh.database.IngredientStorageAccess;
import com.example.nosh.entity.StoredIngredient;

import java.util.ArrayList;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IngredientsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IngredientsFragment extends Fragment {

    private StoredIngredientAdapter adapter;

    private IngredientStorageController controller;

    private IngredientsFragmentListener listener;

    private ArrayList<StoredIngredient> ingredients;

    private class IngredientsFragmentListener implements
            StoredIngredientAdapter.RecyclerViewListener, View.OnClickListener {

        @Override
        public void onClick(View v) {

        }

        @Override
        public void onDeleteButtonClick(int pos) {
            controller.remove(ingredients.get(pos));

            ingredients.remove(pos);

            adapter.notifyItemRemoved(pos);
        }
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public IngredientsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IngredientsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IngredientsFragment newInstance(String param1, String param2) {
        IngredientsFragment fragment = new IngredientsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseAccessFactory factory =
                AppInitializer.getInstance(requireContext()).
                        initializeComponent(DatabaseAccessFactoryInitializer.class);

        controller = new IngredientStorageController(factory
                .createAccessController(IngredientStorageAccess.class.getSimpleName()));
        listener = new IngredientsFragmentListener();

        controller.add(
                new Date(), 1, 8, "STARBUCKS Iced Coffee",
                "1.18 ~ 1.42 L", "Dairy", "Fridge");

        controller.add(new Date(), 1, 4, "COCA-COLA Mini Cans",
                "6x222 mL", "Drinks", "Fridge");

        controller.add(new Date(), 1, 5, "KRAFT Peanut Butter",
                "750g", "Pantry", "Pantry");

        ingredients = controller.get();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ingredients, container, false);

        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        adapter = new StoredIngredientAdapter(listener, getContext(), ingredients);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return v;
    }
}