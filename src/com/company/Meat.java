package com.company;

public class Meat extends Food{
    public int initialPrice = 800;
    public int totalQuantity = 0;
    public int buyQuantity = 0;

    public Meat(String foodType,int quantity){
        super(foodType);
        this.totalQuantity += quantity;
        this.buyQuantity = quantity;
    }
    public int checkBalance(int balance){
        return balance - initialPrice * buyQuantity;
    }

}