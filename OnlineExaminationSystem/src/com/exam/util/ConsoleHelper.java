package com.exam.util;

import java.util.Scanner;


public class ConsoleHelper {

    // ─── ANSI Colour Codes ────────────────────────────────────────────────────

    public static final String RESET   = "\u001B[0m";
    public static final String RED     = "\u001B[31m";
    public static final String GREEN   = "\u001B[32m";
    public static final String YELLOW  = "\u001B[33m";
    public static final String BLUE    = "\u001B[34m";
    public static final String MAGENTA = "\u001B[35m";
    public static final String CYAN    = "\u001B[36m";
    public static final String WHITE   = "\u001B[37m";
    public static final String BOLD    = "\u001B[1m";


    private static final Scanner scanner = new Scanner(System.in);

    private ConsoleHelper() { /* Utility class — no instances */ }


    public static void printDoubleLine() {
        System.out.println(CYAN + "═".repeat(60) + RESET);
    }


    public static void printLine() {
        System.out.println(CYAN + "─".repeat(60) + RESET);
    }


    public static void printHeader(String title) {
        printDoubleLine();
        int padding = (60 - title.length()) / 2;
        String spaces = " ".repeat(Math.max(0, padding));
        System.out.println(BOLD + BLUE + spaces + title + RESET);
        printDoubleLine();
    }

    public static void printSuccess(String message) {
        System.out.println(GREEN + "✔  " + message + RESET);
    }


    public static void printError(String message) {
        System.out.println(RED + "✘  " + message + RESET);
    }


    public static void printWarning(String message) {
        System.out.println(YELLOW + "⚠  " + message + RESET);
    }


    public static void printInfo(String message) {
        System.out.println(CYAN + "ℹ  " + message + RESET);
    }


    public static void printPrompt(String label) {
        System.out.print(YELLOW + label + " → " + RESET);
    }

    // ─── Input Helpers ────────────────────────────────────────────────────────


    public static String readString(String prompt) {
        while (true) {
            printPrompt(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isBlank()) return input;
            printError("Input cannot be empty. Please try again.");
        }
    }


    public static String readPassword(String prompt) {
        printPrompt(prompt);
        return scanner.nextLine().trim();
    }


    public static int readInt(String prompt, int min, int max) {
        while (true) {
            printPrompt(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) return value;
                printError("Please enter a number between " + min + " and " + max + ".");
            } catch (NumberFormatException e) {
                printError("Invalid input. Please enter a valid number.");
            }
        }
    }


    public static void pause() {
        System.out.print(CYAN + "\nPress Enter to continue..." + RESET);
        scanner.nextLine();
    }


    public static void clearScreen() {
        for (int i = 0; i < 3; i++) System.out.println();
    }


    public static Scanner getScanner() { return scanner; }
}
