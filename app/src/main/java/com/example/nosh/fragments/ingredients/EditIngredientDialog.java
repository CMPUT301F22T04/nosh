package com.example.nosh.fragments.ingredients;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.nosh.R;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.utils.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * This class takes input from the user and returns it to the parent fragment for the editing of an existing ingredient,
 * The viewing and editing functionality are both handled here,
 * ingName {@link String}, ingDescription {@link String}, ingExpirationDate {@link Date}, ingStorageLocation {@link String},
 * ingQuantity {@link String}, ingUnit {@link String}, ingCategory {@link String}
 * @author JulianCamiloGallego
 * @version 1.0
 */
public class EditIngredientDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private EditText ingDescription;
    private Button ingExpirationDate;
    private EditText ingStorageLocation;
    private EditText ingQuantity;
    private EditText ingUnit;
    private EditText ingCategory;

    /**
     * This is a newInstance method to create a EditIngredientDialog instance.
     * @param ingredient stores the ingredient the user wished to edit/view {@link Ingredient}
     * @return EditIngredientDialog
     */
    public static EditIngredientDialog newInstance(Ingredient ingredient) {
        EditIngredientDialog frag = new EditIngredientDialog();
        Bundle args = new Bundle();
        args.putSerializable("ingredient", ingredient);
        frag.setArguments(args);

        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.edit_ingredient_layout, container, false);

        // get ingredient data and populate UI
        assert getArguments() != null;
        Ingredient ingredient = (Ingredient) getArguments().getSerializable("ingredient");
        populateFields(view, ingredient);

        // DatePickerDialog initialization
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), this, year,
                month, day);

        // views
        ingDescription = view.findViewById(R.id.edit_description);
        ingExpirationDate = view.findViewById(R.id.edit_date);
        ingStorageLocation = view.findViewById(R.id.edit_storage_location);
        ingQuantity = view.findViewById(R.id.edit_qty);
        ingUnit = view.findViewById(R.id.edit_unit);
        ingCategory = view.findViewById(R.id.edit_ingredient_category);
        Button submitButton = view.findViewById(R.id.submit_edit);
        ImageButton cancelButton = view.findViewById(R.id.cancel_edit);

        // listeners
        ingExpirationDate.setOnClickListener(v -> datePickerDialog.show());

        cancelButton.setOnClickListener(v -> dismiss());

        submitButton.setOnClickListener(v -> {
            editIngredientAction(ingredient);
        });

        return view;
    }

    /**
     * This method populates all the fields in the UI with the ingredient data
     */
    private void populateFields(View view, Ingredient ingredient){
        TextView name = view.findViewById(R.id.edit_name);
        ingDescription = view.findViewById(R.id.edit_description);
        ingExpirationDate = view.findViewById(R.id.edit_date);
        ingStorageLocation = view.findViewById(R.id.edit_storage_location);
        ingQuantity = view.findViewById(R.id.edit_qty);
        ingUnit = view.findViewById(R.id.edit_unit);
        ingCategory = view.findViewById(R.id.edit_ingredient_category);

        name.setText(ingredient.getName());
        ingDescription.setHint(ingredient.getDescription());
        ingExpirationDate.setText(DateUtil.formatDate(ingredient.getBestBeforeDate()));
        ingStorageLocation.setText(ingredient.getLocation());
        ingQuantity.setHint(Integer.toString(ingredient.getAmount()));
        ingUnit.setHint(Double.toString(ingredient.getUnit()));
        ingCategory.setHint(ingredient.getCategory());

    }

    /**
     * This methods stores all user input into a bundle and passes it back to the parent,
     * if the user does not enter a new value the old value will be taken instead.
     */
    private void editIngredientAction(Ingredient ingredient) {
        Bundle args = new Bundle();

        String description =
                ((ingDescription.getText().toString().isEmpty()) ?
                        ingredient.getDescription() :
                        ingDescription.getText().toString());

        Date date = ((ingExpirationDate.getText().toString().isEmpty()) ?
                DateUtil.getCalendar(ingredient.getBestBeforeDate().toString()).getTime() :
                DateUtil.getCalendar(ingExpirationDate.getText().toString()).getTime());

        String storageLocation =
                ((ingStorageLocation.getText().toString().isEmpty()) ?
                        ingredient.getLocation() :
                        ingStorageLocation.getText().toString());

        int qty = ((ingQuantity.getText().toString().isEmpty()) ?
                ingredient.getAmount() :
                Integer.parseInt(ingQuantity.getText().toString()));

        double unit = ((ingUnit.getText().toString().isEmpty()) ?
                ingredient.getUnit() :
                Double.parseDouble(ingQuantity.getText().toString()));

        String category = ((ingCategory.getText().toString().isEmpty()) ?
               ingredient.getCategory() :
                ingCategory.getText().toString());

        args.putString("hashcode", ingredient.getHashcode());
        args.putString("name", ingredient.getName());
        args.putString("description", description);
        args.putSerializable("date", date);
        args.putString("location", storageLocation);
        args.putInt("qty", qty);
        args.putDouble("unit", unit);
        args.putString("category", category);

        requireActivity().getSupportFragmentManager().setFragmentResult("edit_ingredient", args);

        dismiss();
    }

    /**
     * This methods formats Date String to be in the yyyy-mm-dd format.
     */
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

    /**
     * This method will dismiss the fragment dialog.
     */
    @Override
    public void dismiss() {
        super.dismiss();
    }
}