import java.util.Scanner;

class BankAccount {

    private double balance;
    private final String pin;

    public BankAccount(double initialBalance, String pin) {
        this.balance = initialBalance;
        this.pin = pin;
    }

    public boolean verifyPin(String enteredPin) {
        return pin.equals(enteredPin);
    }

    public double getBalance() {
        return balance;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            return false;
        }

        balance += amount;
        return true;
    }

    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            return false;
        }

        balance -= amount;
        return true;
    }
}

class ATM {

    private final BankAccount account;
    private final Scanner scanner;

    public ATM(BankAccount account, Scanner scanner) {
        this.account = account;
        this.scanner = scanner;
    }

    public void start() {

        System.out.println("=====================================");
        System.out.println("            ATM INTERFACE");
        System.out.println("=====================================");

        if (!login()) {
            System.out.println("\nToo many incorrect attempts.");
            System.out.println("Access denied.");
            return;
        }

        boolean running = true;

        while (running) {

            displayMenu();

            System.out.print("Enter your choice: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    checkBalance();
                    break;

                case 2:
                    depositMoney();
                    break;

                case 3:
                    withdrawMoney();
                    break;

                case 4:
                    running = false;
                    System.out.println("\nThank you for using the ATM.");
                    break;

                default:
                    System.out.println(
                        "Invalid choice! Please select an option from 1 to 4."
                    );
            }
        }
    }

    private boolean login() {

        final int MAX_ATTEMPTS = 3;

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {

            System.out.print("Enter your PIN: ");
            String enteredPin = scanner.next();

            if (account.verifyPin(enteredPin)) {
                System.out.println("\nLogin successful!");
                return true;
            }

            System.out.println(
                "Incorrect PIN. Attempts remaining: "
                + (MAX_ATTEMPTS - attempt)
            );
        }

        return false;
    }

    private void displayMenu() {

        System.out.println("\n=====================================");
        System.out.println("              ATM MENU");
        System.out.println("=====================================");
        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");
        System.out.println("=====================================");
    }

    private void checkBalance() {

        System.out.printf(
            "Current Balance: ₹%.2f%n",
            account.getBalance()
        );
    }

    private void depositMoney() {

        System.out.print("Enter deposit amount: ₹");

        if (!scanner.hasNextDouble()) {
            System.out.println("Invalid amount! Please enter a number.");
            scanner.next();
            return;
        }

        double amount = scanner.nextDouble();

        if (account.deposit(amount)) {
            System.out.printf(
                "Deposit successful!%nNew Balance: ₹%.2f%n",
                account.getBalance()
            );
        } else {
            System.out.println(
                "Invalid deposit amount! Amount must be greater than 0."
            );
        }
    }

    private void withdrawMoney() {

        System.out.print("Enter withdrawal amount: ₹");

        if (!scanner.hasNextDouble()) {
            System.out.println("Invalid amount! Please enter a number.");
            scanner.next();
            return;
        }

        double amount = scanner.nextDouble();

        if (amount <= 0) {
            System.out.println(
                "Invalid withdrawal amount! Amount must be greater than 0."
            );
        } else if (amount > account.getBalance()) {
            System.out.println("Insufficient balance!");
        } else if (account.withdraw(amount)) {
            System.out.printf(
                "Withdrawal successful!%nNew Balance: ₹%.2f%n",
                account.getBalance()
            );
        }
    }
}

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // Demo account for this project
        BankAccount account = new BankAccount(5000.00, "1234");

        ATM atm = new ATM(account, scanner);

        atm.start();

        scanner.close();
    }
}