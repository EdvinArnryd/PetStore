package com.company;
import com.company.Food;

public class Meat extends Food {
    public int buyQuantity;

    public Meat(String foodType,int quantity){
        super(foodType);
        initialPrice = 400;
        this.totalQuantity += quantity;
        this.buyQuantity = quantity;
    }
    public int checkBalance(int balance){
        return balance - initialPrice * buyQuantity;
    }
    public void printField(){
        super.printField();
        System.out.println("Quantity: " + totalQuantity + " KG" );
    }
    public double getTotalQuantity(){
        return totalQuantity;
    }

    public void updateQuantity(double quantity){
        this.totalQuantity += quantity;
    }

    public void minusQuantity(double quantity){
        this.totalQuantity -= quantity;
    }


}