package com.exam.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class ExamResult {

    private String        username;
    private int           totalQuestions;
    private int           correctAnswers;
    private int           wrongAnswers;
    private int           skippedAnswers;
    private double        percentage;
    private String        grade;
    private LocalDateTime attemptedAt;
    private long          timeTakenSeconds; // How many seconds the user actually used

    // ─── Constructor ──────────────────────────────────────────────────────────

    public ExamResult(String username, int totalQuestions, int correctAnswers,
                      int wrongAnswers, int skippedAnswers, long timeTakenSeconds) {
        this.username        = username;
        this.totalQuestions  = totalQuestions;
        this.correctAnswers  = correctAnswers;
        this.wrongAnswers    = wrongAnswers;
        this.skippedAnswers  = skippedAnswers;
        this.timeTakenSeconds = timeTakenSeconds;
        this.attemptedAt     = LocalDateTime.now();

        // Calculate percentage and grade
        this.percentage = (totalQuestions > 0)
                ? ((double) correctAnswers / totalQuestions) * 100
                : 0;
        this.grade = calculateGrade(this.percentage);
    }

    // ─── Grade Logic ──────────────────────────────────────────────────────────


    private String calculateGrade(double pct) {
        if (pct >= 90) return "A+";
        if (pct >= 80) return "A";
        if (pct >= 70) return "B";
        if (pct >= 60) return "C";
        if (pct >= 50) return "D";
        return "F";
    }

    // ─── Getters ──────────────────────────────────────────────────────────────

    public String   getUsername()          { return username;          }
    public int      getTotalQuestions()    { return totalQuestions;    }
    public int      getCorrectAnswers()    { return correctAnswers;    }
    public int      getWrongAnswers()      { return wrongAnswers;       }
    public int      getSkippedAnswers()    { return skippedAnswers;    }
    public double   getPercentage()        { return percentage;        }
    public String   getGrade()            { return grade;             }
    public long     getTimeTakenSeconds() { return timeTakenSeconds;  }


    public String getFormattedDate() {
        return attemptedAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    }


    public String getFormattedTimeTaken() {
        long mins = timeTakenSeconds / 60;
        long secs = timeTakenSeconds % 60;
        return String.format("%02d:%02d", mins, secs);
    }


    public boolean isPassed() {
        return !grade.equals("F");
    }

    @Override
    public String toString() {
        return String.format("ExamResult{user='%s', score=%d/%d (%.1f%%), grade='%s'}",
                username, correctAnswers, totalQuestions, percentage, grade);
    }
}
