package com.example.nosh.fragments.list;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.nosh.R;

/**
 * This class is responsible for communicating with the shopping list fragment and send the selected
 * sorting criteria
 * @author Julian Gallego Franco
 * @version 1.0
 */
public class SortingListDialog extends DialogFragment {

    public static SortingListDialog newInstance() {
        return new SortingListDialog();
    }

    private Button confirmSort;
    private RadioGroup typeGroup;

    /**
     * A event listener class for sorting dialog
     */
    private class DialogListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId() == confirmSort.getId()) {
                // Get the radio button ids that are being selected
                int typeBtnId = typeGroup.getCheckedRadioButtonId();
                // This content description determines which field is being sorted
                String type = typeGroup.findViewById(typeBtnId).getContentDescription().toString();

                Bundle bundle = new Bundle();
                bundle.putString("type", type);

                requireActivity().getSupportFragmentManager().setFragmentResult("sort_list",
                        bundle);

                dismiss();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_shopping_list_sort, container, false);

        DialogListener listener = new DialogListener();

        confirmSort = view.findViewById(R.id.confirm_sortL);
        typeGroup = view.findViewById(R.id.type_groupL);

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

    @Override
    public void onResume() {
        super.onResume();
        Window window = getDialog().getWindow();
        if (window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = Resources.getSystem().getDisplayMetrics().widthPixels;
        window.setAttributes(params);
    }
}