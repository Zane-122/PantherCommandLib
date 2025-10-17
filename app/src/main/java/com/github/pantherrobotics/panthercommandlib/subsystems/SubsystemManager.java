package com.github.pantherrobotics.panthercommandlib.subsystems;

import java.util.HashSet;

public class SubsystemManager {
    private final static HashSet<Subsystem> subsystems = new HashSet<>();
    public static void addSubsystem(Subsystem sub) {
        // If the subsystem is already in the set, stop.
        if (subsystems.contains(sub)) return;

        // Add the subsystem to the set
        subsystems.add(sub);

        // Initialize the subsystem
        sub.init();
    }
    public static void updateSubsystems() {
        for (Subsystem s : subsystems) {
            // Update the current subsystem
            s.update();
        }
    }

    public static HashSet<Subsystem> getSubsystems() {
        return subsystems;
    }

    /**
     * Clear the data from memory
     */
    public static void clear() {
        subsystems.clear();
    }
}
