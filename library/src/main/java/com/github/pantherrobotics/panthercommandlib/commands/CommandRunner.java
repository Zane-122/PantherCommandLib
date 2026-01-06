package com.github.pantherrobotics.panthercommandlib.commands;

import android.util.Pair;

import com.github.pantherrobotics.panthercommandlib.PantherCommandLib;
import com.github.pantherrobotics.panthercommandlib.subsystems.Subsystem;
import com.github.pantherrobotics.panthercommandlib.subsystems.SubsystemManager;

import java.util.HashSet;

public class CommandRunner {
    // This will store a list of all the subsystems that are currently in use by all running commands
    private final static HashSet<Subsystem> subsystemsInUse = new HashSet<>();
    private final static HashSet<Command> commandsRunning = new HashSet<>();

    /**
     * This will start to run the command that put in, but if there is another command running,
     * this one will not run.
     *
     * @param command The command that you want to run. If the command that runs
     * @return Returns whether or not the command got run. If it got run, this returns <code>true</code>, otherwise, it returns <code>false</code>
     */
    @SuppressWarnings("all")
    public static boolean runCommand(Command command) {
        if (command == null || commandsRunning.contains(command)) return false;

        HashSet<Subsystem> requiredSubsystems = command.getRequiredSubsystems();

        for (Subsystem sub : requiredSubsystems) {
            if (subsystemsInUse.contains(sub)) {
                return false;
            }
        }

        for (Subsystem sub : requiredSubsystems) {
            sub.getDefaultCommand().onFinish(true);
        }

        subsystemsInUse.addAll(command.getRequiredSubsystems());
        commandsRunning.add(command);

        command.init();

        Command deadlineCommand = command.getDeadlineCommand();

        if (!(deadlineCommand == null)) {
            forceRunCommand(deadlineCommand);
        }

        return true;
    }

    /**
     * This will start to run the command that put in, but unlike <code>runCommand()</code>,
     * this will run and interrupt any other commands that are using subsystems that this one requires
     *
     * @param command The command that you want to run. If the command that runs
     */
    public static void forceRunCommand(Command command) {
        if (command == null || commandsRunning.contains(command)) return;

        HashSet<Subsystem> requiredSubsystems = command.getRequiredSubsystems();

        for (Command commandRunning : new HashSet<>(commandsRunning)) {
            for (Subsystem requiredSubsystem : new HashSet<>(requiredSubsystems)) {
                if (commandRunning.usingSubsystem(requiredSubsystem)) {
                    commandRunning.forceEnd();
                    break;
                }
            }
        }

        for (Subsystem sub : requiredSubsystems) {
            sub.getDefaultCommand().onFinish(true);
        }

        subsystemsInUse.addAll(command.getRequiredSubsystems());
        commandsRunning.add(command);

        command.init();

        Command deadlineCommand = command.getDeadlineCommand();

        if (!(deadlineCommand == null)) {
            forceRunCommand(deadlineCommand);
        }
    }

    /**
     * This updates all commands
     */
    public static void executeCommands(boolean disableDefaultCommands) {
        HashSet<Pair<Command, Boolean>> toEnd = new HashSet<>();

        for (Command command : new HashSet<>(commandsRunning)) {
            if (command.isFinished()) {
                toEnd.add(new Pair<>(command, false));
            } else {
                command.update();
            }

            if (command.getDeadlineCommand() != null){
                if (command.getDeadlineCommand().isFinished()) {
                    toEnd.add(new Pair<>(command, true));
                }
            }
        }

        for (Pair<Command, Boolean> command : toEnd) {
            endCommand(command.first, command.second);
        }

        if (!disableDefaultCommands) {
            for (Subsystem sub : SubsystemManager.getSubsystems()) {
                if (!subsystemsInUse.contains(sub)) {
                    sub.getDefaultCommand().update();
                }
            }
        }
    }

    /** Ends commands that we want and we can choose if they are interrupted or not.
     *
     * @param command The command that you want to end
     * @param interrupted Whether or not you interrupted that command with another one
     */
    private static void endCommand(Command command, boolean interrupted) {
        if (command == null || !commandsRunning.contains(command)) return;

        for (Subsystem s : command.getRequiredSubsystems()) {
            subsystemsInUse.remove(s);
        }

        commandsRunning.remove(command);

        command.onFinish(interrupted);
    }

    /** Ends commands that we want and marks it as interrupted since we cut it out
     *
     * @param command The command that you want to end
     */
    public static void forceEndCommand(Command command) {
        endCommand(command, true);
    }

    /**
     * Clear the data from memory
     */
    public static void clear() {
        commandsRunning.clear();
        subsystemsInUse.clear();
    }

    /**
     * Gets all the commands currently running
     *
     * @return All of the commands currently running
     */
    public static HashSet<Command> getCommandsRunning() {
        return new HashSet<>(commandsRunning);
    }
}
