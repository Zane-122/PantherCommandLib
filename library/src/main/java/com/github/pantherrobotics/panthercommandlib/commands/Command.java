package com.github.pantherrobotics.panthercommandlib.commands;

import com.github.pantherrobotics.panthercommandlib.commands.commandgroups.RaceCommandGroup;
import com.github.pantherrobotics.panthercommandlib.commands.commandgroups.SequentialCommandGroup;
import com.github.pantherrobotics.panthercommandlib.commands.standardcommands.InstantCommand;
import com.github.pantherrobotics.panthercommandlib.subsystems.Subsystem;

import java.util.Arrays;
import java.util.HashSet;

public abstract class Command {
    // This is a set that will store ALL REQUIRED SUBSYSTEMS THAT ARE USED BY THIS COMMAND
    private final HashSet<Subsystem> requiredSubsystems = new HashSet<>();

    private Command deadline = null;
    private final SequentialCommandGroup nextCommands = new SequentialCommandGroup(this);

    /**
     * A Command is code that utilizes subsystems to physically do something on the robot.
     * You must call <code>addRequiredSubsystems()</code> and put every subsystem you use to ensure this
     * doesn't conflict with other commands.
     * You should run <code>addRequiredSubsystems()</code> here.
     */
    public Command() {}

    /**
     * The init function should be runniang code that you want to happen right when the command is started.
     * This will only run once, before update begins to run.
     */
    public abstract void init();

    /**
     * This code gets run every few milliseconds. This is basically a loop.
     */
    public abstract void update();

    /**
     * This code gets run when <code>isFinished()</code> returns true. After this code is run, the command stops.
     *
     * @param commandInterrupted This value is true if the command was interrupted by another one
     */
    public abstract void onFinish(boolean commandInterrupted);

    /**
     * When this method returns true, the command stops and the <code>onFinish()</code> method gets run.
     */
    public abstract boolean isFinished();

    /**
     * When this command is started, if another command is already requiring the subsystems that this one is requiring, this will
     * not run and the other command will continue. If another command that requires the same subsystems as this one, and it starts after this one,
     * it will be canceled and this one will continue to run.
     *
     * @param subsystems This should be an array of all of your different subsystems that you are PHYSICALLY USING in this command.
     */
    public void addRequiredSubsystems(Subsystem... subsystems) {
        requiredSubsystems.addAll(Arrays.asList(subsystems));
    }

    /**
     * Runs this command
     */
    public void run() {
        CommandRunner.runCommand(this);
    }

    /**
     * Force runs this command
     */
    public void forceRun() {
        CommandRunner.forceRunCommand(this);
    }

    /**
     * Force ends this command
     */
    public void forceEnd() {
        CommandRunner.forceEndCommand(this);
    }

    /**
     * You should not be calling this. You just don't have to.
     * This is used inside the command scheduler to choose whether or not it is allowed to run teh command
     *
     * @return The currently required subsystems in this command
     */
    HashSet<Subsystem> getRequiredSubsystems() {
        return new HashSet<>(requiredSubsystems);
    }

    /**
     * This returns whether this command is using the provided subsystem.
     *
     * @param sub The subsystem you are checking for.
     * @return The result of if it is using it or not.
     */
    boolean usingSubsystem(Subsystem sub) {
        return requiredSubsystems.contains(sub);
    }

    // ------------------------------------------------

    /**
     * This is a modifier for this command. It sets teh command, if any, that will run at the same time as this one,
     * and if this other command finishes before this one, it will force end this command, acting as a deadline
     *
     * @param command The command that you want to act as the deadline
     * @return This returns this Command
     */
    public Command withDeadline(Command command) {
        this.deadline = command;

        return this;
    }

    /**
     * Instead of giving THIS command a deadline, it makes this command a deadline for the command passed in
     *
     * @param command The command that you want to use as the main command
     */
    public Command deadlineFor(Command command) {
        return command.withDeadline(this);
    }

    /**
     * This is a modifier for this command. It sets another command, if any, that will run after this command finishes and any
     * other that you added after.
     *
     * @param command The command that you want to add to the sequence
     * @return This returns this Command
     */
    public SequentialCommandGroup andThen(Command command) {
        nextCommands.addCommand(command);

        return nextCommands;
    }

    public Command getDeadlineCommand() {
        return deadline;
    }
}
