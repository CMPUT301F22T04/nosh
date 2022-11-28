package com.example.nosh.fragments.ingredients;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.nosh.R;
import com.example.nosh.utils.DateUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * This class takes input from the user and returns it to the parent fragment to create a new ingredient,
 * ingName {@link String}, ingDescription {@link String}, ingExpirationDate {@link Date}, ingStorageLocation {@link String},
 * ingQuantity {@link String}, ingUnit {@link String}, ingCategory {@link String}
 * @author JulianCamiloGallego
 * @version 1.0
 */
public class AddIngredientDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private EditText ingName;
    private EditText ingDescription;
    private Button ingExpirationDate;
    private EditText ingStorageLocation;
    private EditText ingQuantity;
    private EditText ingUnit;
    private EditText ingCategory;

    /**
     * This is a newInstance method to create a AddIngredientDialog instance.
     * optionally parameters can be set to pass in data to the fragment from the parent fragment
     * @return AddIngredientDialog
     */
    public static AddIngredientDialog newInstance() {
        return new AddIngredientDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.add_ingredient_layout, container, false);

        // DatePickerDialog initialization
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), this, year,
                month, day);

        // views
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

        cancelButton.setOnClickListener(v -> dismiss());

        submitButton.setOnClickListener(v -> {
            if (validInput()) {
                addIngredientAction();
            }
        });

        return view;
    }

    /**
     * This methods checks if the user filled in all the required fields
     * @return boolean
     */
    private boolean validInput() {
        boolean invalidInput = true;

        if (TextUtils.isEmpty(ingName.getText().toString())) {
            ingName.setError("ingredient name required");
            invalidInput = false;
        }
        if (TextUtils.isEmpty(ingQuantity.getText().toString())) {
            ingQuantity.setError("qty required");
            invalidInput = false;
        }
        if (TextUtils.isEmpty(ingUnit.getText().toString())) {
            ingUnit.setError("unit required");
            invalidInput = false;
        }

        return invalidInput;
    }

    /**
     * This methods stores all user input into a bundle and passes it back to the parent.
     */
    private void addIngredientAction() {
        Bundle args = new Bundle();

        String d = ingExpirationDate.getText().toString();

        Date date;

        try {
            date = DateUtil.getCalendar(d).getTime();
        } catch (ParseException e) {
            date = new Date();
        }

        args.putString("name", ingName.getText().toString());
        args.putString("description", ingDescription.getText().toString());
        args.putSerializable("date", date);
        args.putString("location", ingStorageLocation.getText().toString());
        args.putInt("qty", Integer.parseInt(ingQuantity.getText().toString()));
        args.putString("unit", ingUnit.getText().toString());
        args.putString("category", ingCategory.getText().toString());

        requireActivity()
                .getSupportFragmentManager()
                .setFragmentResult("add_ingredient", args);

        dismiss();
    }

    /**
     * This methods formats Date String to be in the yyyy-mm-dd format.
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        int displayMonth = month + 1;

        if (Integer.toString(month).length() == 1 && Integer.toString(day).length() == 1) {
            ingExpirationDate.setText(year + "-" + "0" + displayMonth + "-" + "0" + day);
            return;
        }
        if (Integer.toString(month).length() == 1) {
            ingExpirationDate.setText(year + "-" + "0" + displayMonth + "-" + day);
            return;
        }
        if (Integer.toString(day).length() == 1) {
            ingExpirationDate.setText(year + "-" + displayMonth + "-" + "0" + day);
            return;
        }
        ingExpirationDate.setText(year + "-" + displayMonth + "-" + day);
    }

    /**
     * This method will dismiss the fragment dialog.
     */
    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        if(window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = Resources.getSystem().getDisplayMetrics().widthPixels;
        window.setAttributes(params);
    }
}