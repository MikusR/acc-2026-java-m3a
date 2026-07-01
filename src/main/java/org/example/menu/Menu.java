package org.example.menu;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Menu {
    private final List<MenuOption> options = new ArrayList<>();

    public Menu(List<MenuOption> options) {
        this.options.addAll(options);
    }
}
