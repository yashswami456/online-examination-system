package com.exam.model;


public class User {

    private String username;
    private String password;
    private String fullName;
    private String email;
    private String phone;
    private int lastScore;       // Score from the most recent exam
    private int examsTaken;      // Total number of exams taken

    // ─── Constructor ──────────────────────────────────────────────────────────

    public User(String username, String password, String fullName, String email, String phone) {
        this.username   = username;
        this.password   = password;
        this.fullName   = fullName;
        this.email      = email;
        this.phone      = phone;
        this.lastScore  = 0;
        this.examsTaken = 0;
    }

    // ─── Getters ──────────────────────────────────────────────────────────────

    public String getUsername()  { return username;   }
    public String getPassword()  { return password;   }
    public String getFullName()  { return fullName;   }
    public String getEmail()     { return email;      }
    public String getPhone()     { return phone;      }
    public int    getLastScore() { return lastScore;  }
    public int    getExamsTaken(){ return examsTaken; }

    // ─── Setters ──────────────────────────────────────────────────────────────

    public void setPassword(String password)  { this.password  = password;  }
    public void setFullName(String fullName)  { this.fullName  = fullName;  }
    public void setEmail(String email)        { this.email     = email;     }
    public void setPhone(String phone)        { this.phone     = phone;     }
    public void setLastScore(int score)       { this.lastScore = score;     }

    /** Increments the total exam count by one. */
    public void incrementExamsTaken() { this.examsTaken++; }

    @Override
    public String toString() {
        return String.format("User{username='%s', fullName='%s', email='%s', examsTaken=%d}",
                username, fullName, email, examsTaken);
    }
}
