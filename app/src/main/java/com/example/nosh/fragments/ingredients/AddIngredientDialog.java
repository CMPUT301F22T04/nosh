package com.example.nosh.fragments.ingredients;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.nosh.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class AddIngredientDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private EditText ingName;
    private EditText ingDescription;
    private Button ingExpirationDate;
    private Button ingStorageLocation;
    private EditText ingQuantity;
    private EditText ingUnit;
    private Button ingCategory;

    public static AddIngredientDialog newInstance() {
        return new AddIngredientDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.add_ingredient_layout, container,
                false);

        // DatePickerDialog used for the date
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog =
                new DatePickerDialog(requireContext(), this, year, month, day);

        ingName = view.findViewById(R.id.add_name);
        ingDescription = view.findViewById(R.id.add_description);
        ingExpirationDate = view.findViewById(R.id.add_date);
        ingStorageLocation = view.findViewById(R.id.add_storage_location);
        ingQuantity = view.findViewById(R.id.add_qty);
        ingUnit = view.findViewById(R.id.add_unit);
        ingCategory = view.findViewById(R.id.add_ingredient_category);

        Button submitButton = view.findViewById(R.id.submit_add);
        ImageButton cancelButton = view.findViewById(R.id.cancel_add);

        // listeners
        ingExpirationDate.setOnClickListener(v -> datePickerDialog.show());
        ingStorageLocation.setOnClickListener(v -> showLocationPicker());
        ingCategory.setOnClickListener(v -> showCategoryPicker());

        submitButton.setOnClickListener(v -> {
            /*
             * User is allowed to add an item with empty fields as long as a
             * description is provided
             */
            String name =
                    ((ingName.getText().toString().isEmpty()) ? "" :
                            ingName.getText().toString());
            if (name.trim().isEmpty()) {
                Toast.makeText(getActivity(), "New ingredients must have a " +
                        "name.", Toast.LENGTH_SHORT).show();
                return;
            }
            String description =
                    ((ingDescription.getText().toString().isEmpty()) ? "No description..." :
                            ingDescription.getText().toString());
            String date = ((ingExpirationDate.getText().toString().isEmpty()) ?
                    "yyyy-mm-dd" : ingExpirationDate.getText().toString());
            String storageLocation =
                    ((ingStorageLocation.getText().toString().isEmpty()) ?
                            "No storage location..." :
                            ingStorageLocation.getText().toString());
            String quantity = ((ingQuantity.getText().toString().isEmpty()) ? "0" :
                    ingQuantity.getText().toString());
            String unit = ((ingUnit.getText().toString().isEmpty()) ? "No unit..." :
                    ingUnit.getText().toString());
            String category = ((ingCategory.getText().toString().isEmpty()) ? "No category..." :
                    ingCategory.getText().toString());

            // TODO: call the controller and add it that way
            dismiss();
        });

        cancelButton.setOnClickListener(v -> dismiss());


        return view;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @SuppressLint("SetTextI18n")
    private void showLocationPicker() {
        // Storage Location picker dialog
        Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.storage_location_picker);

        EditText location = dialog.findViewById(R.id.storage_input);
        Button confirm = dialog.findViewById(R.id.confirm_location_button);

        dialog.show();

        confirm.setOnClickListener(v -> {
            ingStorageLocation.setText(location.getText().toString());
            dialog.dismiss();
        });
    }

    @SuppressLint("SetTextI18n")
    private void showCategoryPicker() {
        // Storage Location picker dialog
        Dialog dialog = new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.ingredient_category_picker);

        EditText category = dialog.findViewById(R.id.category_input);
        Button confirm = dialog.findViewById(R.id.confirm_category_button);

        dialog.show();

        confirm.setOnClickListener(v -> {
            ingCategory.setText(category.getText().toString());
            dialog.dismiss();
        });
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        // Format Date String to be in the yyyy-mm-dd format
        if (Integer.toString(month).length() == 1 && Integer.toString(day).length() == 1) {
            ingExpirationDate.setText(year + "-" + "0" + month + "-" + "0" + day);
            return;
        }
        if (Integer.toString(month).length() == 1) {
            ingExpirationDate.setText(year + "-" + "0" + month + "-" + day);
            return;
        }
        if (Integer.toString(day).length() == 1) {
            ingExpirationDate.setText(year + "-" + month + "-" + "0" + day);
            return;
        }
        ingExpirationDate.setText(year + "-" + month + "-" + day);
    }
}
