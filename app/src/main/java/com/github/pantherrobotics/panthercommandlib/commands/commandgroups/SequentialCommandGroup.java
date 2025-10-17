package com.github.pantherrobotics.panthercommandlib.commands.commandgroups;

import com.github.pantherrobotics.panthercommandlib.commands.Command;
import com.github.pantherrobotics.panthercommandlib.commands.CommandGroup;
import com.github.pantherrobotics.panthercommandlib.commands.CommandRunner;

public class SequentialCommandGroup extends CommandGroup {
    int index;
    boolean commandStarted;

    public SequentialCommandGroup(Command... commands) {
        super(commands);

        index = 0;
    }

    @Override
    public final void update() {
        if (isFinished()) return;

        if (!commandStarted) {
            CommandRunner.forceRunCommand(commands.get(index));
            commandStarted = true;
        } else {
            if (!CommandRunner.getCommandsRunning().contains(commands.get(index))) {
                commandStarted = false;
                index++;
            }
        }
    }

    @Override
    public final boolean isFinished() {
        return index >= commands.size();
    }
}
