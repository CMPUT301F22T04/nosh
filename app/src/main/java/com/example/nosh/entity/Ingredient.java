package com.example.nosh.entity;

import com.google.common.hash.Hashing;
import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;


/**
 * Generalization ingredient (can be in ingredient storage, recipe, shopping list)
 */
public class Ingredient implements Hashable, Serializable {

    private boolean inStorage = false;
    private Date bestBeforeDate = new Date();
    private double unit;
    private int amount;
    private String category = "";
    private String description = "";
    private String location = "";
    private String name = "";
    private boolean Ascending = false;
    private boolean Descending = false;

    /**
     * This field is used for Id of a document in the database
     * The hashcode generate by taking the time of which this object creates, 
     * and then hash using sha256. 
     */
    private String hashcode;

    /**
     * For creating new Object from Firestore
     */
    public Ingredient() {

    }

    /**
     * This constructor is for creating ingredients in the ingredient storage
     */
    public Ingredient(Date bestBeforeDate, double unit, int amount,
                      String category, String description, String location,
                      String name) {
        this(unit, amount, category, description, name);
        this.bestBeforeDate = bestBeforeDate;
        this.location = location;
        inStorage = true;
    }

    public Ingredient(double unit, int amount, String category, String description,
                      String name) {
        this.amount = amount;
        this.unit = unit;
        this.name = name;
        this.description = description;
        this.category = category;
        hashcode = Hashing.sha256().hashInt(new Timestamp(new Date()).getNanoseconds())
                .toString();
    }

    public boolean isInStorage() {
        return inStorage;
    }

    public void setInStorage(boolean inStorage) {
        this.inStorage = inStorage;
    }

    public Date getBestBeforeDate() {
        return bestBeforeDate;
    }

    public void setBestBeforeDate(Date bestBeforeDate) {
        this.bestBeforeDate = bestBeforeDate;
    }

    public double getUnit() {
        return unit;
    }

    public void setUnit(double unit) {
        this.unit = unit;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashcode() {
        return hashcode;
    }

    public void setHashcode(String hashcode) {
        this.hashcode = hashcode;
    }

    public boolean getAscending(){return Ascending;}

    public void setAscending(boolean value){
        Ascending = value;
    }
    public boolean getDescending(){return Descending;}

    public void setDescending(boolean value){
        Descending = value;
    }



    /**
     * Following  4 functions are custom comparator functions for sorting
     */
    public static Comparator<Ingredient> DescriptionComparator = new Comparator<Ingredient>() {

        public int compare(Ingredient d1, Ingredient d2) {
            String desc1 = d1.getDescription().toUpperCase();
            String desc2 = d2.getDescription().toUpperCase();

            //ascending order
            return desc1.compareTo(desc2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};
    public static Comparator<Ingredient> DateComparator = new Comparator<Ingredient>() {

        public int compare(Ingredient d1, Ingredient d2) {
            Date date1 = d1.getBestBeforeDate();
            Date date2 = d2.getBestBeforeDate();

            //ascending order
            return date1.compareTo(date2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};
    public static Comparator<Ingredient> LocationComparator = new Comparator<Ingredient>() {

        public int compare(Ingredient l1, Ingredient l2) {
            String loc1 = l1.getLocation().toUpperCase();
            String loc2 = l2.getLocation().toUpperCase();

            //ascending order
            return loc1.compareTo(loc2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};
    public static Comparator<Ingredient> CategoryComparator = new Comparator<Ingredient>() {

        public int compare(Ingredient c1, Ingredient c2) {
            String cat1 = c1.getCategory().toUpperCase();
            String cat2 = c2.getCategory().toUpperCase();

            //ascending order
            return cat1.compareTo(cat2);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};

    public static Comparator<Ingredient> DescriptionComparatorD = new Comparator<Ingredient>() {

        public int compare(Ingredient d1, Ingredient d2) {
            String desc1 = d1.getDescription().toUpperCase();
            String desc2 = d2.getDescription().toUpperCase();

            //ascending order
            return desc2.compareTo(desc1);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};
    public static Comparator<Ingredient> DateComparatorD = new Comparator<Ingredient>() {

        public int compare(Ingredient d1, Ingredient d2) {
            Date date1 = d1.getBestBeforeDate();
            Date date2 = d2.getBestBeforeDate();

            //ascending order
            return date2.compareTo(date1);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};
    public static Comparator<Ingredient> LocationComparatorD = new Comparator<Ingredient>() {

        public int compare(Ingredient l1, Ingredient l2) {
            String loc1 = l1.getLocation().toUpperCase();
            String loc2 = l2.getLocation().toUpperCase();

            //ascending order
            return loc2.compareTo(loc1);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};
    public static Comparator<Ingredient> CategoryComparatorD = new Comparator<Ingredient>() {

        public int compare(Ingredient c1, Ingredient c2) {
            String cat1 = c1.getCategory().toUpperCase();
            String cat2 = c2.getCategory().toUpperCase();

            //ascending order
            return cat2.compareTo(cat1);

            //descending order
            //return StudentName2.compareTo(StudentName1);
        }};
}
