package org.example.factory;

import org.example.model.*;

public class AnimalFactory {
    public static Animal createAnimal(String animal, String name, int age) {

        return switch (animal) {
            case "Dog" -> new Dog(new AnimalId(), name, age);
            case "Cat" -> new Cat(new AnimalId(), name, age);
            case "Bird" -> new Bird(new AnimalId(), name, age);
            case "Snake" -> new Snake(new AnimalId(), name, age);
            default -> throw new IllegalStateException("Unexpected value: " + animal);
        };
    }
}
