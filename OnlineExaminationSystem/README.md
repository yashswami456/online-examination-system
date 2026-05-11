# 🎓 Online Examination System

A fully-featured, console-based **Online Examination System** built in Java using Object-Oriented Programming principles. Students can register, log in, take timed MCQ exams, review results, and manage their profiles — all from the terminal.

---

## ✨ Features

| Feature | Description |
|---|---|
| 🔐 User Authentication | Login, logout, and new-user registration |
| 👤 Profile Management | Update name, email, phone; change password |
| 📝 MCQ Exam Engine | 10 randomly selected questions per attempt |
| ⏱️ Countdown Timer | 10-minute timer; auto-submits on expiry |
| 📊 Result & Grading | Score, percentage, letter grade, answer review |
| 🔄 Session System | Multi-login support; graceful logout |
| 🎨 Coloured Console UI | ANSI colours, borders, and formatted output |

---

## 🚀 Quick Start

### Prerequisites
- **Java JDK 17+** installed ([Download](https://adoptium.net/))
- A terminal / command prompt

### Run on Linux / macOS

```bash
git clone <repo-url>
cd OnlineExaminationSystem
chmod +x run.sh
./run.sh
```

### Run on Windows

```cmd
cd OnlineExaminationSystem
run.bat
```

### Manual Build (any OS)

```bash
# 1. Compile
find src -name "*.java" > sources.txt
javac -d bin @sources.txt

# 2. Run
java -cp bin com.exam.Main
```

---

## 🔑 Demo Accounts

| Username | Password | Role |
|---|---|---|
| `admin` | `admin123` | Administrator |
| `student` | `student123` | Sample Student |

You can also register a new account from the welcome screen.

---

## 📁 Project Structure

```
OnlineExaminationSystem/
├── src/
│   └── com/exam/
│       ├── Main.java                  # Application entry point
│       ├── model/
│       │   ├── User.java              # User entity
│       │   ├── Question.java          # MCQ question entity
│       │   └── ExamResult.java        # Exam result / scoring
│       ├── service/
│       │   ├── UserService.java       # Auth, profile, session
│       │   ├── QuestionService.java   # Question bank (25 questions)
│       │   └── ExamService.java       # Exam orchestration
│       ├── util/
│       │   ├── ExamTimer.java         # Background countdown timer
│       │   └── ConsoleHelper.java     # UI helpers, ANSI colours
│       └── ui/
│           ├── LoginUI.java           # Login & registration screens
│           ├── DashboardUI.java       # Post-login main menu
│           ├── ExamUI.java            # Live exam interface + results
│           └── ProfileUI.java         # Profile viewer & editor
├── bin/                               # Compiled .class files (generated)
├── run.sh                             # Build & run (Linux/macOS)
├── run.bat                            # Build & run (Windows)
├── .gitignore
└── README.md
```

---

## 🏗️ Architecture

The project follows a clean **three-layer architecture**:

```
┌─────────────────────────────────────┐
│            UI Layer                 │  LoginUI, DashboardUI, ExamUI, ProfileUI
├─────────────────────────────────────┤
│          Service Layer              │  UserService, ExamService, QuestionService
├─────────────────────────────────────┤
│           Model Layer               │  User, Question, ExamResult
├─────────────────────────────────────┤
│           Util Layer                │  ExamTimer, ConsoleHelper
└─────────────────────────────────────┘
```

**OOP Concepts Used:**
- **Encapsulation** — All model fields are private with public getters/setters
- **Abstraction** — Services expose clean APIs; UI knows nothing about data storage
- **Separation of Concerns** — UI, business logic, and data are in distinct layers
- **Single Responsibility** — Each class has one clear purpose

---

## 📚 Question Bank

25 MCQ questions across five subject areas:

| Subject | Questions |
|---|---|
| Java Programming | 7 |
| Data Structures & Algorithms | 5 |
| Operating Systems | 4 |
| Computer Networks | 4 |
| Database Management Systems | 5 |

Questions are **randomly shuffled** each attempt, so no two exams are identical.

---

## 🎯 Grading Scheme

| Grade | Percentage |
|---|---|
| **A+** | 90% – 100% |
| **A** | 80% – 89%  |
| **B** | 70% – 79%  |
| **C** | 60% – 69%  |
| **D** | 50% – 59%  |
| **F** | Below 50%  |

**No negative marking.** Skipped questions score 0.

---

## ⚙️ Configuration

You can change exam parameters in `ExamService.java`:

```java
public static final int QUESTIONS_PER_EXAM    = 10;    // Questions per attempt
public static final int EXAM_DURATION_SECONDS = 10 * 60; // 10 minutes
```

To add more questions, edit `QuestionService.java` and call `questionBank.add(...)`.

---

## 🧑‍💻 How the Timer Works

`ExamTimer` runs a dedicated background **daemon thread** that decrements a shared counter once per second. When it reaches zero, it fires the `onTimeUp` callback (provided by `ExamService`), which signals the question loop in `ExamUI` to break and auto-submit.

Thread safety is achieved through `AtomicBoolean` and `AtomicLong` — no synchronised blocks needed.

---

## 📄 License

This project is for educational purposes. Feel free to use, modify, and extend it.
