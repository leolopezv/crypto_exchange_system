package org.example.view;

public class MenuView {
    public void showLoginMenu(ConsoleView consoleView) {
        consoleView.showMessage("=== Welcome to Leonardo's Exchange! Select: ===");
        consoleView.showMessage("1. Create new account\t2. Login to profile");
        consoleView.showMessage("=================== 3. Quit ===================");
    }

    public void showExMenu(ConsoleView consoleView, String userName) {
        consoleView.showMessage("========= " + userName + ", enter an option: =========");
        consoleView.showMessage("1. Deposit fiat money \t4. Place Buy Order");
        consoleView.showMessage("2. View wallet balance\t5. Place Sell Order");
        consoleView.showMessage("3. Buy Exchange crypto\t6. View Past Transactions");
        consoleView.showMessage("=================== 7. Log out ===================");
    }
}