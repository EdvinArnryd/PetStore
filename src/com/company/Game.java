package com.company;
import java.util.*;


public class Game {
    public Game() {
        startMenu();
    }

    private void startMenu() {
        print("Welcome to the game!");
        Scanner scanner = new Scanner(System.in);
        do {
            {
                print("How many players are you (1-4):");
                while (!scanner.hasNextInt()) {
                    scanner.nextLine();
                }
                var countPlayers = scanner.nextInt();

                print("How many rounds do you want to play?(5-30):");
                while (!scanner.hasNextInt()) {
                    scanner.nextLine();
                }
                var rounds = scanner.nextInt();

                mainMenu(countPlayers, rounds);
            }
        } while (true);
    }

    public void mainMenu(int countPlayers, int rounds) {
        var players = new ArrayList<Player>();
        Scanner scanner = new Scanner(System.in);
        for (int i = 1; i <= countPlayers; i++) {
            System.out.printf("What is your name player%d:", i);
            var playersName = scanner.nextLine();
            players.add(new Player(playersName.toUpperCase()));
        }
        var countRounds = 5;
        do {
            for (int i = 1; i <= countPlayers; i++) {
                System.out.printf("It is your turn Player%d :%s", i, players.get(i - 1).name);
                System.out.println("");
                print("Please enter your choice:");
                print("-------------------------");
                print("1:Buy animal");
                print("2:Buy food");
                print("3:Feed animal");
                print("4:Breed animal");
                print("5:Sell animal");
                print("-------------------------");
                while (!scanner.hasNextInt()) {
                    scanner.nextLine();
                }
                var functionNumber = scanner.nextInt();
                switch (functionNumber) {
                    case 1:
                        var buyFlag = true;
                        do {
                            Animal myNewAnimal = Store.buyAnimal();
                            var balance = players.get(i - 1).balance;
                            var className = myNewAnimal.getClass().getSimpleName();
                            int restBalance = -1;
                            switch (className) {
                                case "Dog" -> restBalance = ((Dog) myNewAnimal).checkBalance(balance);
                                case "Cat" -> restBalance = ((Cat) myNewAnimal).checkBalance(balance);
                                case "Horse" -> restBalance = ((Horse) myNewAnimal).checkBalance(balance);
                                case "Chicken" -> restBalance = ((Horse) myNewAnimal).checkBalance(balance);
                                case "Sheep" -> restBalance = ((Horse) myNewAnimal).checkBalance(balance);
                            }
                            if (restBalance >= 0) {
                                players.get(i - 1).addAnimal(myNewAnimal);
                                players.get(i - 1).updateBalance(restBalance);
                            } else print("Sorry!You don't have enough money to buy this animal");
                            System.out.println("Would you like to buy another animal(y/n)");
                            buyFlag = (scanner.next()).toUpperCase().equals("Y");
                        } while (buyFlag);
                        for (var player : players) {
                            System.out.println("Name: " + player.name + " Balance " + player.balance);
                        }
                        break;
                    case 2:
                    case 3:
                    case 4:
                    default:
                        break;
                }

            }
            countRounds++;
        } while (countRounds >= rounds);

    }

    private void print(String x) {
        if (!x.equals("")) {
            System.out.println(x);
        }
    }

    private void toLostHealth(Player player){
        for(var animal: player.animals) {
            var className = animal.getClass().getSimpleName();
            switch(className){
                case "Dog"      -> ((Dog) animal).lostHealth();
                case "Cat"      -> ((Cat) animal).lostHealth();
                case "Horse"    -> ((Horse) animal).lostHealth();
                case "Chicken"  -> ((Chicken) animal).lostHealth();
                case "Sheep"   -> ((Sheep) animal).lostHealth();
            }
        }
    }

    private void showInfo(Player player){
        System.out.printf("You have money: %d ",player.balance);
        System.out.println("");
        if (player.animals.size() > 0 ){
            print("You currently have these animals");
            for(var animal: player.animals){
                System.out.print(animal.getClass().getSimpleName() + " ");
                animal.printField();
            }
        }
        if (player.foods.size() > 0 ){
            print("You currently have these types of food");
            for(var food: player.foods) {
                food.printField();
            }
        }
    }

    private void toBuyAnimals(Player player){
        Scanner scanner = new Scanner(System.in);
        var buyAnimal = true;
        do{
            Animal myNewAnimal = Store.buyAnimal();
            var balance = player.balance;
            var className = myNewAnimal.getClass().getSimpleName();
            int restBalance = -1 ;
            switch(className){
                case "Dog" -> restBalance=((Dog) myNewAnimal).checkBalance(balance);
                case "Cat" -> restBalance=((Cat) myNewAnimal).checkBalance(balance);
                case "Horse" -> restBalance=((Horse) myNewAnimal).checkBalance(balance);
                case "Chicken" -> restBalance=((Chicken) myNewAnimal).checkBalance(balance);
                case "Sheep" -> restBalance=((Sheep) myNewAnimal).checkBalance(balance);
            }
            if (restBalance >=0){
                player.addAnimal(myNewAnimal);
                player.updateBalance(restBalance);
            }
            else print("Sorry!You don't have enough money to buy this animal");
            System.out.println("Would you like to buy other animal(y/n)");
            buyAnimal = (scanner.next()).toUpperCase().equals("Y");
        }while (buyAnimal);
    }

    private void toBuyFoods(Player player){
        Scanner scanner = new Scanner(System.in);
        var buyFood = true;
        do{
            Food myNewFood = Store.buyFood();
            var balance = player.balance;
            var className = myNewFood.getClass().getSimpleName();
            int restBalance = -1 ;
            switch(className){
                case "Meat" -> restBalance=((Meat) myNewFood).checkBalance(balance);
                case "Corn" -> restBalance=((Corn) myNewFood).checkBalance(balance);
                case "Grass" -> restBalance=((Grass) myNewFood).checkBalance(balance);
            }
            var findSameFood = false;
            Food myRemoveFood = null;
            var foodTotalQuantity = 0.0;
            if (restBalance >=0){
                for(var food: player.foods) {
                    if (food.getFoodType().equals(className) ){
                        findSameFood = true;
                        myRemoveFood = food;
                        foodTotalQuantity = food.getTotalQuantity();
                    }
                }
                player.addFood(myNewFood);
                if (findSameFood){
                    player.removeFood(myRemoveFood);
                    for(var food: player.foods) {
                        if (food.getFoodType().equals(className) )
                        {food.updateQuantity(foodTotalQuantity);
                        }
                    }
                }
                player.updateBalance(restBalance);
            }
            else print("Sorry! You don't have enough money to buy this food");
            System.out.println("Would you like to buy something else?(y/n)");
            buyFood = (scanner.next()).toUpperCase().equals("Y");
        }while (buyFood);
    }

    private void toFeedAnimals(Player player){
        Scanner scanner = new Scanner(System.in);
        var feedAnimal = true;
        do{
            if (player.animals.size() == 0 ) {
                print("You don't have any animals to feed");
                feedAnimal = false;
                return;
            }

            if (player.foods.size() == 0 ) {
                print("You don't have any food");
                feedAnimal = false;
                return;
            }

            print("Enter the animal's name that you want to feed");
            var name = scanner.next();
            print("Enter the name of the food");
            var foodType = scanner.next();
            print("Please enter the amount");
            var quantity = scanner.next();
            var arrayOfCharsQuantity = quantity.toCharArray();
            double feedQuantity = (InputCheck.isNumeric(arrayOfCharsQuantity)? Double.parseDouble(quantity):0);
            Food foodToFeed = null;
            for(var food: player.foods) {
                if (food.getFoodType().toUpperCase().equals(foodType.toUpperCase())){
                    foodToFeed = food;
                }
            }
            if (foodToFeed == null){
                print("You don't have that type of food");
                return;
            }
            if (foodToFeed.getTotalQuantity() < feedQuantity){
                print("You don't have enough food");
                continue;
            }

            if (feedQuantity <= 0){
                print("Please input the feed quantity more than 0");
            }
            if (foodToFeed == null){
                print("You have enter the error food!");
                continue;
            }
            for(var animal: player.animals){
                var className = animal.getClass().getSimpleName();
                if (!isFoodSuitable(animal,className,foodType)){
                    print("The food that you choose can not feed the animal that you want to feed");
                    continue;
                }
                if (animal.getName().toUpperCase().equals(name.toUpperCase())) {
                    switch (className) {
                        case "Dog" -> ((Dog) animal).eat(foodToFeed, feedQuantity);
                        case "Cat" -> ((Cat) animal).eat(foodToFeed, feedQuantity);
                        case "Horse" -> ((Horse) animal).eat(foodToFeed, feedQuantity);
                        case "Chicken" -> ((Chicken) animal).eat(foodToFeed, feedQuantity);
                        case "Sheep" -> ((Sheep) animal).eat(foodToFeed, feedQuantity);
                    }
                }
                else {
                    print("You have enter the error food");
                    continue;
                }
            }
            var className = foodToFeed.getClass().getSimpleName();
            switch(className){
                case "Meat" -> ((Meat) foodToFeed).minusQuantity(feedQuantity);
                case "Corn" -> ((Corn)foodToFeed).minusQuantity(feedQuantity);
                case "Grass" -> ((Grass) foodToFeed).minusQuantity(feedQuantity);
            }

            System.out.println("Would you like to feed other animal(y/n)");
            feedAnimal = (scanner.next()).toUpperCase().equals("Y");
        }while (feedAnimal);
    }

    private boolean isFoodSuitable(Animal animal,String className,String foodType) {
        boolean b = switch (className) {
            case "Dog" -> Arrays.asList(((Dog) animal).edibleFood).contains(foodType);
            case "Cat" -> Arrays.asList(((Cat) animal).edibleFood).contains(foodType);
            case "Horse" -> Arrays.asList(((Horse) animal).edibleFood).contains(foodType);
            case "Chicken" -> Arrays.asList(((Chicken) animal).edibleFood).contains(foodType);
            case "Sheep" -> Arrays.asList(((Sheep) animal).edibleFood).contains(foodType);
            default -> false;
        };
        return b;
    }

}