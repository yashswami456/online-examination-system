package com.exam.service;

import com.exam.model.User;
import java.util.HashMap;
import java.util.Map;


public class UserService {

    /** In-memory user store: username → User */
    private final Map<String, User> userStore = new HashMap<>();

    /** The user who is currently logged in (null when no session exists). */
    private User currentUser = null;

    // ─── Constructor ──────────────────────────────────────────────────────────

    public UserService() {
        seedDemoUsers();
    }

    /** Pre-populate the store with two sample accounts for easy testing. */
    private void seedDemoUsers() {
        userStore.put("admin",
                new User("admin", "admin123", "Admin User", "admin@exam.com", "9999999999"));
        userStore.put("student",
                new User("student", "student123", "John Doe", "john@exam.com", "8888888888"));
    }

    // ─── Authentication ───────────────────────────────────────────────────────


    public User login(String username, String password) {
        User user = userStore.get(username);
        if (user != null && user.getPassword().equals(password)) {
            currentUser = user;
            return user;
        }
        return null;
    }


    public void logout() {
        currentUser = null;
    }


    public boolean register(String username, String password, String fullName,
                            String email, String phone) {
        if (userStore.containsKey(username)) {
            return false; // username already exists
        }
        User newUser = new User(username, password, fullName, email, phone);
        userStore.put(username, newUser);
        return true;
    }

    // ─── Profile Management ───────────────────────────────────────────────────


    public boolean updateProfile(String fullName, String email, String phone) {
        if (currentUser == null) return false;
        if (fullName != null && !fullName.isBlank()) currentUser.setFullName(fullName);
        if (email    != null && !email.isBlank())    currentUser.setEmail(email);
        if (phone    != null && !phone.isBlank())    currentUser.setPhone(phone);
        return true;
    }


    public boolean changePassword(String oldPassword, String newPassword) {
        if (currentUser == null) return false;
        if (!currentUser.getPassword().equals(oldPassword)) return false;
        currentUser.setPassword(newPassword);
        return true;
    }

    // ─── Score Recording ──────────────────────────────────────────────────────

    /** Records the latest exam score for the currently logged-in user. */
    public void recordScore(int score) {
        if (currentUser != null) {
            currentUser.setLastScore(score);
            currentUser.incrementExamsTaken();
        }
    }

    // ─── Accessors ────────────────────────────────────────────────────────────

    /** Returns the currently logged-in user, or {@code null}. */
    public User getCurrentUser() { return currentUser; }

    /** Returns {@code true} if a user is currently logged in. */
    public boolean isLoggedIn() { return currentUser != null; }
}
