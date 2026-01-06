package com.github.pantherrobotics.panthercommandlib.triggers;

import com.github.pantherrobotics.panthercommandlib.commands.Command;
import com.github.pantherrobotics.panthercommandlib.commands.CommandRunner;
import com.github.pantherrobotics.panthercommandlib.commands.standardcommands.InstantCommand;

import java.util.ArrayList;
import java.util.function.BooleanSupplier;

public class Trigger {
    // Or and And conditions
    ArrayList<BooleanSupplier> andConditions;
    ArrayList<BooleanSupplier> orConditions;

    // Stored boolean values
    boolean currentValue;
    boolean previousValue;

    // Commands to run w/ conditions
    Command commandWhileTrue;
    Command commandWhileFalse;
    Command commandOnTrue;
    Command commandOnFalse;

    // Whether or not to force run commands
    boolean forceRunCommands;

    public Trigger(BooleanSupplier condition) {
        this.andConditions = new ArrayList<>();
        this.orConditions = new ArrayList<>();

        andConditions.add(condition);

        currentValue = false;
        previousValue = false;

        commandWhileFalse = new InstantCommand();
        commandWhileTrue = new InstantCommand();
        commandOnFalse = new InstantCommand();
        commandOnTrue = new InstantCommand();

        forceRunCommands = false;

        TriggerHandler.addTrigger(this);
    }

    /**
     * Add another condition that must be true for this to evaluate to true
     *
     * @param condition The condition you want to add
     */
    public Trigger and(BooleanSupplier condition) {
        andConditions.add(condition);

        return this;
    }

    /**
     * Add another condition, and if this one is true, the whole command will result to true.
     * Unlike traditional boolean expressions, this will override <code> and </code>
     *
     * @param condition condition
     */
    public Trigger or(BooleanSupplier condition) {
        orConditions.add(condition);

        return this;
    }

    /**
     * This will force run the commands with this trigger.
     */
    public void forceRunCommands() {
        this.forceRunCommands = true;
    }

    /**
     * Evaluates all boolean expressions to true or false
     *
     * @return the result
     */
    private boolean evaluate() {
        return andConditions.stream().allMatch(BooleanSupplier::getAsBoolean) || orConditions.stream().anyMatch(BooleanSupplier::getAsBoolean);
    }

    // ------------------------------------------------------------------------

    /**
     * Runs the command one time when the condition goes from <code> false </code> to <code> true </code>
     *
     * @param command The command that you want to run
     */
    public Trigger onTrue(Command command) {
        commandOnTrue = command;

        return this;
    }

    /**
     * Runs the command one time when the condition goes from <code> true </code> to <code> false </code>
     *
     * @param command The command that you want to run
     */
    public Trigger onFalse(Command command) {
        commandOnFalse = command;

        return this;
    }

    /**
     * Runs this command over and over again WHILE the condition returns <code>true</code>, interrupts the command when false.
     *
     * @param command The command that you want to run
     */
    public Trigger whileTrue(Command command) {
        commandWhileTrue = command;

        return this;
    }

    /**
     * Runs this command over and over again WHILE the condition returns <code>false</code>, interrupts the command when false.
     *
     * @param command The command that you want to run
     */
    public Trigger whileFalse(Command command) {
        commandWhileFalse = command;

        return this;
    }

    // ------------------------------------------------------------------------

    /**
     * Updates the trigger to run the commands it is told to
     */
    void update() {
        previousValue = currentValue;
        currentValue = evaluate();

        // -------------------------------

        // On True
        if ((!previousValue) && currentValue) {
            if (!CommandRunner.getCommandsRunning().contains(commandOnTrue)) {
                if (forceRunCommands) {
                    commandOnTrue.forceRun();
                } else {
                    commandOnTrue.run();
                }
            }
        }

        // -------------------------------

        // On False
        if (previousValue && (!currentValue)) {
            if (!CommandRunner.getCommandsRunning().contains(commandOnFalse)) {
                if (forceRunCommands) {
                    commandOnFalse.forceRun();
                } else {
                    commandOnFalse.run();
                }
            }
        }

        // -------------------------------

        // While True
        if (currentValue && previousValue) {
            if (!CommandRunner.getCommandsRunning().contains(commandWhileTrue)) {
                if (forceRunCommands) {
                    commandWhileTrue.forceRun();
                } else {
                    commandWhileTrue.run();
                }
            }
        } else {
            CommandRunner.forceEndCommand(commandWhileTrue);
        }

        // -------------------------------

        // While False
        if (!currentValue && !previousValue) {
            if (!CommandRunner.getCommandsRunning().contains(commandWhileFalse)) {

                if (forceRunCommands) {
                    commandWhileFalse.forceRun();
                } else {
                    commandWhileFalse.run();
                }
            }
        } else {
            CommandRunner.forceEndCommand(commandWhileFalse);
        }
    }

}
