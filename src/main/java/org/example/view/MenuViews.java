package org.example.view;

public class MenuViews {
    public void showWelcomeMenu(ConsoleView consoleView) {
        consoleView.showMessage("Welcome to Leonardo's Crypto Exchange:");
        consoleView.showMessage("1. Register");
        consoleView.showMessage("2. Login");
        consoleView.showMessage("3. Quit");
        consoleView.showMessage("Enter an option to get into the club: ");
    }

    public void showExchangingMenu(ConsoleView consoleView, String userName) {
        consoleView.showMessage(userName + ", enter an option:");
        consoleView.showMessage("1. Deposit money");
        consoleView.showMessage("2. View wallet balance");
        consoleView.showMessage("3. Buy crypto from the Exchange");
        consoleView.showMessage("4. Soon");
        consoleView.showMessage("5. Log out");
    }
}