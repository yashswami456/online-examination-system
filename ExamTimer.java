package com.exam.util;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;


public class ExamTimer {

    private final long          durationSeconds;  // Total time allotted
    private final AtomicLong    remaining;        // Seconds left
    private final AtomicBoolean running;          // Whether the timer is active
    private final Runnable      onTimeUp;         // Callback when time expires
    private Thread              timerThread;

    // ─── Constructor ──────────────────────────────────────────────────────────


    public ExamTimer(long durationSeconds, Runnable onTimeUp) {
        this.durationSeconds = durationSeconds;
        this.remaining       = new AtomicLong(durationSeconds);
        this.running         = new AtomicBoolean(false);
        this.onTimeUp        = onTimeUp;
    }

    // ─── Lifecycle ────────────────────────────────────────────────────────────

    /** Starts the countdown on a background daemon thread. */
    public void start() {
        running.set(true);
        timerThread = new Thread(() -> {
            while (running.get() && remaining.get() > 0) {
                try {
                    Thread.sleep(1000); // sleep 1 second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                if (running.get()) {
                    remaining.decrementAndGet();
                }
            }
            // Trigger callback only if stopped naturally (time ran out)
            if (running.get() && remaining.get() <= 0) {
                running.set(false);
                if (onTimeUp != null) {
                    onTimeUp.run();
                }
            }
        });
        timerThread.setDaemon(true); // Won't block JVM shutdown
        timerThread.setName("ExamTimer");
        timerThread.start();
    }

    /** Stops the timer (called on manual submission). */
    public void stop() {
        running.set(false);
        if (timerThread != null) {
            timerThread.interrupt();
        }
    }

    public long getRemainingSeconds() { return remaining.get(); }


    public boolean isRunning() { return running.get(); }


    public String getFormattedTime() {
        long secs = remaining.get();
        long mins = secs / 60;
        long sec  = secs % 60;
        return String.format("%02d:%02d", mins, sec);
    }
}
