package org.example.view;

public class MenuViews {
    public void showWelcomeMenu(ConsoleView consoleView) {
        consoleView.showMessage("Welcome to Leonardo's Crypto Exchange System:");
        consoleView.showMessage("1. Register");
        consoleView.showMessage("2. Login");
        consoleView.showMessage("3. Quit");
        consoleView.showMessage("Enter an option to get into the club: ");
    }

    public void showExchangingMenu(ConsoleView consoleView, String userName) {
        consoleView.showMessage("Welcome back " + userName + "!");
        consoleView.showMessage("1. Deposit money to your wallet");
        consoleView.showMessage("2. View wallet balance");
        consoleView.showMessage("3. Soon");
        consoleView.showMessage("4. Soon");
        consoleView.showMessage("5. Log out");
        consoleView.showMessage("Enter an option to start exchanging: ");
    }
}
