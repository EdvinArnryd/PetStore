package com.company;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Dialogs {

    public static String askAnimalType(String message) {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("What kind of animal would you want to %s? \n",message);
        if (message.equals("buy")){
            System.out.println("1:Dog 2000kr 2:Horse 3000kr 3:Cat 1500kr 4:Chicken 100kr 5:Sheep 500kr");
        }
        else if  (message.equals("sell")) {
            System.out.println("1:Dog 2000kr 2:Horse 3000kr 3:Cat 1500kr 4:Chicken 100kr 5:Sheep 500kr");
            System.out.println("Your sold price would be considered animal's health percent and animal's age");
        }
        else {
            System.out.println("1:Dog 2:Horse 3:Cat 4:Chicken 5:Sheep");
        }
        var choice = scanner.nextLine();
        while (!Arrays.asList("1", "2", "3", "4", "5").contains(choice)) {
            System.out.println("Input error,please input again");
            choice = scanner.nextLine();
        }
        var animalType = "";
        switch (choice) {
            case "1" -> animalType = "Dog";
            case "2" -> animalType = "Horse";
            case "3" -> animalType = "Cat";
            case "4" -> animalType = "Chicken";
            case "5" -> animalType = "Sheep";
            default -> animalType = "";
        }
        return animalType;
    }

    public static String askFoodType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What kind of food would you want to choose?");
        System.out.println("1:Beef Price:200kr 2:Milk Price 15kr 3:Grass 60kr 4:Corn 5kr 5:Oat 80kr");
        var choice = scanner.nextLine();
        while (!Arrays.asList("1", "2", "3", "4", "5").contains(choice)) {
            System.out.println("Input error,please input again");
            choice = scanner.nextLine();
        }
        var foodType = "";
        switch (choice) {
            case "1" -> foodType = "Beef";
            case "2" -> foodType = "Milk";
            case "3" -> foodType = "Grass";
            case "4" -> foodType = "Corn";
            case "5" -> foodType = "Oat";
            default -> foodType = "";
        }
        return foodType;
    }
}
