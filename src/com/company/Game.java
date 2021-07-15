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
        var countRounds = 5;
        do{
            for (int i = 0; i < countPlayers; i++) {
                System.out.printf("It is your turn Player%d :%s",i+1,players.get(i).name + "\n");
                toLostHealth(players.get(i));
                if (players.get(i).animals.size() > 0 ){
                    for (var animal: players.get(i).animals) {
                        animal.die();
                    }
                }

                showInfo(players.get(i));

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
                    default:
                        break;
                }

            }
            countRounds++;
        } while (countRounds >= rounds);

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

    //This method to show players information about the player owned.
    private void showInfo(Player player){
        System.out.printf("Your money: %d ",player.balance);
        System.out.println("");
        if (player.animals.size() > 0 ){
            print("Animals you currently own: ");
            for(var animal: player.animals){
                System.out.print(animal.getClass().getSimpleName() + " ");
                animal.printField();
                if (!animal.isLiving()){
                    System.out.printf("Your animal:%s has died!!!",animal.getName());
                }
            }
        }
        if (player.foods.size() > 0 ){
            print("Food you currently own: ");
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
            System.out.println("Would you like to buy another animal(y/n)");
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
            else print("Sorry!You don't have enough money to buy this food");
            System.out.println("Would you like to buy other food?(y/n)");
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
            print("Please enter your animals name to feed");
            var name = scanner.next();
            if  (!InputCheck.isAnimalNameExist(player,name)){
                continue;
            };

            print("Please enter your food type to feed(only input number)");
            for(var i = 0; i <player.foods.size(); i++) {
                System.out.printf(i+1 + "."+ player.foods.get(i).getFoodType()+ " ");
            }
            System.out.println("");
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
                print("You don´t have such food to feed.");
                continue;
            }

            print("How much food do you want to feed your animal?");
            var quantity = scanner.next();
            var arrayOfCharsQuantity = quantity.toCharArray();
            double feedQuantity = (InputCheck.isNumeric(arrayOfCharsQuantity)? Double.parseDouble(quantity):0);

            if (foodToFeed.getTotalQuantity() < feedQuantity){
                print("You don't have so many food.");
                continue;
            }

            if (feedQuantity <= 0){
                print("Please input the feed quantity more than 0.");
                continue;
            }
            var animalClassName = "";
            Animal animalToFeed = null;
            for(var animal: player.animals){
                if (animal.getName().toUpperCase().equals(name.toUpperCase())) {
                    animalClassName = animal.getClass().getSimpleName();
                    animalToFeed = animal;
                }
            }

            if (!isFoodSuitable(animalToFeed,animalClassName,foodType)){
                print("Your animal doesn't like that food");
                continue;
            }
            switch (animalClassName) {
                case "Dog" -> ((Dog) animalToFeed).eat(foodToFeed, feedQuantity);
                case "Cat" -> ((Cat) animalToFeed).eat(foodToFeed, feedQuantity);
                case "Horse" -> ((Horse) animalToFeed).eat(foodToFeed, feedQuantity);
                case "Chicken" -> ((Chicken) animalToFeed).eat(foodToFeed, feedQuantity);
                case "Sheep" -> ((Sheep) animalToFeed).eat(foodToFeed, feedQuantity);
                default -> {throw new IllegalStateException("Unexpected value: " + animalClassName);}
            }
            //After the animal eating food,minus the foods quantity
            switch(foodType){
                case "Meat" -> ((Meat) foodToFeed).minusQuantity(feedQuantity);
                case "Corn" -> ((Corn)foodToFeed).minusQuantity(feedQuantity);
                case "Grass" -> ((Grass) foodToFeed).minusQuantity(feedQuantity);
            }

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
            var animalType = Dialogs.askAnimalType();

            List<String> animalName = new ArrayList<>();
            for(var animal:player.animals){
                animalName.add(animal.getName());
            }
            print("Please enter one of your animals name to breed");
            var animalName1 = scanner.next();
            if  (!InputCheck.isAnimalNameExist(player,animalName1)){
                continue;
            };

            print("Enter your second animal to breed");
            var animalName2 = scanner.next();
            if  (!InputCheck.isAnimalNameExist(player,animalName2)){
                continue;
            };

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
            System.out.println("Would you like to breed other animal(y/n)");
            breedAnimal = (scanner.next()).toUpperCase().equals("Y");
        }while (breedAnimal);
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
            //Check if the animal is suitable for breed
            if ((animal.getName().equals(animalName1)) && (animal.getClass().getSimpleName().equals(animalType))) {
                animalGender1 = animal.getGender();
            }
            if ((animal.getName().equals(animalName2)) && (animal.getClass().getSimpleName().equals(animalType))) {
                animalGender2 = animal.getGender();
            }
        }
        System.out.printf("animalGender1:%s animalGender2:%s",animalGender1,animalGender2);
        System.out.println("");
        if (animalGender1.equals("MALE") && animalGender2.equals("FEMALE")) {
            canBeBreed = true;
        } else if (animalGender1.equals("FEMALE") && animalGender2.equals("MALE")) {
            canBeBreed = true;
        } else {
            System.out.println("These two animals can't breed" +
                    " They need to be opposite gender to breed");
            canBeBreed = false;
        }
        return canBeBreed;
    }
}