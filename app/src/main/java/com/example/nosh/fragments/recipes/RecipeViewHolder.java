package com.example.nosh.fragments.recipes;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;


public class RecipeViewHolder extends RecyclerView.ViewHolder {

    private final ImageView imageView;
    private final TextView nameTextView;
    private  final TextView descriptionTxtView;
    private final ImageButton delBtn;


    private final class ViewHolderListener implements View.OnClickListener {

        private final RecipeAdapter.RecyclerViewListener listener;

        ViewHolderListener(RecipeAdapter.RecyclerViewListener listener) {
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
    RecipeViewHolder(RecipeAdapter.RecyclerViewListener listener,
                            @NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.recipe_entry_img);
        nameTextView = itemView.findViewById(R.id.recipe_entry_name);
        descriptionTxtView = itemView.findViewById(R.id.recipe_entry_description);
        delBtn = itemView.findViewById(R.id.del_btnR);

        ViewHolderListener viewHolderListener = new ViewHolderListener(listener);

        delBtn.setOnClickListener(viewHolderListener);
        itemView.setOnClickListener(viewHolderListener);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public TextView getDescriptionTxtView() {return descriptionTxtView;}
}
