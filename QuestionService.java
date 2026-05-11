package com.exam.service;

import com.exam.model.Question;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class QuestionService {

    /** The full question bank. */
    private final List<Question> questionBank = new ArrayList<>();

    // ─── Constructor ──────────────────────────────────────────────────────────

    public QuestionService() {
        loadQuestions();
    }

    // ─── Question Loader ──────────────────────────────────────────────────────


    private void loadQuestions() {

        // ── Java Programming ─────────────────────────────────────────────────
        questionBank.add(new Question(
                "Which keyword is used to inherit a class in Java?",
                new String[]{"implements", "extends", "inherits", "super"},
                2));

        questionBank.add(new Question(
                "What is the default value of a boolean variable in Java?",
                new String[]{"true", "false", "0", "null"},
                2));

        questionBank.add(new Question(
                "Which of the following is NOT a Java primitive type?",
                new String[]{"int", "float", "String", "char"},
                3));

        questionBank.add(new Question(
                "What does JVM stand for?",
                new String[]{"Java Virtual Machine", "Java Variable Method",
                             "Java Verified Module", "Java Visual Manager"},
                1));

        questionBank.add(new Question(
                "Which collection class allows duplicate elements and maintains insertion order?",
                new String[]{"HashSet", "TreeSet", "ArrayList", "HashMap"},
                3));

        questionBank.add(new Question(
                "What is the output of: System.out.println(10 / 3); ?",
                new String[]{"3.33", "3", "3.0", "Compilation error"},
                2));

        questionBank.add(new Question(
                "Which access modifier makes a member visible only within its own class?",
                new String[]{"public", "protected", "default", "private"},
                4));

        // ── Data Structures ──────────────────────────────────────────────────
        questionBank.add(new Question(
                "Which data structure uses LIFO (Last In First Out) order?",
                new String[]{"Queue", "Stack", "Linked List", "Tree"},
                2));

        questionBank.add(new Question(
                "What is the time complexity of binary search?",
                new String[]{"O(n)", "O(n²)", "O(log n)", "O(1)"},
                3));

        questionBank.add(new Question(
                "Which sorting algorithm has the best average-case time complexity?",
                new String[]{"Bubble Sort", "Selection Sort", "Merge Sort", "Insertion Sort"},
                3));

        questionBank.add(new Question(
                "In a binary tree, what is the maximum number of nodes at level k?",
                new String[]{"k", "2k", "2^k", "k²"},
                3));

        questionBank.add(new Question(
                "Which data structure is used to implement recursion internally?",
                new String[]{"Queue", "Stack", "Array", "Heap"},
                2));

        // ── Operating Systems ─────────────────────────────────────────────────
        questionBank.add(new Question(
                "What is a deadlock in operating systems?",
                new String[]{"A system crash", "A situation where processes wait for each other indefinitely",
                             "A memory overflow", "A type of virus"},
                2));

        questionBank.add(new Question(
                "Which scheduling algorithm gives the shortest average waiting time?",
                new String[]{"FCFS", "Round Robin", "SJF (Shortest Job First)", "Priority Scheduling"},
                3));

        questionBank.add(new Question(
                "What does 'thrashing' refer to in OS?",
                new String[]{"High CPU usage", "Excessive paging activity degrading performance",
                             "Disk failure", "Memory leak"},
                2));

        questionBank.add(new Question(
                "Which of the following is NOT a page replacement algorithm?",
                new String[]{"FIFO", "LRU", "Optimal", "Round Robin"},
                4));

        // ── Computer Networks ─────────────────────────────────────────────────
        questionBank.add(new Question(
                "Which layer of the OSI model is responsible for routing?",
                new String[]{"Data Link Layer", "Network Layer", "Transport Layer", "Session Layer"},
                2));

        questionBank.add(new Question(
                "What does HTTP stand for?",
                new String[]{"HyperText Transfer Protocol", "High Transfer Text Protocol",
                             "Hyperlink Text Transfer Protocol", "Host Transfer Text Protocol"},
                1));

        questionBank.add(new Question(
                "Which protocol is used to assign IP addresses dynamically?",
                new String[]{"DNS", "FTP", "DHCP", "SMTP"},
                3));

        questionBank.add(new Question(
                "What is the maximum transmission unit (MTU) of Ethernet?",
                new String[]{"512 bytes", "1024 bytes", "1500 bytes", "2048 bytes"},
                3));

        // ── Database Management ───────────────────────────────────────────────
        questionBank.add(new Question(
                "Which SQL command is used to retrieve data from a table?",
                new String[]{"INSERT", "UPDATE", "SELECT", "DELETE"},
                3));

        questionBank.add(new Question(
                "What does ACID stand for in database systems?",
                new String[]{"Atomicity, Consistency, Isolation, Durability",
                             "Access, Control, Integrity, Data",
                             "Atomic, Computed, Integrated, Defined",
                             "Availability, Consistency, Isolation, Design"},
                1));

        questionBank.add(new Question(
                "Which normal form eliminates transitive dependencies?",
                new String[]{"1NF", "2NF", "3NF", "BCNF"},
                3));

        questionBank.add(new Question(
                "What is a foreign key?",
                new String[]{"A key that is always unique",
                             "A key that references the primary key of another table",
                             "A key used only in NoSQL databases",
                             "A composite key made of multiple columns"},
                2));

        questionBank.add(new Question(
                "Which JOIN returns all records from the left table even if there is no match?",
                new String[]{"INNER JOIN", "RIGHT JOIN", "LEFT JOIN", "FULL JOIN"},
                3));
    }

    // ─── Public API ───────────────────────────────────────────────────────────


    public List<Question> getShuffledQuestions(int count) {
        List<Question> shuffled = new ArrayList<>(questionBank);
        Collections.shuffle(shuffled);
        int limit = Math.min(count, shuffled.size());
        return Collections.unmodifiableList(shuffled.subList(0, limit));
    }


    public int getTotalQuestions() { return questionBank.size(); }
}
