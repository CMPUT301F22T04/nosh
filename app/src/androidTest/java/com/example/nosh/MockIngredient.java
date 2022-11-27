package com.example.nosh;

public class MockIngredient {
    public String ingName;
    public String ingDescription;
    public int quantity;
    public String unit;
    public String ingCategory;

    public MockIngredient(String ingName, String ingDescription, int quantity,
                          String unit, String ingCategory){
        this.ingName = ingName;
        this.ingDescription = ingDescription;
        this.quantity = quantity;
        this.unit = unit;
        this.ingCategory = ingCategory;
    }
}
