package com.example.nosh.fragments.ingredients;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nosh.R;
import com.example.nosh.entity.Ingredient;
import com.example.nosh.utils.DateUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;


public class ViewIngredient extends AppCompatActivity {
    private FloatingActionButton deleteButton;
    private FloatingActionButton saveButton;
    private Button backButton;
    private EditText ingName;
    private EditText ingDescription;
    private EditText ingDate;
    private EditText ingLocation;
    private EditText ingCount;
    private EditText ingAmount;

    /**
     * Event listener for Edit / View
     */
    private class ViewEditIngredientListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == backButton.getId()) {
                setResult(-1, null);
                finish();
            } else if (view.getId() == saveButton.getId()) {

                Intent intent = getIntent();
//                intent.putExtra("Test", 1234);
                /**
                 * Code you get all the text from EditText view
                 */

                intent.putExtra("name", ingName.getText().toString());

                setResult(0, intent);
                finish();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ingredient_view);

        ViewEditIngredientListener listener = new ViewEditIngredientListener();

        // Buttons and EditText initialization
        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backToMainButton);
        ingName = findViewById(R.id.nameText);
        ingDescription = findViewById(R.id.descriptionText);
        ingDate = findViewById(R.id.dateText);
        ingLocation = findViewById(R.id.locationText);
        ingCount = findViewById(R.id.unitText);
        ingAmount = findViewById(R.id.amountText);
        getFood();

        backButton.setOnClickListener(listener);
        saveButton.setOnClickListener(listener);
//        onEditClick();
    }

    public void getFood() {
        // add the newly submitted food to the array
        Intent intent = getIntent();
        String newFoodJson = intent.getStringExtra("ingredient");
        if (newFoodJson != null){
            Gson gson = new Gson();
            Ingredient newFood = gson.fromJson(newFoodJson, Ingredient.class);
            ingName.setText(newFood.getName());
            ingDescription.setText(newFood.getDescription());
            String stringDate = "Expiry Date: " + DateUtil.formatDate(newFood.getBestBeforeDate());
            ingDate.setText(stringDate);
            String stringLocation = "Location: " + newFood.getLocation();
            ingLocation.setText(stringLocation);
            String stringAmount = "Amount: " + String.valueOf(newFood.getAmount());
            ingCount.setText(stringAmount);
            String stringCost = "Cost: " + String.valueOf(newFood.getUnit());
            ingAmount.setText(stringCost);
        }
    }


//    public void onEditClick(){
//        // edit a food
//        saveButton.setOnClickListener(view -> {
//            Intent intent = getIntent();
//            String positionEdit = intent.getStringExtra("position");
//            String foodJson = intent.getStringExtra("foodJson");
//            Intent switchActivityIntent = new Intent(this, AddActivity.class);
//            switchActivityIntent.putExtra("editOrAdd","edit");
//            switchActivityIntent.putExtra("editFoodJson",foodJson);
//            switchActivityIntent.putExtra("positionEdit", positionEdit);
//            startActivity(switchActivityIntent);
//        });
//    }
}


