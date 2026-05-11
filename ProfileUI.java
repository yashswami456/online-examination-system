package com.exam.ui;

import com.exam.model.User;
import com.exam.service.UserService;
import com.exam.util.ConsoleHelper;


public class ProfileUI {

    private final UserService userService;

    public ProfileUI(UserService userService) {
        this.userService = userService;
    }

    // ─── Entry Point ──────────────────────────────────────────────────────────

    /** Shows the profile menu and loops until the user returns to the dashboard. */
    public void show() {
        while (true) {
            ConsoleHelper.clearScreen();
            ConsoleHelper.printHeader("MY PROFILE");

            displayProfile();

            ConsoleHelper.printLine();
            System.out.println("  [1]  Update Profile Info");
            System.out.println("  [2]  Change Password");
            System.out.println("  [3]  Back to Dashboard");
            ConsoleHelper.printLine();

            int choice = ConsoleHelper.readInt("Enter choice", 1, 3);

            switch (choice) {
                case 1 -> handleUpdateProfile();
                case 2 -> handleChangePassword();
                case 3 -> { return; }
            }
        }
    }

    // ─── Private Handlers ─────────────────────────────────────────────────────

    /** Displays a formatted profile card for the current user. */
    private void displayProfile() {
        User user = userService.getCurrentUser();

        ConsoleHelper.printLine();
        printField("Username",    user.getUsername());
        printField("Full Name",   user.getFullName());
        printField("Email",       user.getEmail());
        printField("Phone",       user.getPhone());
        printField("Exams Taken", String.valueOf(user.getExamsTaken()));
        printField("Last Score",  user.getExamsTaken() > 0
                                  ? user.getLastScore() + " correct" : "No exams yet");
        ConsoleHelper.printLine();
    }

    private void printField(String label, String value) {
        System.out.printf("  %s%-14s%s : %s%n",
                ConsoleHelper.CYAN + ConsoleHelper.BOLD,
                label,
                ConsoleHelper.RESET,
                value);
    }

    /** Prompts for updated profile fields (blank input keeps current value). */
    private void handleUpdateProfile() {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeader("UPDATE PROFILE");

        User user = userService.getCurrentUser();

        ConsoleHelper.printInfo("Press Enter to keep the current value for any field.");
        ConsoleHelper.printLine();

        System.out.println("  Current Full Name : " + user.getFullName());
        System.out.print(ConsoleHelper.YELLOW + "  New Full Name     → " + ConsoleHelper.RESET);
        String fullName = ConsoleHelper.getScanner().nextLine().trim();

        System.out.println("  Current Email     : " + user.getEmail());
        System.out.print(ConsoleHelper.YELLOW + "  New Email         → " + ConsoleHelper.RESET);
        String email = ConsoleHelper.getScanner().nextLine().trim();

        System.out.println("  Current Phone     : " + user.getPhone());
        System.out.print(ConsoleHelper.YELLOW + "  New Phone         → " + ConsoleHelper.RESET);
        String phone = ConsoleHelper.getScanner().nextLine().trim();

        // Pass null for fields the user left blank so the service won't overwrite them
        boolean updated = userService.updateProfile(
                fullName.isBlank() ? null : fullName,
                email.isBlank()    ? null : email,
                phone.isBlank()    ? null : phone);

        if (updated) {
            ConsoleHelper.printSuccess("Profile updated successfully!");
        } else {
            ConsoleHelper.printError("Profile update failed. Please log in again.");
        }
        ConsoleHelper.pause();
    }

    /** Guides the user through changing their password. */
    private void handleChangePassword() {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeader("CHANGE PASSWORD");

        String oldPassword = ConsoleHelper.readPassword("Current Password");
        String newPassword = ConsoleHelper.readPassword("New Password");
        String confirm     = ConsoleHelper.readPassword("Confirm New Password");

        if (newPassword.isBlank()) {
            ConsoleHelper.printError("New password cannot be blank.");
            ConsoleHelper.pause();
            return;
        }

        if (!newPassword.equals(confirm)) {
            ConsoleHelper.printError("New passwords do not match. Please try again.");
            ConsoleHelper.pause();
            return;
        }

        if (newPassword.equals(oldPassword)) {
            ConsoleHelper.printError("New password must differ from your current password.");
            ConsoleHelper.pause();
            return;
        }

        if (newPassword.length() < 6) {
            ConsoleHelper.printError("Password must be at least 6 characters long.");
            ConsoleHelper.pause();
            return;
        }

        boolean changed = userService.changePassword(oldPassword, newPassword);
        if (changed) {
            ConsoleHelper.printSuccess("Password changed successfully!");
        } else {
            ConsoleHelper.printError("Current password is incorrect.");
        }
        ConsoleHelper.pause();
    }
}
