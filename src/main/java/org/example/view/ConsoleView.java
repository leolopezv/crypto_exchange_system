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

    // The user may select a number
    public int getUserChoice() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return INVALID_CHOICE;
        }
    }

    // Red color for error messages
    public void showError(String error) {
        System.out.println(ANSI_RED + error + ANSI_RESET);
    }

    // Green color for success messages
    public void showSuccess(String message) {
        System.out.println(ANSI_GREEN + message + ANSI_RESET);
    }

    // Blue color for general messages
    public void showMessage(String message) {
        System.out.println(ANSI_BLUE + message + ANSI_RESET);
    }
}
