package org.example.menu;

import org.example.model.AdoptionStatus;
import org.example.model.Animal;
import org.example.model.AnimalId;
import org.example.model.Bird;
import org.example.shelter.Shelter;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final Shelter<Animal> shelter;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleMenu(Shelter<Animal> shelter) {
        this.shelter = shelter;
    }

    public void start() {
        // TODO:
        // Show menu in a loop
        // Read user input
        // Call correct 'Shelter' methods based on selected option

        do {
            System.out.println("+".repeat(30));
            printMenu();
            if (!scanner.hasNextInt()) {
                // scanner.next() needs to be here, as the hasNextInt() keeps the wrong input
                scanner.next();
                continue;
            }
            int choice = scanner.nextInt();
            if (choice == 0) break;
            // System.out.println(choice);

            switch (choice) {
                case 1:
                    Animal bird3 = new Bird(new AnimalId(), "Bird 3", 1);
                    bird3.setAdoptionStatus(AdoptionStatus.ADOPTED);
                    shelter.addAnimal(bird3);
                    break;
                case 2:

                    for (Animal animal : shelter.getAllAnimals()) {
                        System.out.println(animal);
                    }
                    break;
                case 3:
                    System.out.println("Choose species");
                    List<String> species = shelter.getSpecies();
                    for (int i = 0; i < species.size(); i++) {
                        System.out.println(i + 1 + " | " + shelter.getSpecies().get(i));
                    }
                    int speciesChoice = scanner.nextInt();

                    System.out.println();
                    for (Animal animal : shelter.findBySpecies(species.get(speciesChoice - 1))) {
                        System.out.println(animal);
                    }

                    break;
                case 4:
                    for (Animal animal : shelter.findAvailableAnimals()) {
                        System.out.println(animal);
                    }
                    break;
                case 5:
                    System.out.println("Choose animal");
                    List<Animal> availableAnimals = shelter.findAvailableAnimals();
                    for (int i = 0; i < availableAnimals.size(); i++) {
                        System.out.println(i + 1 + " | " + availableAnimals.get(i).toString());
                    }
                    int animal = scanner.nextInt();
                    shelter.markAsAdopted(availableAnimals.get(animal - 1).getId().toString());
                    break;
                default:
                    break;
            }
        } while (true);

        scanner.close();

    }

    private void printMenu() {
        System.out.println("""
                1. Add animal
                2. List all animals
                3. Find animals by species
                4. List available animals
                5. Mark animal as adopted
                0. Exit
                """);
    }
}
