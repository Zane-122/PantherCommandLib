package com.github.pantherrobotics.panthercommandlib.triggers;

import java.util.HashSet;

public class TriggerHandler {
    private static final HashSet<Trigger> triggers = new HashSet<>();

    /**
     * Adds a trigger to the trigger manager
     *
     * @param trigger The trigger that you are adding
     */
    static void addTrigger(Trigger trigger) {
        triggers.add(trigger);
    }

    /**
     * Updates the triggers.
     */
    public static void updateTriggers() {
        for (Trigger trigger : triggers) {
            trigger.update();
        }
    }

    /**
     * Clear the data from memory
     */
    public static void clear() {
        triggers.clear();
    }
}
