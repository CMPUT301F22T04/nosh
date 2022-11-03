package com.example.nosh.fragments.recipes;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;

public class StoredRecipeViewHolder extends RecyclerView.ViewHolder {
    private final TextView nameTxtView;
    private final ImageView recipeImageView;

    private final class ViewHolderListener implements View.OnClickListener{
        private final StoredRecipeAdapter.RecyclerViewListener listener;

        ViewHolderListener(StoredRecipeAdapter.RecyclerViewListener listener) {
            this.listener = listener;
        }
        @Override
        public void onClick(View v) {
            listener.onEditClick(getAdapterPosition());
        }

    }

    StoredRecipeViewHolder(StoredRecipeAdapter.RecyclerViewListener listener,
                          @NonNull View itemView){
        super(itemView);
        nameTxtView = itemView.findViewById(R.id.stored_recipe_name);
        recipeImageView = itemView.findViewById(R.id.image);

        ViewHolderListener viewHolderListener = new ViewHolderListener(listener);
        itemView.setOnClickListener(viewHolderListener);

    }
    TextView getNameTxtView() {
        return nameTxtView;
    }

    ImageView getImageView() {return recipeImageView;}

}
