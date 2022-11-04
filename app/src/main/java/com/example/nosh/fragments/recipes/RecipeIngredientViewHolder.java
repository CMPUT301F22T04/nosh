package com.example.nosh.fragments.recipes;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nosh.R;


class RecipeIngredientViewHolder extends RecyclerView.ViewHolder {

    public final ImageButton delBtn;
    private final TextView nameTxtView;
    private final ImageButton edtBtn;

    private final class ViewHolderListener implements View.OnClickListener {

        private final RecipeIngredientAdapter.RecyclerViewListener listener;

        ViewHolderListener(RecipeIngredientAdapter.RecyclerViewListener listener) {
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

    RecipeIngredientViewHolder(RecipeIngredientAdapter.RecyclerViewListener
                                 listener, @NonNull View itemView) {
        super(itemView);

        delBtn = itemView.findViewById(R.id.del_btn_recipe_ingredient);
        nameTxtView = itemView.findViewById(R.id.stored_ingredient_recipe_name);
        edtBtn = itemView.findViewById(R.id.edit_btn_recipe_ingredient);
        ViewHolderListener viewHolderListener = new ViewHolderListener(listener);

        delBtn.setOnClickListener(viewHolderListener);
        itemView.setOnClickListener(viewHolderListener);
    }

    TextView getNameTxtView() {
        return nameTxtView;
    }

}
