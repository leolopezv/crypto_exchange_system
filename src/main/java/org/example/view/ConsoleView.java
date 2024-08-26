package org.example.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleView {
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";

    private final Scanner scanner = new Scanner(System.in);
    private static final int INVALID_CHOICE = -1;

    public int getUserChoice() {
        System.out.println("Welcome to Leonardo's Crypto Exchange System:");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Quit");
        System.out.print("Enter your choice: ");
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return INVALID_CHOICE;
        }
    }

    public void showError(String error) {
        System.out.println(ANSI_RED + error + ANSI_RESET);
    }

    public void showSuccess(String message) {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
    }
}
