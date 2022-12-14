package com.example.nosh.fragments.recipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.TextView;
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

import com.bumptech.glide.Glide;
import com.example.nosh.MainActivity;
import com.example.nosh.Nosh;
import com.example.nosh.R;
import com.example.nosh.controller.RecipeController;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.entity.Recipe;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import javax.inject.Inject;


public class EditRecipeDialog extends DialogFragment implements Observer {

    private Button addRecipeBtn;
    private ImageButton addRecipeIngredientBtn;
    private ImageButton backButton;
    private ImageView recipeImageView;
    private EditText recipeName;
    private EditText prepInput;
    private EditText servingInput;
    private EditText categoryInput;
    private EditText commentInput;

    private TextView fragmentTitle;

    private EditRecipeDialogListener listener;
    private ActivityResultLauncher<Intent> launcher;

    private Uri recipeImageUri;
    private ArrayList<Ingredient> ingredients;
    private RecipeIngredientAdapter adapter;
   // private RecipeController controller;
    private HashMap<String, StorageReference> recipeImagesRemote;


    @Inject
    RecipeController controller;

    private class EditRecipeDialogListener implements View.OnClickListener,
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

    public static EditRecipeDialog newInstance(Recipe recipe,int pos) {

        EditRecipeDialog frag = new EditRecipeDialog();
        Bundle args  =  new Bundle();
        args.putSerializable("Recipe",recipe);
        args.putInt("Pos",pos);
        frag.setArguments(args);
        return frag;

    }
    @Override
    public void onAttach(@NonNull Context context) {
        ((Nosh) context.getApplicationContext())
                .getAppComponent()
                .inject(this);

        controller.addObserver(this);

        super.onAttach(context);

        listener = new EditRecipeDialogListener();

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listener = new EditRecipeDialogListener();

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

        assert getArguments() != null;
        Recipe recipe = (Recipe) getArguments().getSerializable("Recipe");
        View view = inflater.inflate(R.layout.add_recipe, container, false);

        addRecipeBtn = view.findViewById(R.id.submit_recipe);
        addRecipeBtn.setText("Confirm");

        fragmentTitle = view.findViewById(R.id.textView13);
        fragmentTitle.setText("Edit Recipe");
        addRecipeIngredientBtn = view.findViewById(R.id.add_recipe_ingredient);
        backButton = view.findViewById(R.id.add_recipe_back_btn);

        recipeImageView = view.findViewById(R.id.recipe_image_view);
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

        recipeName.setText(recipe.getTitle());
        prepInput.setText(Double.toString(recipe.getPreparationTime()));
        servingInput.setText(Long.toString(recipe.getServings()));
        categoryInput.setText(recipe.getCategory());
        commentInput.setText(recipe.getComments());


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

        for (int i = 0;i<recipe.getIngredients().size();i++){
            ingredients.add(recipe.getIngredients().get(i));
            adapter.update(ingredients);
            adapter.notifyItemRangeChanged(0, ingredients.size());
        }
        //        recipeImageView.setImageURI(recipe.getPhotographRemote()g);
        String loc = recipe.getPhotographRemote();
        //controller.getRecipeImageRemote(loc);
        controller.getRecipeImageRemote(loc);
        Glide.with(getContext()).load(controller.getRecipeImageRemote(loc)).into(recipeImageView);


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

        return invalidInput;
    }

    private void addRecipeAction(){
        Bundle args = new Bundle();
        Recipe rec = (Recipe) getArguments().getSerializable("Recipe");
        String hash = rec.getHashcode();

        args.putString("hashcode",hash);
        args.putString("name", recipeName.getText().toString());
        args.putDouble("prep", Double.parseDouble(prepInput.getText().toString()));
        args.putLong("servings", Long.parseLong(servingInput.getText().toString()));
        args.putString("category", categoryInput.getText().toString());
        args.putString("comments", commentInput.getText().toString());
        if (recipeImageUri != null){
            args.putParcelable("photoUri", recipeImageUri);
            args.putString("Code","updateImage");
        }
        else{
            args.putString("PhotoUrI",rec.getPhotographRemote());
            args.putString("Code","noUpdate");
        }

        args.putSerializable("ingredients",ingredients);

        requireActivity().getSupportFragmentManager().setFragmentResult("edit_recipe", args);

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
    @Override
    public void update(Observable o, Object arg) {

    }
}
