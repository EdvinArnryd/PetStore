package com.company;

import com.company.Animal;
import com.company.Food;
public class Cat extends Animal {
    public Cat(String name,String gender){
        super(name,gender);
        initialPrice = 1000;
        healthGrowthRef = 0.6;
        edibleFood = new String[]{"Meat","Grass"};
    }

    public int checkBalance(int balance){
        return balance - initialPrice;
    }

    public void eat(Food food, double quantity){
        this.totalFeedQuantity += quantity;
        var lastHealthPercent = this.healthPercent;
        if ((totalFeedQuantity >= healthGrowthRef) && (healthGrowthRef < 100)) {
            this.addedHealth = 10;
            this.healthPercent += this.addedHealth;
            if (this.healthPercent > 100){
                this.addedHealth = 100 - lastHealthPercent;
                this.healthPercent = 100;
            }
            this.totalFeedQuantity = 0;
        }
    }

    public void lostHealth(){
        int randomNum  = 10 + (int)(Math.random() * 21);
        lostHealth = randomNum;
        this.healthPercent = Math.max(this.healthPercent -lostHealth,0);
    }

    public void printField(){
        super.printField();
        System.out.println("Health Value: " + healthPercent
                + " Lost Health last round: " + lostHealth + " Added Health by feed:" + addedHealth);
    }

    public void die(){

    }

    public boolean isLiving(){
        return true;
    }

    public void breed(Animal animal){

    }

}