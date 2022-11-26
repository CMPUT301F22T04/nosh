package com.example.nosh.fragments.recipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.MainActivity;
import com.example.nosh.R;
import com.example.nosh.controller.RecipeController;
import com.example.nosh.entity.Ingredient;

import java.util.ArrayList;
import java.util.Objects;


public class AddRecipeDialog extends DialogFragment {

    private Button addRecipeBtn;
    private ImageButton addRecipeIngredientBtn;
    private ImageButton backButton;
    private ImageView recipeImageView;
    private EditText recipeName;
    private EditText prepInput;
    private EditText servingInput;
    private EditText categoryInput;
    private EditText commentInput;

    private AddRecipeDialogListener listener;
    private AddRecipeDialogListener listnerIng;
    private ActivityResultLauncher<Intent> launcher;

    private Uri recipeImageUri;
    private Drawable originalImage;
    private ArrayList<Ingredient> ingredients;
    private RecipeIngredientAdapter adapter;
    private RecipeController controller;



    private class AddRecipeDialogListener implements View.OnClickListener,
            ActivityResultCallback<ActivityResult>,RecipeIngredientAdapter.RecyclerViewListener,
            FragmentResultListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == addRecipeBtn.getId()) {
                if (validInput()) {
                    addRecipeAction();
                }
            } else if (v.getId() == recipeImageView.getId()) {
                Intent photoPicker = new Intent();

                photoPicker.setType("image/*");
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);

                MainActivity.verifyStorageReadPermission(getActivity());

                launcher.launch(photoPicker);
            } else if (v.getId() == addRecipeIngredientBtn.getId()) {
                AddIngredientToRecipeDialog addIngredientToRecipeDialog =
                        AddIngredientToRecipeDialog.newInstance();

                addIngredientToRecipeDialog.show(getParentFragmentManager(),
                        "add_ingredient_to_recipe");

            } else if (v.getId() == backButton.getId()) {
                dismiss();
            }
        }

        @Override
        public void onDeleteButtonClick(int pos) {
            if (pos >= 0) {
                ingredients.remove(pos);
                adapter.notifyItemRemoved(pos);
            }
        }

        @Override
        public void onEditClick(int pos) {
        }

        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();

                if (data != null && data.getData() != null) {
                    Bundle bundle = new Bundle();

                    recipeImageUri = data.getData();
                    recipeImageView.setImageURI(recipeImageUri);
                }
            }
        }
        @Override
        public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
            if (requestKey.equals("add_ingredient")) {
                Ingredient ingr = new Ingredient(result.getString("unit"),
                        result.getInt("qty"),
                        result.getString("category"),
                        result.getString("description"),
                        result.getString("name")
                );
                ingredients.add(ingr);
                adapter.update(ingredients);
                adapter.notifyItemRangeChanged(0, ingredients.size());
            }
        }
    }

    public static AddRecipeDialog newInstance() {
        return new AddRecipeDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listener = new AddRecipeDialogListener();

        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                listener
        );
        ingredients = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.add_recipe, container, false);
        // fields


        addRecipeBtn = view.findViewById(R.id.submit_recipe);
        addRecipeIngredientBtn = view.findViewById(R.id.add_recipe_ingredient);
        backButton = view.findViewById(R.id.add_recipe_back_btn);

        recipeImageView = view.findViewById(R.id.recipe_image_view);
        originalImage = recipeImageView.getDrawable();

        recipeName = view.findViewById(R.id.recipe_name_field);
        prepInput = view.findViewById(R.id.preparation_time_field);
        servingInput = view.findViewById(R.id.serving_field);
        categoryInput = view.findViewById(R.id.recipe_category_field);
        commentInput = view.findViewById(R.id.recipe_comment_field);

        // listeners
        //Back out of program
        addRecipeBtn.setOnClickListener(listener);
        addRecipeIngredientBtn.setOnClickListener(listener);
        backButton.setOnClickListener(listener);
        recipeImageView.setOnClickListener(listener);

        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResultListener(
                        "add_ingredient",
                        getViewLifecycleOwner(),
                        listener);

        RecyclerView recyclerView = view.findViewById(R.id.recipe_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());

        adapter = new RecipeIngredientAdapter(listener, getContext(), ingredients);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);


        return view;
    }

    private boolean validInput() {
        boolean invalidInput = true;

        if (TextUtils.isEmpty(recipeName.getText().toString())) {
            recipeName.setError("Cannot be empty");
            invalidInput = false;
        }
        if (TextUtils.isEmpty(prepInput.getText().toString())) {
            prepInput.setError("Cannot be empty");
            invalidInput = false;
        }
        if (TextUtils.isEmpty(servingInput.getText().toString())) {
            servingInput.setError("Cannot be empty");
            invalidInput = false;
        }
        if (TextUtils.isEmpty(categoryInput.getText().toString())) {
            categoryInput.setError("Cannot be empty");
            invalidInput = false;
        }
        if (TextUtils.isEmpty(commentInput.getText().toString())) {
            commentInput.setError("Cannot be empty");
            invalidInput = false;
        }
        if (recipeImageView.getDrawable()==originalImage){
            Context context = getActivity().getApplicationContext();
            CharSequence text = "Please Select an Image!";
            int duration = Toast.LENGTH_SHORT;
            invalidInput = false;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

        return invalidInput;
    }

    private void addRecipeAction(){
        Bundle args = new Bundle();
        args.putString("name", recipeName.getText().toString());
        args.putDouble("prep", Double.parseDouble(prepInput.getText().toString()));
        args.putInt("servings", Integer.parseInt(servingInput.getText().toString()));
        args.putString("category", categoryInput.getText().toString());
        args.putString("comments", commentInput.getText().toString());
        args.putParcelable("photoUri", recipeImageUri);
        args.putSerializable("ingredients",ingredients);

        requireActivity().getSupportFragmentManager().setFragmentResult("add_recipe", args);

        dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = Objects.requireNonNull(getDialog()).getWindow();
        if(window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = Resources.getSystem().getDisplayMetrics().widthPixels;
        window.setAttributes(params);
    }
}
