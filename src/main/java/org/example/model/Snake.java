package org.example.model;

public final class Snake extends Animal {
    public Snake(AnimalId id, String name, int age) {
        super(id, name, age);
    }

    @Override
    public String getSpecies() {
        return "Snake";
    }
}
