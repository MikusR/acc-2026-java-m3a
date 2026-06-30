package org.example.shelter;

import org.example.model.AdoptionStatus;
import org.example.model.Animal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Shelter<T extends Animal> {
    private final List<T> animals = new ArrayList<>();

    public void addAnimal(T animal) {
        animals.add(animal);
    }

    public List<String> getSpecies() {
        Set<String> speciesSet = new HashSet<>();
        for (var species : Animal.class.getPermittedSubclasses()) {
            speciesSet.add(species.getSimpleName());
        }
        return new ArrayList<String>(speciesSet);
    }

    public List<T> getAllAnimals() {

        return animals;
    }

    public List<T> findBySpecies(String species) {
        List<T> animalsBySpecies = new ArrayList<>();
        for (T animal : animals) {
            if (animal.getSpecies().equals(species)) animalsBySpecies.add(animal);
        }

        return animalsBySpecies;
    }

    public List<T> findAvailableAnimals() {
        List<T> availableAnimals = new ArrayList<>();
        for (T animal : animals) {
            if (animal.getAdoptionStatus().equals(AdoptionStatus.AVAILABLE)) availableAnimals.add(animal);
        }
        return availableAnimals;
    }

    public Animal getByID(String id) {
        for (T animal : animals) {
            if (animal.getId().toString().equals(id)) return animal;
        }
        return null;
    }

    public void markAsAdopted(String id) {
        Animal animal = this.getByID(id);
        if (animal == null) return;
        animal.setAdoptionStatus(AdoptionStatus.ADOPTED);
    }
}
