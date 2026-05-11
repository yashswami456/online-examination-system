package com.exam;

import com.exam.model.User;
import com.exam.service.ExamService;
import com.exam.service.QuestionService;
import com.exam.service.UserService;
import com.exam.ui.DashboardUI;
import com.exam.ui.LoginUI;
import com.exam.util.ConsoleHelper;


public class Main {

    public static void main(String[] args) {

        // ── Service layer (application singletons) ────────────────────────────
        UserService     userService     = new UserService();
        QuestionService questionService = new QuestionService();
        ExamService     examService     = new ExamService(questionService);

        // ── UI layer ──────────────────────────────────────────────────────────
        LoginUI     loginUI     = new LoginUI(userService);
        DashboardUI dashboardUI = new DashboardUI(userService, examService);

        // ── Main loop: login → dashboard → logout → login … ──────────────────
        while (true) {
            try {
                // Block here until the user authenticates successfully
                User user = loginUI.show();

                if (user != null) {
                    // Show dashboard; returns when the user chooses Logout
                    dashboardUI.show();
                }
            } catch (Exception e) {
                // Graceful top-level error handling — keeps the app running
                ConsoleHelper.printError("An unexpected error occurred: " + e.getMessage());
                ConsoleHelper.printInfo("Returning to the login screen...");
                ConsoleHelper.pause();
            }
        }
    }
}
