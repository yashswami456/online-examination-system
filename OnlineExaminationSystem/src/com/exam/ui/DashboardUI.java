package com.exam.ui;

import com.exam.model.User;
import com.exam.service.ExamService;
import com.exam.service.UserService;
import com.exam.util.ConsoleHelper;


public class DashboardUI {

    private final UserService    userService;
    private final ExamUI         examUI;
    private final ProfileUI      profileUI;

    public DashboardUI(UserService userService, ExamService examService) {
        this.userService = userService;
        this.examUI      = new ExamUI(examService, userService);
        this.profileUI   = new ProfileUI(userService);
    }

    // ─── Entry Point ──────────────────────────────────────────────────────────

    public void show() {
        while (true) {
            ConsoleHelper.clearScreen();
            User user = userService.getCurrentUser();
            printDashboard(user);

            int choice = ConsoleHelper.readInt("Enter choice", 1, 4);

            switch (choice) {
                case 1 -> examUI.show();
                case 2 -> profileUI.show();
                case 3 -> showHelp();
                case 4 -> {
                    handleLogout();
                    return; // Return to the login screen
                }
            }
        }
    }

    // ─── Private Helpers ──────────────────────────────────────────────────────

    private void printDashboard(User user) {
        ConsoleHelper.printHeader("STUDENT DASHBOARD");

        // User summary card
        System.out.println(ConsoleHelper.BOLD + ConsoleHelper.CYAN
                + "  Logged in as : " + ConsoleHelper.RESET
                + ConsoleHelper.WHITE + user.getFullName() + ConsoleHelper.RESET);
        System.out.println(ConsoleHelper.BOLD + ConsoleHelper.CYAN
                + "  Username     : " + ConsoleHelper.RESET + user.getUsername());
        System.out.println(ConsoleHelper.BOLD + ConsoleHelper.CYAN
                + "  Exams Taken  : " + ConsoleHelper.RESET + user.getExamsTaken());
        System.out.println(ConsoleHelper.BOLD + ConsoleHelper.CYAN
                + "  Last Score   : " + ConsoleHelper.RESET
                + (user.getExamsTaken() > 0
                   ? user.getLastScore() + " / " + ExamService.QUESTIONS_PER_EXAM
                   : "N/A"));

        ConsoleHelper.printLine();
        System.out.println(ConsoleHelper.YELLOW + ConsoleHelper.BOLD + "  MAIN MENU" + ConsoleHelper.RESET);
        ConsoleHelper.printLine();
        System.out.println("  [1]  Start Exam");
        System.out.println("  [2]  My Profile");
        System.out.println("  [3]  Help / Instructions");
        System.out.println("  [4]  Logout");
        ConsoleHelper.printLine();
    }

    private void handleLogout() {
        String name = userService.getCurrentUser().getFullName();
        userService.logout();
        ConsoleHelper.printSuccess("Logged out successfully. Goodbye, " + name + "!");
        ConsoleHelper.pause();
    }

    private void showHelp() {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeader("HELP & INSTRUCTIONS");

        System.out.println(ConsoleHelper.BOLD + "  HOW TO TAKE AN EXAM" + ConsoleHelper.RESET);
        ConsoleHelper.printLine();
        System.out.println("  1. Select 'Start Exam' from the dashboard.");
        System.out.println("  2. You will have " + (ExamService.EXAM_DURATION_SECONDS / 60)
                + " minutes to complete " + ExamService.QUESTIONS_PER_EXAM + " MCQ questions.");
        System.out.println("  3. Each question has 4 options. Enter the option number (1–4).");
        System.out.println("  4. Enter 0 to skip a question and come back to it later.");
        System.out.println("  5. Enter 's' or 'submit' at the question prompt to end early.");
        System.out.println("  6. The exam auto-submits when the timer reaches 00:00.");

        ConsoleHelper.printLine();
        System.out.println(ConsoleHelper.BOLD + "  GRADING SCHEME" + ConsoleHelper.RESET);
        ConsoleHelper.printLine();
        System.out.println("  A+  →  90% and above");
        System.out.println("  A   →  80% – 89%");
        System.out.println("  B   →  70% – 79%");
        System.out.println("  C   →  60% – 69%");
        System.out.println("  D   →  50% – 59%");
        System.out.println("  F   →  Below 50%");

        ConsoleHelper.printLine();
        System.out.println(ConsoleHelper.BOLD + "  SCORING" + ConsoleHelper.RESET);
        ConsoleHelper.printLine();
        System.out.println("  • 1 mark for each correct answer.");
        System.out.println("  • 0 marks for wrong or skipped answers (no negative marking).");

        ConsoleHelper.pause();
    }
}
