package com.exam.service;

import com.exam.model.ExamResult;
import com.exam.model.Question;
import com.exam.util.ExamTimer;

import java.util.List;


public class ExamService {

    // ─── Configuration ────────────────────────────────────────────────────────


    public static final int QUESTIONS_PER_EXAM = 10;


    public static final int EXAM_DURATION_SECONDS = 10 * 60;

    // ─── State ────────────────────────────────────────────────────────────────

    private final QuestionService questionService;

    private List<Question> currentQuestions; // Questions for the active attempt
    private int[]          userAnswers;       // Stores user's choice per question (0 = not answered)
    private ExamTimer      examTimer;         // Countdown timer
    private long           examStartTime;     // System time when exam began (ms)
    private boolean        examActive;        // Whether an attempt is in progress

    // ─── Constructor ──────────────────────────────────────────────────────────

    public ExamService(QuestionService questionService) {
        this.questionService = questionService;
    }

    // ─── Exam Lifecycle ───────────────────────────────────────────────────────


    public void startExam(Runnable onTimeUp) {
        currentQuestions = questionService.getShuffledQuestions(QUESTIONS_PER_EXAM);
        userAnswers      = new int[currentQuestions.size()]; // all 0 → unanswered
        examStartTime    = System.currentTimeMillis();
        examActive       = true;

        // Start the background timer; it will call onTimeUp when time expires
        examTimer = new ExamTimer(EXAM_DURATION_SECONDS, onTimeUp);
        examTimer.start();
    }


    public void recordAnswer(int questionIndex, int selectedOption) {
        if (questionIndex >= 0 && questionIndex < userAnswers.length) {
            userAnswers[questionIndex] = selectedOption;
        }
    }


    public ExamResult submitExam(String username) {
        examActive = false;
        if (examTimer != null) examTimer.stop();

        long timeTakenMs = System.currentTimeMillis() - examStartTime;
        long timeTakenSecs = timeTakenMs / 1000;

        int correct  = 0;
        int wrong    = 0;
        int skipped  = 0;

        for (int i = 0; i < currentQuestions.size(); i++) {
            int answer = userAnswers[i];
            if (answer == 0) {
                skipped++;
            } else if (currentQuestions.get(i).isCorrect(answer)) {
                correct++;
            } else {
                wrong++;
            }
        }

        return new ExamResult(username, currentQuestions.size(),
                              correct, wrong, skipped, timeTakenSecs);
    }

    // ─── Accessors ────────────────────────────────────────────────────────────

    /** Returns the current exam's question list. */
    public List<Question> getCurrentQuestions() { return currentQuestions; }

    /** Returns the remaining time in seconds (from the timer). */
    public long getRemainingSeconds() {
        return (examTimer != null) ? examTimer.getRemainingSeconds() : 0;
    }

    /** Returns {@code true} if an exam session is currently active. */
    public boolean isExamActive() { return examActive; }

    /** Marks the exam as inactive (called when timer auto-submits). */
    public void setExamInactive() { this.examActive = false; }
}
