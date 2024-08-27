package org.example.view;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ConsoleView {
    private static final String RED = "\u001B[31m";
    private static final String GRAY = "\u001B[0m";
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";

    protected final Scanner scanner = new Scanner(System.in);
    private static final int INVALID= -1;

    public int getUserChoice() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine();
            return INVALID;
        }
    }

    public void showError(String error) {
        System.out.println(RED + error + GRAY);
    }

    public void showSuccess(String success) {
        System.out.println(GREEN + success + GRAY);
    }

    public void showMessage(String message) {
        System.out.println(BLUE + message + GRAY);
    }

    public void close() {
        scanner.close();
    }
}
