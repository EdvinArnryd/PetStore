package com.company;

public abstract class Food {
    private String FoodType;
    protected double totalQuantity = 0;
    protected int initialPrice = 0;
    protected int buyQuantity = 0 ;

    public Food(String FoodType){
        this.FoodType = FoodType;
    }

    public String getFoodType(){
        return this.FoodType;
    }
}