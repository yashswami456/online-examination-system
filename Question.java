package com.exam.model;


public class Question {

    private String   questionText;
    private String[] options;        // Four answer choices  (index 0-3)
    private int      correctOption;  // 1-based index of the correct option

    // ─── Constructor ──────────────────────────────────────────────────────────
    public Question(String questionText, String[] options, int correctOption) {
        if (options == null || options.length != 4) {
            throw new IllegalArgumentException("Each question must have exactly 4 options.");
        }
        if (correctOption < 1 || correctOption > 4) {
            throw new IllegalArgumentException("correctOption must be between 1 and 4.");
        }
        this.questionText  = questionText;
        this.options       = options;
        this.correctOption = correctOption;
    }

    // ─── Getters ──────────────────────────────────────────────────────────────

    public String   getQuestionText()  { return questionText;  }
    public String[] getOptions()       { return options;       }
    public int      getCorrectOption() { return correctOption; }


    public boolean isCorrect(int selectedOption) {
        return selectedOption == correctOption;
    }

    @Override
    public String toString() {
        return "Question{text='" + questionText + "', correct=" + correctOption + "}";
    }
}
