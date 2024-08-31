package org.example;

import org.example.controller.RootController;
import org.example.service.AppBootFactoryService;
import org.example.service.AppBootService;
import org.example.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView();
        AppBootFactoryService factory = new AppBootFactoryService();
        AppBootService appBootService = new AppBootService(consoleView, factory);
        RootController rootController = new RootController(appBootService);
        rootController.run();
        consoleView.close();
    }
}