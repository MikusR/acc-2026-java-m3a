package org.example.model;

import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class AdoptionEvent {
    private Animal animal;
    private LocalDate date;
    private String name;

    public String toString() {
        return animal + " | " + date + " | " + name;
    }
}

