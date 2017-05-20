package home.gio.calorieplanner.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import home.gio.calorieplanner.Constants;

public class Product implements Serializable,Parcelable{
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

    protected Product(Parcel in) {
        name = in.readString();
        calories = in.readInt();
        carbohydrates = in.readInt();
        protein = in.readInt();
        fat = in.readInt();
        price = in.readString();
        category = in.readString();
        subMenu = in.readString();
        imageURL = in.readString();
        details = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(calories);
        dest.writeInt(carbohydrates);
        dest.writeInt(protein);
        dest.writeInt(fat);
        dest.writeString(price);
        dest.writeString(category);
        dest.writeString(subMenu);
        dest.writeString(imageURL);
        dest.writeString(details);
    }
}
