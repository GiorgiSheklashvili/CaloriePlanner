package home.gio.calorieplanner.model;

import home.gio.calorieplanner.Constants;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Product extends RealmObject {
    @Required
    @PrimaryKey
    private String name;
    private int quantity;
    private int calories;
    private int carbohydrates;
    private int protein;
    private int fat;
    private int alcohol;

    public int totalCalories(int desiredQuantity) {
        return desiredQuantity * (alcohol * Constants.ALCOHOL_CALORIES_PER_GRAM + fat * Constants.FAT_CALORIES_PER_GRAM
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

    public int getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(int alcohol) {
        this.alcohol = alcohol;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
