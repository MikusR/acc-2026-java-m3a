package org.example.menu;

import org.example.model.*;
import org.example.shelter.Shelter;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final Shelter<Animal> shelter;
    private final Scanner scanner = new Scanner(System.in);
    private final List<String> species;

    public ConsoleMenu(Shelter<Animal> shelter) {
        this.shelter = shelter;
        this.species = shelter.getSpecies();
    }

    private static <T> void displayList(String title, List<T> list) {
        System.out.println(title);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + " | " + list.get(i));
        }
    }

    public void start() {
        boolean run = true;

        while (run) {
            System.out.println("+".repeat(30));
            int choice = getValidChoice("Main Menu", mainMenu());

            if (choice == 0) run = false;

            System.out.print("\033[H\033[2J");
            System.out.flush();
            switch (choice) {
                case 1:
                    int speciesToAddChoice = getValidChoice("Choose Species of Animal to create", species);
                    Animal animal = createAnimal(speciesToAddChoice);
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

                    shelter.markAsAdopted(availableAnimals.get(adoptAnimalChoice - 1).getId().toString());
                    System.out.println(availableAnimals.get(adoptAnimalChoice - 1));
                    break;
                default:
                    run = false;
            }
        }

        scanner.close();

    }

    private Animal createAnimal(int speciesToAddChoice) {
        String name = getValidName();
        int age = getValidAge();

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
            System.out.print("Name can't be blank.");
        }

    }

    private <T> int getValidChoice(String title, List<T> list) {
        int maxChoice = list.size();
        displayList(title, list);
        System.out.print("Enter choice number, 0 to go back");
        while (true) {
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number between 1 and " + maxChoice + " or 0 to exit");
                // scanner.next() needs to be here, as the hasNextInt() keeps the wrong input
                scanner.next();
                continue;
            }
            int choice = scanner.nextInt();
            if (choice < 0 || choice > maxChoice) {
                System.out.println("Invalid input! Please enter a number between 1 and " + maxChoice + " or 0 to exit");
            } else return choice;
        }
    }


    private List<String> mainMenu() {
        return List.of("Add animal", "List all animals", "Find animals by species", "List available animals", "Mark animal as adopted", "Exit");
    }
}
