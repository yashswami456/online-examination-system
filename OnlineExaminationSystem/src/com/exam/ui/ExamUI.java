package com.exam.ui;

import com.exam.model.ExamResult;
import com.exam.model.Question;
import com.exam.service.ExamService;
import com.exam.service.UserService;
import com.exam.util.ConsoleHelper;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;


public class ExamUI {

    private final ExamService    examService;
    private final UserService    userService;

    /** Becomes true when the timer fires so the question loop can exit. */
    private final AtomicBoolean timerExpired = new AtomicBoolean(false);

    public ExamUI(ExamService examService, UserService userService) {
        this.examService = examService;
        this.userService = userService;
    }

    // ─── Entry Point ──────────────────────────────────────────────────────────

    /** Shows the pre-exam confirmation screen and starts the session. */
    public void show() {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeader("EXAMINATION");

        System.out.println(ConsoleHelper.BOLD + "  Exam Details:" + ConsoleHelper.RESET);
        ConsoleHelper.printLine();
        System.out.println("  • Questions : " + ExamService.QUESTIONS_PER_EXAM + " MCQs");
        System.out.println("  • Duration  : " + (ExamService.EXAM_DURATION_SECONDS / 60) + " minutes");
        System.out.println("  • Scoring   : +1 per correct answer, 0 for wrong/skipped");
        System.out.println("  • Submit    : Type 's' at any question prompt to finish early.");
        ConsoleHelper.printLine();
        ConsoleHelper.printWarning("Once started, the timer cannot be paused!");
        ConsoleHelper.printLine();

        System.out.println("  [1]  Start Exam");
        System.out.println("  [2]  Back to Dashboard");
        ConsoleHelper.printLine();

        int choice = ConsoleHelper.readInt("Enter choice", 1, 2);
        if (choice == 2) return;

        runExam();
    }

    // ─── Exam Runner ──────────────────────────────────────────────────────────

    private void runExam() {
        timerExpired.set(false);

        // Start exam and register the timer-expiry callback
        examService.startExam(() -> {
            timerExpired.set(true);
            System.out.println(ConsoleHelper.RED + ConsoleHelper.BOLD
                    + "\n\n  ⏰  TIME'S UP! Your exam has been auto-submitted.\n"
                    + ConsoleHelper.RESET);
        });

        List<Question> questions = examService.getCurrentQuestions();
        Scanner sc = ConsoleHelper.getScanner();

        // ── Question loop ──────────────────────────────────────────────────
        for (int i = 0; i < questions.size(); i++) {

            // Exit loop if timer fired
            if (timerExpired.get()) break;

            // Check timer before displaying the question
            if (!examService.isExamActive() || examService.getRemainingSeconds() <= 0) break;

            Question q = questions.get(i);
            ConsoleHelper.clearScreen();
            printTimerBar();
            printQuestion(i + 1, questions.size(), q);

            // Read answer
            String input = sc.nextLine().trim().toLowerCase();

            // Handle early submission
            if (input.equals("s") || input.equals("submit")) {
                ConsoleHelper.printWarning("Submitting exam early...");
                break;
            }

            // Validate numeric input
            int answer = 0;
            try {
                answer = Integer.parseInt(input);
                if (answer < 0 || answer > 4) {
                    ConsoleHelper.printError("Enter 1–4 to answer, 0 to skip, or 's' to submit.");
                    i--; // Repeat same question
                    ConsoleHelper.pause();
                    continue;
                }
            } catch (NumberFormatException e) {
                ConsoleHelper.printError("Invalid input. Enter 1–4, 0 to skip, or 's' to submit.");
                i--; // Repeat same question
                ConsoleHelper.pause();
                continue;
            }

            examService.recordAnswer(i, answer);

            // Quick feedback
            if (answer == 0) {
                ConsoleHelper.printWarning("Question skipped.");
            } else if (q.isCorrect(answer)) {
                ConsoleHelper.printSuccess("Correct!");
            } else {
                ConsoleHelper.printError("Incorrect.");
            }

            // Short pause only on last question or feedback
            if (i < questions.size() - 1) {
                try { Thread.sleep(600); } catch (InterruptedException ignored) {}
            }
        }

        // If timer expired in mid-loop, wait briefly so the message is visible
        if (timerExpired.get()) {
            try { Thread.sleep(1500); } catch (InterruptedException ignored) {}
        }

        // ── Submit & show result ───────────────────────────────────────────
        ExamResult result = examService.submitExam(userService.getCurrentUser().getUsername());
        userService.recordScore(result.getCorrectAnswers());
        showResult(result, questions);
    }

    // ─── UI Helpers ───────────────────────────────────────────────────────────

    /** Prints the remaining-time header bar. */
    private void printTimerBar() {
        long remaining = examService.getRemainingSeconds();
        long mins = remaining / 60;
        long secs = remaining % 60;
        String timeStr = String.format("%02d:%02d", mins, secs);

        String colour = (remaining <= 60) ? ConsoleHelper.RED
                      : (remaining <= 180) ? ConsoleHelper.YELLOW
                      : ConsoleHelper.GREEN;

        System.out.println(ConsoleHelper.CYAN + "═".repeat(60) + ConsoleHelper.RESET);
        System.out.println(ConsoleHelper.BOLD + "  Online Examination System"
                + "                 ⏱  " + colour + timeStr + ConsoleHelper.RESET);
        System.out.println(ConsoleHelper.CYAN + "═".repeat(60) + ConsoleHelper.RESET);
    }

    /** Displays one question with its four numbered options. */
    private void printQuestion(int num, int total, Question q) {
        System.out.println();
        System.out.println(ConsoleHelper.BOLD + ConsoleHelper.WHITE
                + "  Q" + num + " of " + total + ":  " + q.getQuestionText()
                + ConsoleHelper.RESET);
        ConsoleHelper.printLine();

        String[] opts = q.getOptions();
        String[] labels = {"A", "B", "C", "D"};
        for (int i = 0; i < opts.length; i++) {
            System.out.println("  [" + (i + 1) + "]  " + labels[i] + ".  " + opts[i]);
        }

        ConsoleHelper.printLine();
        System.out.print(ConsoleHelper.YELLOW
                + "  Answer (1-4 | 0=skip | s=submit): "
                + ConsoleHelper.RESET);
    }

    /** Renders the full result report. */
    private void showResult(ExamResult result, List<Question> questions) {
        ConsoleHelper.clearScreen();
        ConsoleHelper.printHeader("EXAM RESULT");

        String gradeColour = result.isPassed() ? ConsoleHelper.GREEN : ConsoleHelper.RED;

        System.out.println(ConsoleHelper.BOLD + "  Candidate  : " + ConsoleHelper.RESET
                + result.getUsername());
        System.out.println(ConsoleHelper.BOLD + "  Date/Time  : " + ConsoleHelper.RESET
                + result.getFormattedDate());
        System.out.println(ConsoleHelper.BOLD + "  Time Taken : " + ConsoleHelper.RESET
                + result.getFormattedTimeTaken());

        ConsoleHelper.printLine();

        System.out.println(ConsoleHelper.BOLD + "  Total Questions : " + ConsoleHelper.RESET
                + result.getTotalQuestions());
        System.out.println(ConsoleHelper.GREEN + ConsoleHelper.BOLD
                + "  Correct         : " + ConsoleHelper.RESET + result.getCorrectAnswers());
        System.out.println(ConsoleHelper.RED + ConsoleHelper.BOLD
                + "  Wrong           : " + ConsoleHelper.RESET + result.getWrongAnswers());
        System.out.println(ConsoleHelper.YELLOW + ConsoleHelper.BOLD
                + "  Skipped         : " + ConsoleHelper.RESET + result.getSkippedAnswers());

        ConsoleHelper.printLine();

        System.out.printf("  Score      :  %s%s%d / %d (%.1f%%)%s%n",
                ConsoleHelper.BOLD, gradeColour,
                result.getCorrectAnswers(), result.getTotalQuestions(),
                result.getPercentage(), ConsoleHelper.RESET);

        System.out.printf("  Grade      :  %s%s %s %s%n",
                ConsoleHelper.BOLD, gradeColour, result.getGrade(), ConsoleHelper.RESET);

        System.out.printf("  Status     :  %s%s%s%s%n",
                ConsoleHelper.BOLD,
                result.isPassed() ? ConsoleHelper.GREEN : ConsoleHelper.RED,
                result.isPassed() ? "PASSED ✔" : "FAILED ✘",
                ConsoleHelper.RESET);

        ConsoleHelper.printLine();

        // Show correct answers for review
        System.out.println(ConsoleHelper.BOLD + "  ANSWER KEY:" + ConsoleHelper.RESET);
        ConsoleHelper.printLine();
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            int correct = q.getCorrectOption();
            System.out.printf("  Q%-2d  Correct: [%d] %s%n",
                    i + 1, correct, q.getOptions()[correct - 1]);
        }

        ConsoleHelper.printDoubleLine();

        if (result.isPassed()) {
            ConsoleHelper.printSuccess("Congratulations! You passed the exam.");
        } else {
            ConsoleHelper.printWarning("You did not pass. Review the topics and try again!");
        }

        ConsoleHelper.pause();
    }
}
