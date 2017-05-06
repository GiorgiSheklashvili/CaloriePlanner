package home.gio.calorieplanner.models;

import java.io.Serializable;

import home.gio.calorieplanner.Constants;

public class Product implements Serializable{
    private String name;
    private int calories;
    private int carbohydrates;
    private int protein;
    private int fat;
    private String price;
    private String category;
    private String subMenu;
    private String imageURL;
    private String details;

    public Product() {
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int totalCalories(int desiredQuantity) {
        return desiredQuantity * (fat * Constants.FAT_CALORIES_PER_GRAM
                + carbohydrates * Constants.CARB_CALORIES_PER_GRAM + protein * Constants.PROTEIN_CALORIES_PER_GRAM);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(int carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(String subMenu) {
        this.subMenu = subMenu;
    }
}
