package com.company;
import java.util.Scanner;

public class Cat extends Animal {
    public Cat(String name,String gender){
        super(name,gender);
        initialPrice = 1800;
        healthGrowthRef = 0.5;
        breedQuantity = 3;
        healthStatus = "Healthy";
        isAlive = true;
        edibleFood = new String[]{"Meat","Grass"};
        currentAge = 0;
        maxAge = 16;
    }

    public int checkBalance(int balance){
        return balance - initialPrice;
    }

    public void printField(){
        System.out.println("");
        super.printField();
        System.out.println("Health: " + healthPercent
                + " Lost Health last round: " + lostHealth
                + " Added Health with food:" + addedHealth
                + " Health Status: " + healthStatus
        );
        System.out.println(" Current Age: " + currentAge
                + " Max Age: " + maxAge
                + " Price: " + initialPrice
                + " Breed quantity: " + breedQuantity);
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
        this.lostHealth  =  10 + (int)(Math.random() * (30-20+1));
        this.healthPercent = Math.max(this.healthPercent -this.lostHealth,0);
    }

    public void increaseAge(){
        this.currentAge++;
    }

    public void die(){
        if (this.healthPercent <= 0) {
            updateHealthStatus("Death");
            this.isAlive = false;
        }
        if (this.currentAge == this.maxAge){
            updateHealthStatus("Death");
            this.isAlive = false;
        }
    }

    public boolean isLiving(){
        if (healthStatus.equals("Death")){
            this.isAlive = false;
        }
        return this.isAlive;
    }

    public int getBreedQuantity(){
        return breedQuantity;
    }

    public int getHealthPercent(){return healthPercent;}

    public int getInitialPrice(){return initialPrice;}

    public static Animal breed(String gender){
        Scanner scanner = new Scanner(System.in);
        System.out.println("It is a new baby.What is the baby animals name:");
        var animalName = scanner.nextLine();
        return (new Cat(animalName,gender));
    }

    public void updateHealthStatus(String status){
        this.healthStatus = status;
    }

}