package com.example.comp2000cw1;

public class DataModel {
    String dishName, dishType, dishDescription, dishPrice, dishAllergens, dishImage;

    //MENU DATAMODEL
    public DataModel(String dishName, String dishType, String dishDescription,
                     String dishPrice, String dishAllergens, String dishImage) {
        this.dishName = dishName;
        this.dishType = dishType;
        this.dishDescription = dishDescription;
        this.dishPrice = dishPrice;
        this.dishAllergens = dishAllergens;
        this.dishImage = dishImage;
    }

    public String getDishName() {
        return dishName;
    }
    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishType() {
        return dishType;
    }
    public void setDishType(String dishType) {
        this.dishType = dishType;
    }

    public String getDishDescription() {
        return dishDescription;
    }
    public void setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }

    public String getDishPrice() {
        return dishPrice;
    }
    public void setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getDishAllergens() {
        return dishAllergens;
    }
    public void setDishAllergens(String dishAllergens) {
        this.dishAllergens = dishAllergens;
    }

    public String getDishImage() {
        return dishImage;
    }
    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    @Override
    public String toString() {
        return "DataModel{" +
                "dishName='" + dishName + '\'' +
                ", dishType='" + dishType + '\'' +
                ", dishDescription='" + dishDescription + '\'' +
                ", dishPrice='" + dishPrice + '\'' +
                ", dishAllergens='" + dishAllergens + '\'' +
                ", dishImage='" + dishImage +
                '}';
    }
}
