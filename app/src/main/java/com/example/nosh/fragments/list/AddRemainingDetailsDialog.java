package com.example.nosh.fragments.list;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.os.Bundle;
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
public class AddRemainingDetailsDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private Button ingExpirationDate;
    private EditText ingStorageLocation;

    /**
     * This is a newInstance method to create a AddIngredientDialog instance.
     * optionally parameters can be set to pass in data to the fragment from the parent fragment
     * @return AddIngredientDialog
     */
    public static AddRemainingDetailsDialog newInstance() {
        return new AddRemainingDetailsDialog();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.add_remaining_details_layout, container, false);

        // DatePickerDialog initialization
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), this, year,
                month, day);

        // views
        ingExpirationDate = view.findViewById(R.id.add_missing_date);
        ingStorageLocation = view.findViewById(R.id.add_missing_storage_location);
        Button submitButton = view.findViewById(R.id.submit_details);
        //ImageButton cancelButton = view.findViewById(R.id.cancel_add_details);

        // listeners
        ingExpirationDate.setOnClickListener(v -> datePickerDialog.show());

        //cancelButton.setOnClickListener(v -> dismiss());

        submitButton.setOnClickListener(v -> updateIngredientAction());

        return view;
    }

    /**
     * This methods stores all user input into a bundle and passes it back to the parent.
     */
    private void updateIngredientAction() {
        Bundle args = new Bundle();

        String d = ingExpirationDate.getText().toString();

        Date date;
        try {
            date = DateUtil.getCalendar(d).getTime();
        } catch (ParseException e) {
            date = new Date();
        }

        args.putSerializable("date", date);
        args.putString("location", ingStorageLocation.getText().toString());

        requireActivity().getSupportFragmentManager().setFragmentResult("add_details", args);

        dismiss();
    }

    /**
     * This methods formats Date String to be in the yyyy-mm-dd format.
     */
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
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