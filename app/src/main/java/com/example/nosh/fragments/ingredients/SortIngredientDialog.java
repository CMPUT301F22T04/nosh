package com.example.nosh.fragments.ingredients;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.nosh.R;

/**
 * Sorting ingredients class, communicates with IngredientsFragment ONLY to sort the date in ascending order
 */
public class SortIngredientDialog extends DialogFragment {

    public static SortIngredientDialog newInstance() {
        return new SortIngredientDialog();
    }

    private Button confirmSort;
    private RadioGroup orderGroup;
    private RadioGroup typeGroup;

    /**
     * A event listener class for sorting dialog
     */
    private class DialogListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == confirmSort.getId()) {

                // Get the radio button ids that are being selected
                int orderBtnId = orderGroup.getCheckedRadioButtonId();
                int typeBtnId = typeGroup.getCheckedRadioButtonId();

                if (orderBtnId == -1 || typeBtnId == -1) {
                    // Handle exception here
                    // Not all the options are being selected
                } else {

                    // This content description determines whether is ascending or descending
                    String order_name = orderGroup.findViewById(orderBtnId)
                            .getContentDescription()
                            .toString();

                    boolean order = order_name.equals("desc");

                    // This content description determines which field is being sorted
                    String type = typeGroup.findViewById(typeBtnId)
                            .getContentDescription()
                            .toString();

                    Bundle bundle = new Bundle();

                    bundle.putBoolean("order", order);
                    bundle.putString("type", type);

                    requireActivity().getSupportFragmentManager().setFragmentResult(
                           "sort_ingredient",  bundle
                    );

                    dismiss();
                }
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_ingredientssort, container,
                false);

        DialogListener listener = new DialogListener();

        confirmSort = view.findViewById(R.id.confirm_sort);
        orderGroup = view.findViewById(R.id.order_group);
        typeGroup = view.findViewById(R.id.type_group);

        confirmSort.setOnClickListener(listener);

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
