package com.example.nosh.fragments.Shopping;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;


class ShoppingViewHolder extends RecyclerView.ViewHolder {

    private final TextView nameTxtView;
    private final TextView descriptionTxtView;

    private final class ViewHolderListener implements View.OnClickListener {

        private final ShoppingAdapter.RecyclerViewListener listener;

        ViewHolderListener(ShoppingAdapter.RecyclerViewListener listener) {
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            //if (v.getId() == delBtn.getId()) {
                //listener.onDeleteButtonClick(getAdapterPosition());
            //} else {
                //listener.onEditClick(getAdapterPosition());
            //}
        }
    }

    ShoppingViewHolder(ShoppingAdapter.RecyclerViewListener
                                 listener, @NonNull View itemView) {
        super(itemView);

        //delBtn = itemView.findViewById(R.id.delete_ingredient_button);
        nameTxtView = itemView.findViewById(R.id.ingredient_names);
        descriptionTxtView = itemView.findViewById(R.id.ingredient_descriptions);

        ViewHolderListener viewHolderListener = new ViewHolderListener(listener);

        //delBtn.setOnClickListener(viewHolderListener);
        itemView.setOnClickListener(viewHolderListener);
    }

    TextView getNameTxtView() {
        return nameTxtView;
    }

    TextView getDescriptionTxtView() {
        return descriptionTxtView;
    }
}
