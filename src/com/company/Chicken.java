package com.company;
import java.util.Scanner;

public class Chicken extends Animal {
    public Chicken(String name,String gender){
        super(name,gender);
        initialPrice = 2000;
        healthGrowthRef = 0.5;
        edibleFood = new String[]{"Corn","Grass"};
        breedQuantity = 3;
        isAlive = true;
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
        lostHealth =  10 + (int)(Math.random() * (30-20+1));
        this.healthPercent = Math.max(this.healthPercent -lostHealth,0);
    }

    public void printField(){
        super.printField();
        System.out.println("Health Value: " + healthPercent
                + " Lost Health last round: " + lostHealth + " Healed from food: " + addedHealth);
    }

    public void die(){
        if (this.healthPercent <= 0) {
            this.isAlive = false;
        }
    }

    public boolean isLiving(){
        return this.isAlive;
    }

    public int getBreedQuantity(){
        return breedQuantity;
    }

    public static Animal breed(String gender) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("It is a new baby.What is the baby animals name:");
        var animalName = scanner.nextLine();
        return (new Chicken(animalName, gender));
    }
}