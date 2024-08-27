package org.example.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleView {
    /*
    ConsoleView is the main view of the MVC architecture and uses color to distinguish status
     */
    private static final String RED = "\u001B[31m";
    private static final String GRAY = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";

    private final Scanner scanner = new Scanner(System.in);
    private static final int INVALID= -1;

    // The user may select a number
    public int getUserChoice() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return INVALID;
        }
    }

    // Red color for error status
    public void showError(String error) {
        System.out.println(RED + error + GRAY);
    }

    // Green color for success status
    public void showSuccess(String success) {
        System.out.println(GREEN + success + GRAY);
    }

    // Blue color for general messages
    public void showMessage(String message) {
        System.out.println(BLUE + message + GRAY);
    }
}
