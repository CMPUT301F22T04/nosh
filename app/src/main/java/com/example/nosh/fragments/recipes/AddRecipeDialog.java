package com.example.nosh.fragments.recipes;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;

import java.util.Calendar;


public class AddRecipeDialog extends DialogFragment {
    private ImageButton backButton;
    private EditText recipeName;
    private ImageView photo;
    private EditText prepInput;
    private EditText servingInput;
    private EditText categoryInput;
    private EditText commentInput;
    private Button add;
    private Button add_ingredient ;
    private String image_uri;


    public static AddRecipeDialog newInstance() {
        return new AddRecipeDialog();
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
        add_ingredient = view.findViewById(R.id.add_ingredient);
        // listeners
        //Back out of program
        backButton.setOnClickListener(v -> dismiss());

        add.setOnClickListener(v -> {
            if (validInput()) {
                addRecipeAction();
            }
        });
        photo.setOnClickListener(v -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, 100);

            });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 100) {
          Uri uri = data.getData();
          image_uri = uri.toString();
          photo.setImageURI(uri);

        }
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
        args.putString("photo",image_uri);
        requireActivity().getSupportFragmentManager().setFragmentResult("add_recipe", args);

        dismiss();

    }

}
