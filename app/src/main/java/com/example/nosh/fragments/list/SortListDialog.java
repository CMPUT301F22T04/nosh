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
 * Sorting ingredients class, communicates with IngredientsFragment ONLY to sort the date in ascending order
 */
public class SortListDialog extends DialogFragment {

    public static SortListDialog newInstance() {
        return new SortListDialog();
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

                if (typeBtnId == -1) {
                    // Handle exception here
                    // Not all the options are being selected
                } else {
                    // This content description determines which field is being sorted
                    String type = typeGroup.findViewById(typeBtnId)
                            .getContentDescription()
                            .toString();

                    Bundle bundle = new Bundle();

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

        View view = inflater.inflate(R.layout.fragment_shopping_list_sort, container,
                false);

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
        if(window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = Resources.getSystem().getDisplayMetrics().widthPixels;
        window.setAttributes(params);
    }
}