package com.company;

import java.util.*;

public class Game {
    public Game(){
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


    public void mainMenu(int countPlayers,int rounds ) {
        var players = new ArrayList<Player>();
        Scanner scanner = new Scanner(System.in);
        for (int i = 1; i <= countPlayers; i++) {
            System.out.printf("Please enter your name player%d:", i);
            var playersName = scanner.nextLine();
            players.add(new Player(playersName.toUpperCase()));
        }
        var countRounds = 1;
        do{
            for (int i = 0; i < countPlayers; i++) {
                var exitPlayerName = "";
                if (players.get(i).name.equals(exitPlayerName)){
                    i++;
                    break;
                }
                if ((players.get(i).balance <= 0) && (players.get(i).animals.size() == 0 )){
                    print("You lost! Because you have no money and no animals!!!");
                    exitPlayerName = players.get(i).name;
                    break;
                }
                System.out.printf("It is your turn %d :%s. round:%d" + "\n",i+1,players.get(i).name,countRounds);
                if (players.get(i).animals.size() > 0 ){
                    toLostHealth(players.get(i));
                    for (var animal: players.get(i).animals) {
                        animal.increaseAge();
                        animal.die();
                    }
                    animalsGotSick(players.get(i));
                }

                showInfo(players.get(i));

                var removeDiedAnimal = false;
                for(var j = players.get(i).animals.size()-1;j >= 0;j--){
                    var diedAnimal = players.get(i).animals.get(j);
                    if (!diedAnimal.isAlive) {
                        players.get(i).removeAnimal(diedAnimal);
                        removeDiedAnimal = true;
                    }
                }
                if (removeDiedAnimal) {showInfo(players.get(i));}

                //print the game´s main menu
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

                switch (functionNumber){
                    case 1:
                        toBuyAnimals(players.get(i));
                        break;
                    case 2:
                        toBuyFoods(players.get(i));
                        break;
                    case 3:
                        toFeedAnimals(players.get(i));
                        break;
                    case 4:
                        toBreedAnimals(players.get(i));
                        break;
                    case 5:
                        toSellAnimals(players.get(i));
                        break;
                    default:
                        break;
                }

            }
            countRounds++;
        } while (countRounds <= rounds);
        print("You have played all rounds");
        print("Now is time to sell all animals for everyone and decide the winner");
        for (var player:players) {
            toSellAllAnimals(player);
        }
        players.sort((Player a, Player b) -> { return a.balance > b.balance ? -1 : 1; });
        var winnerName = players.get(0).name;
        var winnerBalance    = players.get(0).balance;
        System.out.printf("Congratulation!!! %s You won!!! You had the most amount of money :%d \n",winnerName,winnerBalance);
        print("Game over");
        System.exit(0);
    }

    private void print(String x){
        if(!x.equals("")){ System.out.println(x); }
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
        System.out.printf("You have money: %d  \n",player.balance);
        if (player.animals.size() > 0 ){
            print("You own following animals");
            System.out.println("-".repeat(110));
            for(var animal: player.animals){
                var animalType  = animal.getClass().getSimpleName();
                System.out.print(animalType + " ");
                animal.printField();
                if (!animal.isLiving()){
                    System.out.printf("Your animal:%s has died!!! \n",animal.getName());
                }
            }
            System.out.println("-".repeat(110));
        }
        if (player.foods.size() > 0 ){
            print("You own following food");
            System.out.println("-".repeat(110));
            for(var food: player.foods) {
                food.printField();
            }
            System.out.println("-".repeat(110));
        }

    }

    private void toBuyAnimals(Player player){
        Scanner scanner = new Scanner(System.in);
        var buyAnimal = true;
        do{
            Animal myNewAnimal = Store.buyAnimal(player);
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
            showInfo(player);
            System.out.println("Would you like to buy other animal(y/n)");
            buyAnimal = (scanner.next()).toUpperCase().equals("Y");
        }while (buyAnimal);
    }

    /**
     * In this method: the player can buy foods from store.
     * @param player
     */
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
            else print("Sorry!You don't have enough money to buy this food");
            showInfo(player);
            System.out.println("Would you like to buy other food(y/n)");
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
                print("You don't have any foods to feed");
                feedAnimal = false;
                return;
            }
            var animalType =  Dialogs.askAnimalType("feed");
            print("Please enter your food type to feed(only input number)");
            for(var i = 0; i <player.foods.size(); i++) {
                System.out.printf(i+1 + "."+ player.foods.get(i).getFoodType()+ " ");
            }
            System.out.println();
            var foodTypeNumber = scanner.next();
            var foodType = "";
            for(var i = 0; i <player.foods.size(); i++) {
                foodType = foodTypeNumber.equals((i+1)+"")?foodType =player.foods.get(i).getFoodType():"";
                if (!foodType.equals("")){
                    break;
                }
            }
            Food foodToFeed = null;
            for(var food: player.foods) {
                if (food.getFoodType().toUpperCase().equals(foodType.toUpperCase())){
                    foodToFeed = food;
                }
            }
            if (foodToFeed == null){
                print("You don't have that type of food");
                System.out.println("Would you like to continue to feed other animal(y/n)");
                feedAnimal = (scanner.next()).toUpperCase().equals("Y");
                continue;
            }
            print("How much food do you want to feed you animal");
            var quantity = scanner.next();
            var arrayOfCharsQuantity = quantity.toCharArray();
            double feedQuantity = (InputCheck.isNumeric(arrayOfCharsQuantity)? Double.parseDouble(quantity):0);

            if (foodToFeed.getTotalQuantity() < feedQuantity){
                print("You don't have enough food");
                System.out.println("Would you like to continue to feed other animal(y/n)");
                feedAnimal = (scanner.next()).toUpperCase().equals("Y");
                continue;
            }

            if (feedQuantity <= 0){
                print("Please input the feed quantity more than 0.0");
                System.out.println("Would you like to continue to feed other animals(y/n)");
                feedAnimal = (scanner.next()).toUpperCase().equals("Y");
                continue;
            }
            print("Please enter your animal's name to feed");
            var animalNameToFeed = scanner.next();
            if  (InputCheck.isAnimalNameNotExist(player,animalType,animalNameToFeed)){
                continue;
            }

            Animal animalToFeed = null;
            for(var animal: player.animals){
                if ((animal.getName().equals(animalNameToFeed)) && (animal.getClass().getSimpleName().equals(animalType))) {
                    animalToFeed = animal;
                }
            }
            if (!isFoodSuitable(animalToFeed,animalType,foodType)){
                print("The food you chose is not suitable for the animal you wanted to feed");
                System.out.println("Would you like to continue to feed other animal(y/n)");
                feedAnimal = (scanner.next()).toUpperCase().equals("Y");
                continue;
            }

            //Star to eat food
            switch (animalType) {
                case "Dog" -> ((Dog) animalToFeed).eat(foodToFeed, feedQuantity);
                case "Cat" -> ((Cat) animalToFeed).eat(foodToFeed, feedQuantity);
                case "Horse" -> ((Horse) animalToFeed).eat(foodToFeed, feedQuantity);
                case "Chicken" -> ((Chicken) animalToFeed).eat(foodToFeed, feedQuantity);
                case "Sheep" -> ((Sheep) animalToFeed).eat(foodToFeed, feedQuantity);
                default -> {throw new IllegalStateException("Unexpected value: " + animalType);}
            }
            var totalQuantity = 0.0 ;
            switch(foodType){
                case "Meat" -> {((Meat) foodToFeed).minusQuantity(feedQuantity);
                    totalQuantity = ((Meat) foodToFeed).getTotalQuantity();}
                case "Corn" -> {((Corn)foodToFeed).minusQuantity(feedQuantity);
                    totalQuantity = ((Corn) foodToFeed).getTotalQuantity();}
                case "Grass" -> {((Grass) foodToFeed).minusQuantity(feedQuantity);
                    totalQuantity = ((Grass) foodToFeed).getTotalQuantity();}
                default -> {throw new IllegalStateException("Unexpected value: " + foodType);}
            }
            if (totalQuantity == 0) {
                player.removeFood(foodToFeed);
            }
            showInfo(player);
            System.out.println("Would you like to feed other animal(y/n)");
            feedAnimal = (scanner.next()).toUpperCase().equals("Y");
        }while (feedAnimal);
    }
    private void toBreedAnimals(Player player){
        Scanner scanner = new Scanner(System.in);
        var breedAnimal = true;
        do{
            if (player.animals.size() == 0 ) {
                print("You don't have any animals to feed");
                breedAnimal = false;
                return;
            }
            var animalType = Dialogs.askAnimalType("breed");

            print("Please enter one of your animals name to breed");
            var animalName1 = scanner.next();
            if  (InputCheck.isAnimalNameNotExist(player,animalType,animalName1)){
                continue;
            }

            print("Please enter another of your animals name to breed");
            var animalName2 = scanner.next();
            if  (InputCheck.isAnimalNameNotExist(player,animalType,animalName2)){
                continue;
            }

            var canBeBreed = areAnimalSuitableToBreed(player,animalType,animalName1,animalName2);
            if (!canBeBreed){ continue;}
            var breedQuantity = 0;
            for(var animal:player.animals){
                if ((animal.getName().equals(animalName1)) && (animal.getClass().getSimpleName().equals(animalType))) {
                    breedQuantity = animal.getBreedQuantity();
                }
            }
            Animal myNewAnimal = null;
            var str = breedQuantity>1?"s":"";
            System.out.printf("Congratulations！！！%d baby animal%s will be born. \n",breedQuantity,str);
            for(var k = 0; k<breedQuantity; k++){
                int rd = Math.random()>0.5?1:0;
                String gender = (rd == 1? "MALE":"FEMALE");
                switch (animalType) {
                    case "Dog" ->  myNewAnimal = Dog.breed(gender);
                    case "Cat" ->  myNewAnimal = Cat.breed(gender);
                    case "Horse" ->  myNewAnimal = Horse.breed(gender);
                    case "Chicken" -> myNewAnimal = Chicken.breed(gender);
                    case "Sheep" -> myNewAnimal = Sheep.breed(gender);
                }
                player.addAnimal(myNewAnimal);
            }
            showInfo(player);
            System.out.println("Would you like to breed other animal(y/n)");
            breedAnimal = (scanner.next()).toUpperCase().equals("Y");
        }while (breedAnimal);
    }

    private void toSellAnimals(Player player){
        Scanner scanner = new Scanner(System.in);
        var sellAnimal = true;
        do{
            if (player.animals.size() == 0 ) {
                print("You don't have any animals to sell");
                sellAnimal = false;
                return;
            }
            var animalType = Dialogs.askAnimalType("sell");

            print("Please enter one of your animals name to sell");
            var animalNameToSell = scanner.next();
            if  (InputCheck.isAnimalNameNotExist(player,animalType,animalNameToSell)){
                System.out.println("Would you like to sell your other animal(y/n)");
                sellAnimal = (scanner.next()).toUpperCase().equals("Y");
                return;
            }
            Animal animalToSell = null;
            for(var animal:player.animals){
                if ((animal.getName().equals(animalNameToSell)) && (animal.getClass().getSimpleName().equals(animalType))) {
                    animalToSell = animal;
                }
            }
            var sellAmount = getAmountOfSellAnimal(animalToSell,animalType);
            player.increaseBalance(sellAmount);
            player.removeAnimal(animalToSell);
            showInfo(player);

            System.out.println("Would you like to sell other animal(y/n)");
            sellAnimal = (scanner.next()).toUpperCase().equals("Y");
        }while (sellAnimal);
    }

    private void  toSellAllAnimals(Player player){
        for(var animal:player.animals){
            var sellAmount = getAmountOfSellAnimal(animal,animal.getClass().getSimpleName());
            player.increaseBalance(sellAmount);
        }
        System.out.printf("%s You have money: %d  \n",player.name,player.balance);
    }

    private void animalsGotSick(Player player){
        for(var animal:player.animals){
            var animalType = animal.getClass().getSimpleName();
            if (animal.healthStatus.equals("Healthy")){
                int rndNum = new Random().nextInt(5) + 1;
                var animalHealthStatus=  (rndNum == 1? "Sick":"Healthy");
                if (animalHealthStatus.equals("Sick")){
                    switch (animalType){
                        case "Dog" ->  ((Dog) animal).updateHealthStatus(animalHealthStatus);
                        case "Cat" ->  ((Cat) animal).updateHealthStatus(animalHealthStatus);
                        case "Horse" -> ((Horse) animal).updateHealthStatus(animalHealthStatus);
                        case "Chicken" -> ((Chicken) animal).updateHealthStatus(animalHealthStatus);
                        case "Sheep" -> ((Sheep) animal).updateHealthStatus(animalHealthStatus);
                    }
                }
            }
        }
    }


    private boolean isFoodSuitable(Animal animal,String animalClassName,String foodType) {
        boolean b = switch (animalClassName) {
            case "Dog" -> Arrays.asList(((Dog) animal).edibleFood).contains(foodType);
            case "Cat" -> Arrays.asList(((Cat) animal).edibleFood).contains(foodType);
            case "Horse" -> Arrays.asList(((Horse) animal).edibleFood).contains(foodType);
            case "Chicken" -> Arrays.asList(((Chicken) animal).edibleFood).contains(foodType);
            case "Sheep" -> Arrays.asList(((Sheep) animal).edibleFood).contains(foodType);
            default -> false;
        };
        return b;
    }

    private boolean areAnimalSuitableToBreed(Player player, String animalType,String animalName1, String animalName2){
        String animalGender1 = "";
        String animalGender2 = "";
        var canBeBreed = false;
        for (var animal : player.animals) {
            if ((animal.getName().equals(animalName1)) && (animal.getClass().getSimpleName().equals(animalType))) {
                animalGender1 = animal.getGender();
            }
            if ((animal.getName().equals(animalName2)) && (animal.getClass().getSimpleName().equals(animalType))) {
                animalGender2 = animal.getGender();
            }
        }

        if (animalGender1.equals("MALE") && animalGender2.equals("FEMALE")) {
            canBeBreed = true;
        } else if (animalGender1.equals("FEMALE") && animalGender2.equals("MALE")) {
            canBeBreed = true;
        } else {
            System.out.println("These two animals can not breed " +
                    " They need to be opposite gender to breed");
            canBeBreed = false;
        }
        return canBeBreed;
    }

    private int getAmountOfSellAnimal(Animal animalToSell, String animalType) {
        var sellAmount = 0;
        var price = 0;
        var healthValue = 0;
        switch (animalType) {
            case "Dog" -> {  price =  ((Dog) animalToSell).getInitialPrice();
                healthValue =  ((Dog) animalToSell).getHealthPercent();
                sellAmount = (int)Math.round(price * healthValue /100);
            }
            case "Cat" -> {  price =  ((Cat) animalToSell).getInitialPrice();
                healthValue =  ((Cat) animalToSell).getHealthPercent();
                sellAmount = (int)Math.round(price * healthValue /100);
            }
            case "Horse" -> { price =  ((Horse) animalToSell).getInitialPrice();
                healthValue =  ((Horse) animalToSell).getHealthPercent();
                sellAmount = (int)Math.round(price * healthValue /100);
            }
            case "Chicken" -> { price =  ((Chicken) animalToSell).getInitialPrice();
                healthValue =  ((Chicken) animalToSell).getHealthPercent();
                sellAmount = (int)Math.round(price * healthValue /100);
            }
            case "Sheep" -> { price =  ((Sheep) animalToSell).getInitialPrice();
                healthValue =  ((Sheep) animalToSell).getHealthPercent();
                sellAmount = (int)Math.round(price * healthValue /100);
            }
        }
        return sellAmount;
    }
}