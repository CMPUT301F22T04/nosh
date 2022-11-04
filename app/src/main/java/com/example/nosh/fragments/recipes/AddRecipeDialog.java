package com.example.nosh.fragments.recipes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.nosh.R;


public class AddRecipeDialog extends DialogFragment {
    private ImageButton backButton;
    private EditText recipeName;
    private ImageView photo;
    private EditText prepInput;
    private EditText servingInput;
    private EditText categoryInput;
    private EditText commentInput;
    private Button add;
    private Button addIngredient;
    private String imageUri;

    private AddRecipeDialogListener listener;
    private ActivityResultLauncher<Intent> launcher;

    private class AddRecipeDialogListener implements View.OnClickListener,
            ActivityResultCallback<ActivityResult> {

        @Override
        public void onClick(View v) {
            if (v.getId() == add.getId()) {
                if (validInput()) {
                    addRecipeAction();
                }
            } else if (v.getId() == photo.getId()) {
                Intent photoPicker = new Intent();

                photoPicker.setType("image/*");
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);

                launcher.launch(photoPicker);
            } else if (v.getId() == backButton.getId()) {
                dismiss();
            }
        }

        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();

                if (data != null && data.getData() != null) {
                    Uri uri = data.getData();

                    imageUri = uri.getPath();
                    photo.setImageURI(uri);
                }
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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.add_recipe, container, false);
        // fields
        backButton = view.findViewById(R.id.back_button);

        photo = view.findViewById(R.id.Image);

        recipeName = view.findViewById(R.id.add_name);
        prepInput = view.findViewById(R.id.prep_input);
        servingInput = view.findViewById(R.id.serving_input);
        categoryInput = view.findViewById(R.id.category_input);
        commentInput = view.findViewById(R.id.add_description);
        add = view.findViewById(R.id.save);
        addIngredient = view.findViewById(R.id.add_ingredient);

        // listeners
        //Back out of program
        backButton.setOnClickListener(listener);
        add.setOnClickListener(listener);
        photo.setOnClickListener(listener);

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
        if(photo.getDrawable()==null){
            Context context = getActivity().getApplicationContext();
            CharSequence text = "Image cannot be empty!";
            int duration = Toast.LENGTH_SHORT;

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
        args.putString("photo", imageUri);
        requireActivity().getSupportFragmentManager().setFragmentResult("add_recipe", args);

        dismiss();

    }

}
