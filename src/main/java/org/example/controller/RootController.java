package org.example.controller;

import org.example.service.AppBootService;

public class RootController {
    private final MenuController menuController;

    public RootController(AppBootService appBootService) {
        this.menuController = appBootService.getMenuController();
    }

    public void run() {
        while (true) {
            menuController.displayMenu();
        }
    }
}
