package com.example.nosh.fragments.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.nosh.R;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;

public class AddIngredientToRecipeDialog extends DialogFragment {

    private AddIngredientToRecipeDialogListener listener;
    private ImageButton backButton;
    private EditText IngredientName;
    private EditText IngredientDescription;
    private EditText IngredientQuantity;
    private EditText IngredientUnit;
    private EditText IngredientCategory;
    private Button add;

    public static AddIngredientToRecipeDialog newInstance() {
        return new AddIngredientToRecipeDialog();
    }

    private class AddIngredientToRecipeDialogListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if (v.getId() == add.getId()) {
                if (validInput()) {
                    addIngredientAction();
                    dismiss();
                }
            }  else if (v.getId() == backButton.getId()) {
                dismiss();
            }
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.add_ingredient_to_recipe_layout,
                container, false);
        // fields
        backButton = view.findViewById(R.id.cancel_add_recipe_ingredient);
        IngredientName = view.findViewById(R.id.add_name_recipe_ingredient);
        IngredientDescription = view.findViewById(R.id.add_description_recipe_ingredient);
        IngredientQuantity = view.findViewById(R.id.add_qty_recipe_ingredient);
        IngredientUnit = view.findViewById(R.id.add_unit_recipe_ingredient);
        IngredientCategory =  view.findViewById(R.id.add_ingredient_recipe_category);
        add = view.findViewById(R.id.submit_add_recipe_ingredient);


        backButton.setOnClickListener(v -> dismiss());



        add.setOnClickListener(v -> {
            if (validInput()) {
                addIngredientAction();
            }
        });

        return view;
    }
    private boolean validInput() {
        boolean invalidInput = true;

        if (TextUtils.isEmpty(IngredientName.getText().toString())) {
            IngredientName.setError("ingredient name required");
            invalidInput = false;
        }
        if (TextUtils.isEmpty(IngredientQuantity.getText().toString())) {
            IngredientQuantity.setError("qty required");
            invalidInput = false;
        }
        if (TextUtils.isEmpty(IngredientUnit.getText().toString())) {
            IngredientUnit.setError("unit required");
            invalidInput = false;
        }
        if (TextUtils.isEmpty(IngredientCategory.getText().toString())) {
            IngredientCategory.setError("Category required");
            invalidInput = false;
        }

        return invalidInput;
    }
    private void addIngredientAction() {
        Bundle args = new Bundle();


        args.putString("name", IngredientName.getText().toString());
        args.putString("description", IngredientDescription.getText().toString());
        args.putInt("qty", Integer.parseInt(IngredientQuantity.getText().toString()));
        args.putString("unit", IngredientUnit.getText().toString());
        args.putString("category", IngredientQuantity.getText().toString());

        requireActivity().getSupportFragmentManager().setFragmentResult("add_ingredient", args);

        dismiss();
    }


    //    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        listener = new AddIngredientToRecipeDialogListener();
//
//
//    }
    @Override
    public void dismiss() {
        super.dismiss();
    }



}
