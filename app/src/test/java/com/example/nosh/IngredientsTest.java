package com.example.nosh;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.nosh.entity.Ingredient;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;


public class IngredientsTest {

    private Ingredient ingredients;

    @BeforeEach
    private void createTest(){
        ingredients = new Ingredient(20221231,10,10,"spice", "enhances taste of other food", "pantry", "salt" );
    }

    @Test
    public void getDescriptionTest(){
        String desc = "salt";
        ingredients = new Ingredient(20221231,10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        assertEquals(ingredients.getDescription(), desc);
    }

    @Test
    public void setDescriptionTest(){
        String desc = "milk";
        ingredients = new Ingredient(20221231,10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        ingredients.setDescription(desc);
        assertEquals(ingredients.getDescription(), desc);
    }

    @Test
    public void getBestBeforeDate(){
        Date date = 20221231;
        ingredients = new Ingredient(20221231,10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        assertEquals(ingredients.getBestBeforeDate(), date);
    }

    @Test
    public void setBestBeforeDate(){
        Date date = 20221125;
        ingredients = new Ingredient(20221231,10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        ingredients.setBestBeforeDate(date);
        assertEquals(ingredients.getBestBeforeDate(), date);
    }

    @Test
    public void getLocationTest(){
        String location = "pantry";
        ingredients = new Ingredient(20221231,10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        assertEquals(ingredients.getLocation(), location);
    }

    @Test
    public void setLocationTest(){
        String location = "fridge";
        ingredients = new Ingredient(20221231,10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        ingredients.setLocation(location);
        assertEquals(ingredients.getLocation(), location);
    }

    @Test
    public void getCategoryTest(){
        String category = "spice";
        ingredients = new Ingredient(20221231,10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        assertEquals(ingredients.getCategory(), category);
    }

    @Test
    public void setCategoryTest(){
        String category = "condiments";
        ingredients = new Ingredient(20221231,10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        ingredients.setCategory(category);
        assertEquals(ingredients.getCategory(), category);
    }



    @Test
    public void getAmountTest(){
        Integer amount = 10;
        ingredients = new Ingredient(20221231,10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        assertEquals(ingredients.getAmount(), Optional.of(amount));
    }

    @Test
    public void setAmountTest(){
        Integer amount = 15;
        ingredients = new Ingredient(20221231,10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        ingredients.setAmount(amount);
        assertEquals(ingredients.getAmount(), Optional.of(amount));
    }

    @Test
    public void getUnitTest(){
        double unit = 10;
        ingredients = new Ingredient(20221231,10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        assertEquals(ingredients.getUnit(), unit);
    }

    @Test
    public void setUnitTest(){
        double unit = 10.2;
        ingredients = new Ingredient(20221231,10,10,"spice", "enhances taste of other food", "pantry", "salt" );
        ingredients.setUnit(unit);
        assertEquals(ingredients.getUnit(), unit);
    }
}
