package com.example.nosh.Ingredients;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.example.nosh.entity.Ingredient;
import java.util.Date;
import java.util.Optional;

public class IngredientsTest {

    private Ingredient ingredients;

    @BeforeEach
    private void createTest(){
        ingredients = new Ingredient(new Date(),10,10,"spice", "enhances taste of other food", "pantry", "salt" );
    }

    @Test
    public void getDescriptionTest(){
        String desc = "enhances taste of other food";
        ingredients = new Ingredient(new Date(),10,10,"spice", desc, "pantry", "salt" );
        assertEquals(ingredients.getDescription(), desc);
    }

    @Test
    public void setDescriptionTest(){
        String desc = "milk";
        ingredients = new Ingredient(new Date(),10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        ingredients.setDescription(desc);
        assertEquals(ingredients.getDescription(), desc);
    }

    @Test
    public void getBestBeforeDate(){
        Date date = new Date();
       ingredients = new Ingredient(date,10,10,"spice", "enhances taste of other food", "pantry", "salt" );
       assertEquals(ingredients.getBestBeforeDate(), date);
   }

    @Test
    public void setBestBeforeDate(){
        Date date = new Date();
        ingredients = new Ingredient(new Date(),10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        ingredients.setBestBeforeDate(date);
        assertEquals(ingredients.getBestBeforeDate(), date);
    }

    @Test
    public void getLocationTest(){
        String location = "pantry";
        ingredients = new Ingredient(new Date(),10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        assertEquals(ingredients.getLocation(), location);
    }

    @Test
    public void setLocationTest(){
        String location = "fridge";
        ingredients = new Ingredient(new Date(),10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        ingredients.setLocation(location);
        assertEquals(ingredients.getLocation(), location);
    }

    @Test
    public void getCategoryTest(){
        String category = "spice";
        ingredients = new Ingredient(new Date(),10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        assertEquals(ingredients.getCategory(), category);
    }

    @Test
    public void setCategoryTest(){
        String category = "condiments";
        ingredients = new Ingredient(new Date(),10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        ingredients.setCategory(category);
        assertEquals(ingredients.getCategory(), category);
    }

    @Test
    public void getAmountTest(){
      long amount = 10;
       ingredients = new Ingredient(new Date(),10,10,"spice", "enhances taste of other food", "pantry", "salt" );
       assertEquals(ingredients.getAmount(), amount);
   }

    @Test
    public void setAmountTest(){
        long amount = 15;
        ingredients = new Ingredient(new Date(),10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        ingredients.setAmount(amount);
        assertEquals(ingredients.getAmount(), amount);
    }

    @Test
    public void getUnitTest(){
        double unit = 10;
        ingredients = new Ingredient(new Date(),10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        assertEquals(ingredients.getUnit(), unit);
    }

    @Test
    public void setUnitTest(){
        double unit = 10.2;
        ingredients = new Ingredient(new Date(),10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        ingredients.setUnit(unit);
        assertEquals(ingredients.getUnit(), unit);
    }
}