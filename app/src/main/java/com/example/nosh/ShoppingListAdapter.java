package com.example.nosh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nosh.entity.Ingredient;

import java.util.ArrayList;

public class ShoppingListAdapter extends ArrayAdapter<Ingredient> {
    /*Declaration of variables*/
    private ArrayList<Ingredient> ingredients;

    private Context context;

    /**
     * Constructor of the IngredientAdapter
     * @param context
     * @param ingredients
     */
    public ShoppingListAdapter(@NonNull Context context, @NonNull ArrayList<Ingredient> ingredients) {
        super(context,0, ingredients);
        this.context = context;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.shopping_list_content, parent,false);
        }

        Ingredient ingredient = ingredients.get(position);
        TextView desText, CategoryText, AmountText, UnitText;

        desText = view.findViewById(R.id.shoppingIngredientDesp);
        CategoryText = view.findViewById(R.id.shoppingIngredientCategory);
        AmountText = view.findViewById(R.id.shoppingIngredientAmount);
        UnitText = view.findViewById(R.id.shoppingIngredientUnit);

        desText.setText("Description: " + ingredient.getDescription());
        CategoryText.setText("Category: " +ingredient.getCategory());
        AmountText.setText("Amount Needed: " + ingredient.getAmount());
        UnitText.setText("Unit: " + ingredient.getUnit());

        return view;
    }
}
