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

    private ShoppingFragmentListener listener;
    private ArrayList<Ingredient> ingredients;

    private Button sortButton;
    private Object ShoppingFragment;

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

    @Override
    public void onAttach(@NonNull Context context) {

        // Inject an instance of IngredientStorageController into IngredientFragment
        ;
        ((Nosh) context.getApplicationContext())
                .getAppComponent()
                .inject(this);

        controller.addObserver(this);
        
        super.onAttach(context);
        
        listener = new ShoppingFragmentListener();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ingredients = controller.retrieve();

        System.out.println(ingredients.get(1).getDescription());
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_list, container, false);
        
        RecyclerView recyclerView = v.findViewById(R.id.list_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        
        adapter = new ShoppingAdapter(listener, getContext(), ingredients);
        
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

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
        ingredients = controller.retrieve();

        adapter.update(ingredients);
        adapter.notifyItemRangeChanged(0, ingredients.size());
    }

    @Override
    public void onDestroy() {
        controller.deleteObserver(this);

        super.onDestroy();
    }
}
