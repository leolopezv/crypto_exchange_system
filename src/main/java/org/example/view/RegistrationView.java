package org.example.view;

public class RegistrationView extends ConsoleView{

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
}