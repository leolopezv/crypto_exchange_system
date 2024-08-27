package org.example.view;

import java.util.Scanner;

public class RegistrationView extends ConsoleView{
    /*
    RegistrationView class is a view to ask for the name, email and password of the user
     */
    private static final Scanner scanner = new Scanner(System.in);

    // Methods to get valuable user data
    public String getNameInput() {
        System.out.print("Enter your name: ");
        return scanner.nextLine();
    }

    public String getEmailInput() {
        System.out.print("Enter your email: ");
        return scanner.nextLine();
    }

    public String getPasswordInput() {
        System.out.print("Enter your password: ");
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }
}