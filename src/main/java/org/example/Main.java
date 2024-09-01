package org.example;

import org.example.controller.RootController;
import org.example.service.AppFactory;
import org.example.service.AppService;
import org.example.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        ConsoleView consoleView = new ConsoleView();
        AppFactory factory = new AppFactory();
        AppService appService = new AppService(consoleView, factory);
        RootController rootController = appService.getRootController();
        rootController.run();
        consoleView.close();
    }
}