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

    private ImageButton add_btn;

    private StoredIngredientAdapter adapter;

    private IngrStorageController controller;

    private IngredientsFragmentListener listener;

    private ArrayList<StoredIngredient> storedIngredients;

    private class IngredientsFragmentListener implements
            StoredIngredientAdapter.RecyclerViewListener, View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == add_btn.getId()) {
                // dummy instance
                controller.add(new Date(), 1, 2.25, "name",
                        "description", "category", "location");
            }
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

        DBControllerFactory factory =
                AppInitializer.getInstance(requireContext()).
                        initializeComponent(DBControllerFactoryInitializer.class);

        controller = new IngrStorageController(factory
                .createAccessController(IngrStorageDBController.class.getSimpleName()), this);
        listener = new IngredientsFragmentListener();

        storedIngredients = controller.retrieve();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_ingredients, container, false);

        add_btn = v.findViewById(R.id.add_btn);

        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        adapter = new StoredIngredientAdapter(listener, getContext(), storedIngredients);

        add_btn.setOnClickListener(listener);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void update(Observable o, Object arg) {
        storedIngredients = controller.retrieve();

        adapter.update(storedIngredients);
        adapter.notifyItemRangeChanged(0, storedIngredients.size());
    }

}