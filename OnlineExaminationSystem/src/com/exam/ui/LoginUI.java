package com.exam.ui;

import com.exam.model.User;
import com.exam.service.UserService;
import com.exam.util.ConsoleHelper;


public class LoginUI {

    private final UserService userService;

    public LoginUI(UserService userService) {
        this.userService = userService;
    }

    // ─── Entry Point ──────────────────────────────────────────────────────────


    public User show() {
        while (true) {
            ConsoleHelper.clearScreen();
            showWelcomeBanner();
            showMenu();

            int choice = ConsoleHelper.readInt("Enter choice", 1, 3);

            switch (choice) {
                case 1 -> {
                    User user = handleLogin();
                    if (user != null) return user;
                }
                case 2 -> handleRegister();
                case 3 -> {
                    System.out.println(ConsoleHelper.YELLOW
                            + "\nThank you for using Online Examination System. Goodbye!\n"
                            + ConsoleHelper.RESET);
                    System.exit(0);
                }
            }
        }
    }

    // ─── Private Handlers ─────────────────────────────────────────────────────

    private void showWelcomeBanner() {
        System.out.println(ConsoleHelper.CYAN + ConsoleHelper.BOLD);
        System.out.println("  ╔══════════════════════════════════════════════════════════╗");
        System.out.println("  ║         ONLINE EXAMINATION SYSTEM  v1.0                 ║");
        System.out.println("  ║         Built with Java  |  OOP Architecture            ║");
        System.out.println("  ╚══════════════════════════════════════════════════════════╝");
        System.out.println(ConsoleHelper.RESET);
    }

    private void showMenu() {
        ConsoleHelper.printHeader("WELCOME");
        System.out.println("  [1]  Login");
        System.out.println("  [2]  Register New Account");
        System.out.println("  [3]  Exit");
        ConsoleHelper.printLine();
    }

    /** Prompts for credentials and attempts login. */
    private User handleLogin() {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeader("USER LOGIN");

        String username = ConsoleHelper.readString("Username");
        String password = ConsoleHelper.readPassword("Password");

        User user = userService.login(username, password);
        if (user != null) {
            ConsoleHelper.printSuccess("Login successful! Welcome, " + user.getFullName() + "!");
            ConsoleHelper.pause();
            return user;
        } else {
            ConsoleHelper.printError("Invalid username or password. Please try again.");
            ConsoleHelper.pause();
            return null;
        }
    }


    private void handleRegister() {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeader("NEW USER REGISTRATION");

        ConsoleHelper.printInfo("Demo accounts available: admin / admin123  |  student / student123");
        ConsoleHelper.printLine();

        String username = ConsoleHelper.readString("Choose Username");
        String password = ConsoleHelper.readPassword("Choose Password");
        String fullName = ConsoleHelper.readString("Full Name");
        String email    = ConsoleHelper.readString("Email Address");
        String phone    = ConsoleHelper.readString("Phone Number");

        boolean success = userService.register(username, password, fullName, email, phone);
        if (success) {
            ConsoleHelper.printSuccess("Account created successfully! Please log in.");
        } else {
            ConsoleHelper.printError("Username '" + username + "' is already taken. Choose another.");
        }
        ConsoleHelper.pause();
    }
}
