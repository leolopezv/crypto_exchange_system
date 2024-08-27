package org.example;

import org.example.controller.RootController;
import org.example.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView();
        RootController rootController = new RootController(consoleView);
        rootController.run();
        consoleView.close();
    }
}