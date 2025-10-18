package com.github.pantherrobotics.panthercommandlib.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class CommandGroup extends Command {
    protected final List<Command> commands;
    
    /**
     * Group of commands that can run in different ways
     *
     * @param commands The group of commands
     */
    public CommandGroup(Command... commands) {
        this.commands = Arrays.stream(commands).collect(Collectors.toList());
    }

    /**
     * @param command The command you want to add to the group-- It gets added after all the other commands
     * @return This.
     */
    public CommandGroup addCommand(Command command) {
        commands.add(command);

        return this;
    }

    @Override
    public final void onFinish(boolean commandInterrupted) {
        for (Command command : commands) {
            if (!CommandRunner.getCommandsRunning().contains(command)) {
                command.forceEnd();
            }
        }
    }

    @Override
    public void init() {}; 

    // ----------------------------------

    /**
     * This code should decide how <code> commands</code> get run.
     */
    public abstract void update();

    /**
     * @return The value that decides when all commands are run and this can finish
     */
    public abstract boolean isFinished();
}
