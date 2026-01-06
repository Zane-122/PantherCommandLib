package com.github.pantherrobotics.panthercommandlib.commands.commandgroups;

import com.github.pantherrobotics.panthercommandlib.commands.Command;
import com.github.pantherrobotics.panthercommandlib.commands.CommandGroup;
import com.github.pantherrobotics.panthercommandlib.commands.CommandRunner;

public class ParrallelCommandGroup extends CommandGroup {
    boolean commandsStarted;

    public ParrallelCommandGroup(Command... commands) {
        super(commands);

        commandsStarted = false;
    }

    @Override
    public void init() {
        commandsStarted = false;
    }

    @Override
    public final void update() {
        if (isFinished()) return;

        // Start all commands on first update
        if (!commandsStarted) {
            for (Command command : commands) {
                CommandRunner.forceRunCommand(command);
            }
            commandsStarted = true;
        }
    }

    @Override
    public final boolean isFinished() {
        // Finished when all child commands have finished
        for (Command command : commands) {
            if (CommandRunner.getCommandsRunning().contains(command)) {
                return false;
            }
        }
        return commandsStarted; // Only return true if we've started the commands
    }
}
