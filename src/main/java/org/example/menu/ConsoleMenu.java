package org.example.menu;

import org.example.model.*;
import org.example.shelter.Shelter;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final Shelter<Animal> shelter;
    private final Scanner scanner = new Scanner(System.in);
    private final List<String> species;
    private final Menu mainMenu = new Menu(List.of(
            new MenuOption(1, "Add animal"),
            new MenuOption(2, "List all animals"),
            new MenuOption(3, "Find animals by species"),
            new MenuOption(4, "List available animals"),
            new MenuOption(5, "Mark animal as adopted"),
            new MenuOption(0, "Exit")));


    public ConsoleMenu(Shelter<Animal> shelter) {
        this.shelter = shelter;
        this.species = shelter.getSpecies();
    }

    private static <T> void displayList(String title, List<T> list) {
        System.out.println(title);
        if (list.get(0) instanceof MenuOption) {
            for (T item : list) {
                System.out.println(item);
            }
        } else {
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + 1 + " | " + list.get(i));
            }
        }
    }


    public void start() {
        boolean run = true;

        while (run) {
            System.out.println("+".repeat(30));
            int choice = getValidChoice("Main Menu", mainMenu.getOptions());
            if (choice == 0) run = false;

            System.out.print("\033[H\033[2J");
            System.out.flush();
            switch (choice) {
                case 1:
                    int speciesToAddChoice = getValidChoice("Choose Species of Animal to create", species);
                    if (speciesToAddChoice == 0) break;
                    System.out.println("You are creating a new " + species.get(speciesToAddChoice - 1));
                    String name = getValidName();
                    int age = getValidAge();

                    Animal animal = createAnimal(speciesToAddChoice, name, age);
                    shelter.addAnimal(animal);
                    System.out.println("Animal added");
                    System.out.println(animal);

                    break;
                case 2:
                    displayList("List of All Animals", shelter.getAnimals());
                    break;
                case 3:
                    int speciesChoice = getValidChoice("Choose Species", shelter.getSpecies());
                    if (speciesChoice == 0) break;

                    String selectedSpecies = species.get(speciesChoice - 1);
                    if (shelter.findBySpecies(selectedSpecies).isEmpty()) {
                        System.out.println("There are no animals with species " + selectedSpecies);
                        break;
                    }
                    displayList("List of Animals with type " + selectedSpecies, shelter.findBySpecies(selectedSpecies));
                    break;
                case 4:
                    displayList("Animals available for adoption", shelter.findAvailableAnimals());
                    break;
                case 5:
                    List<Animal> availableAnimals = shelter.findAvailableAnimals();
                    int adoptAnimalChoice = getValidChoice("Mark animal as adopted", availableAnimals);
                    if (adoptAnimalChoice == 0) break;
                    shelter.markAsAdopted(availableAnimals.get(adoptAnimalChoice - 1).getId().toString());
                    System.out.println(availableAnimals.get(adoptAnimalChoice - 1));
                    break;
                default:
                    run = false;
            }
        }

        scanner.close();

    }

    private Animal createAnimal(int speciesToAddChoice, String name, int age) {

        return switch (species.get(speciesToAddChoice - 1)) {
            case "Dog" -> new Dog(new AnimalId(), name, age);
            case "Cat" -> new Cat(new AnimalId(), name, age);
            case "Bird" -> new Bird(new AnimalId(), name, age);
            case "Snake" -> new Snake(new AnimalId(), name, age);
            default -> throw new IllegalStateException("Unexpected value: " + species.get(speciesToAddChoice - 1));
        };
    }

    private int getValidAge() {

        while (true) {
            System.out.print("Enter age: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please use only numbers larger than 0");
                scanner.next();
                continue;
            }
            int age = scanner.nextInt();
            scanner.nextLine();
            if (age <= 0) {
                System.out.println("Invalid input! Please enter a number larger than 0");
            } else return age;
        }

    }

    private String getValidName() {
        while (true) {
            System.out.print("Enter name: ");
            String name = scanner.nextLine();
            if (!name.isBlank()) return name;
            System.out.println("Name can't be blank.");
        }

    }

    private <T> int getValidChoice(String title, List<T> list) {
        int maxChoice;
        if (list.get(0) instanceof MenuOption) {
            maxChoice = list.size() - 1;
        } else {
            maxChoice = list.size();
        }
        displayList(title, list);
        System.out.print("Enter choice number, 0 to go back: ");
        while (true) {
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number between 1 and " + maxChoice + " or 0 to exit");
                // scanner.next() needs to be here, as the hasNextInt() keeps the wrong input
                scanner.next();
                continue;
            }
            int choice = scanner.nextInt();
            scanner.nextLine();
            if (choice < 0 || choice > maxChoice) {
                System.out.println("Invalid input! Please enter a number between 1 and " + maxChoice + " or 0 to exit");
            } else return choice;
        }
    }

}
