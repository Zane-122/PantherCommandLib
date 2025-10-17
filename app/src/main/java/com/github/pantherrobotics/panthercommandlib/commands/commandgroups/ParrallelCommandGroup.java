package com.github.pantherrobotics.panthercommandlib.commands.commandgroups;

import com.github.pantherrobotics.panthercommandlib.commands.Command;
import com.github.pantherrobotics.panthercommandlib.commands.CommandGroup;
import com.github.pantherrobotics.panthercommandlib.commands.CommandRunner;

public class ParrallelCommandGroup extends CommandGroup {
    boolean commandsRun;

    public ParrallelCommandGroup(Command... commands) {
        super(commands);

        commandsRun = false;
    }

    @Override
    public final void update() {
        if (isFinished()) return;

        for (Command command : commands) {
            CommandRunner.forceRunCommand(command);
        }

        commandsRun = true;
    }

    @Override
    public final boolean isFinished() {
        return commandsRun;
    }
}
