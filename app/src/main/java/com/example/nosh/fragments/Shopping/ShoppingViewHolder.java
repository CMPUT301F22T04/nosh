package com.example.nosh.fragments.Shopping;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;


class ShoppingViewHolder extends RecyclerView.ViewHolder {

    private final TextView nameTxtView;
    private final TextView descriptionTxtView;
    private final TextView categoryTxtView;
    private final TextView unitTxtView;
    private final TextView amountTxtView;
    private final CheckBox pickUp;

    private final class ViewHolderListener implements View.OnClickListener {

        private final ShoppingAdapter.RecyclerViewListener listener;

        ViewHolderListener(ShoppingAdapter.RecyclerViewListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == pickUp.getId()) {
                listener.onCheckBoxClick(getAdapterPosition());
            }
        }
    }

    ShoppingViewHolder(ShoppingAdapter.RecyclerViewListener listener, @NonNull View itemView) {
        super(itemView);

        nameTxtView = itemView.findViewById(R.id.ingredient_names);
        descriptionTxtView = itemView.findViewById(R.id.ingredient_descriptionL);
        categoryTxtView = itemView.findViewById(R.id.ingredient_categoryL);
        unitTxtView = itemView.findViewById(R.id.ingredient_unitL);
        amountTxtView = itemView.findViewById(R.id.ingredient_amountL);
        pickUp = itemView.findViewById(R.id.pick_up_checkbox);

        ViewHolderListener viewHolderListener = new ViewHolderListener(listener);

        pickUp.setOnClickListener(viewHolderListener);
        itemView.setOnClickListener(viewHolderListener);
    }

    TextView getNameTxtView() {
        return nameTxtView;
    }

    TextView getDescriptionTxtView() {
        return descriptionTxtView;
    }

    TextView getCategoryTxtView() {
        return categoryTxtView;
    }

    TextView getUnitTxtView() {
        return unitTxtView;
    }

    TextView getAmountTxtView() {
        return amountTxtView;
    }
}