package com.example.nosh.fragments.list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nosh.R;
import com.example.nosh.controller.IngredientStorageController;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.fragments.Shopping.ShoppingAdapter;
import com.example.nosh.fragments.Shopping.ShoppingFragment;
import com.example.nosh.repository.IngredientRepository;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    private Button addButton;
    private ShoppingAdapter adapter;
    private IngredientRepository x;
    // IngredientsFragment depends on controller. Use Dagger to manager dependency injection
    @Inject
    IngredientStorageController controller;


    private com.example.nosh.fragments.Shopping.ShoppingFragment.ShoppingFragmentListener listener;
    private ArrayList<Ingredient> ingredients;

    private Button sortButton;
    private Object ShoppingFragment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }
    private class ShoppingFragmentListener
            implements ShoppingAdapter.RecyclerViewListener,
            View.OnClickListener, FragmentResultListener {

        @Override
        public void onClick(View view) {
            adapter.update(ingredients);

        }

        @Override
        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        System.out.println(ingredients.get(1).getDescription());
        RecyclerView recyclerView = v.findViewById(R.id.list_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());



        recyclerView.setLayoutManager(layoutManager);
        adapter = new ShoppingAdapter(listener, getContext(), ingredients);
        //adapter.update(ingredients);
        recyclerView.setAdapter(adapter);



        return v;
    }
}