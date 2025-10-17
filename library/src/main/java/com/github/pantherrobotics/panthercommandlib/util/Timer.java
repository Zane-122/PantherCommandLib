package com.github.pantherrobotics.panthercommandlib.util;

public class Timer {
    private long startTime;
    private long stopTime;
    private boolean running;

    /**
     * Start the timer
     */
    public void start() {
        startTime = System.currentTimeMillis();
        running = true;
    }

    // Stop the timer
    public void stop() {
        stopTime = System.currentTimeMillis();
        running = false;
    }

    // Get elapsed time in milliseconds
    public long getTimeMillis() {
        if (running) {
            return System.currentTimeMillis() - startTime;
        } else {
            return stopTime - startTime;
        }
    }

    // Reset the timer
    public void reset() {
        startTime = 0;
        stopTime = 0;
        running = false;
    }

    // Get elapsed time in seconds
    public double getTimeSeconds() {
        return getTimeMillis() / 1000.0;
    }
}
