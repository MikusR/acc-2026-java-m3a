package org.example.menu;

import org.example.model.Animal;
import org.example.shelter.Shelter;

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
            System.out.println(choice);
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
