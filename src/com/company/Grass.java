package com.company;

public class Grass extends Food{
    public int initialPrice = 500;
    public int totalQuantity = 0;
    public int buyQuantity = 0;

    public Grass(String foodType,int quantity){
        super(foodType);
        this.totalQuantity += quantity;
        this.buyQuantity = quantity;
    }
    public int checkBalance(int balance){
        return balance - initialPrice * buyQuantity;
    }

}