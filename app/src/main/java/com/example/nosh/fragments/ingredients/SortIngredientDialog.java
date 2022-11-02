package com.example.nosh.fragments.ingredients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.nosh.R;

/**
 * Sorting ingredients class, communicates with IngredientsFragment ONLY to sort the date in ascending order
 */
public class SortIngredientDialog extends DialogFragment {
    public static SortIngredientDialog newInstance() {
        return new SortIngredientDialog();
    }
    private Button desceiptionsort;
    private Button datesort;
    private Button locationsort;
    private Button categorysort;

    private RadioButton order;
    private RadioGroup radiogroupp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ingredientssort, container, false);

        desceiptionsort = view.findViewById(R.id.sort_description);
        datesort = view.findViewById(R.id.sort_bestbefore);
        locationsort = view.findViewById(R.id.sort_location);
        categorysort = view.findViewById(R.id.sort_category);

        Bundle args = new Bundle();

        /**
         * Radio group used to check if ascending or descending
         */
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.RGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if(checkedId == R.id.Ascending) {
                    desceiptionsort.setOnClickListener(v -> {
                        requireActivity().getSupportFragmentManager().setFragmentResult("sort_description",args);
                        dismiss();
                    });
                    datesort.setOnClickListener(v -> {
                        requireActivity().getSupportFragmentManager().setFragmentResult("sort_date",args);
                        dismiss();
                    });
                    locationsort.setOnClickListener(v -> {
                        requireActivity().getSupportFragmentManager().setFragmentResult("sort_location",args);
                        dismiss();
                    });
                    categorysort.setOnClickListener(v -> {
                        requireActivity().getSupportFragmentManager().setFragmentResult("sort_category",args);
                        dismiss();
                    });

                } else if(checkedId == R.id.Descending) {

                    desceiptionsort.setOnClickListener(v -> {
                        requireActivity().getSupportFragmentManager().setFragmentResult("sort_descriptionD",args);
                        dismiss();
                    });
                    datesort.setOnClickListener(v -> {
                        requireActivity().getSupportFragmentManager().setFragmentResult("sort_dateD",args);
                        dismiss();
                    });
                    locationsort.setOnClickListener(v -> {
                        requireActivity().getSupportFragmentManager().setFragmentResult("sort_locationD",args);
                        dismiss();
                    });
                    categorysort.setOnClickListener(v -> {
                        requireActivity().getSupportFragmentManager().setFragmentResult("sort_categoryD",args);
                        dismiss();
                    });
                }
            }

        });







        return view;

    }

    /**
     * Will close the fragment dialog
     */
    @Override
    public void dismiss() {
        super.dismiss();
    }



}
