package com.example.nosh.fragments.ingredients;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;


class IngredientViewHolder extends RecyclerView.ViewHolder {

    public final ImageButton delBtn;
    private final TextView nameTxtView;
    private final TextView descriptionTxtView;

    private final class ViewHolderListener implements View.OnClickListener {

        private final IngredientAdapter.RecyclerViewListener listener;

        ViewHolderListener(IngredientAdapter.RecyclerViewListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == delBtn.getId()) {
                listener.onDeleteButtonClick(getAdapterPosition());
            } else {
                listener.onEditClick(getAdapterPosition());
            }
        }
    }

    IngredientViewHolder(IngredientAdapter.RecyclerViewListener
                                              listener, @NonNull View itemView) {
        super(itemView);

        delBtn = itemView.findViewById(R.id.del_btn);
        nameTxtView = itemView.findViewById(R.id.ingredient_entry_name);
        descriptionTxtView = itemView.findViewById(R.id.ingredient_entry_description);

        ViewHolderListener viewHolderListener = new ViewHolderListener(listener);

        delBtn.setOnClickListener(viewHolderListener);
        itemView.setOnClickListener(viewHolderListener);
    }

    TextView getNameTxtView() {
        return nameTxtView;
    }

    TextView getDescriptionTxtView() {
        return descriptionTxtView;
    }
}
