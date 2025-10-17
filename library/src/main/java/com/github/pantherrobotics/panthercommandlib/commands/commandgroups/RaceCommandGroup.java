package com.github.pantherrobotics.panthercommandlib.commands.commandgroups;

import com.github.pantherrobotics.panthercommandlib.commands.Command;
import com.github.pantherrobotics.panthercommandlib.commands.CommandGroup;
import com.github.pantherrobotics.panthercommandlib.commands.CommandRunner;

public class RaceCommandGroup extends CommandGroup {
    private boolean end;

    public RaceCommandGroup(Command... commands) {
        super(commands);

        end = false;
    }

    @Override
    public final void init() {
        for (Command command : commands) {
            CommandRunner.forceRunCommand(command);
        }
    }


    @Override
    public final void update() {
        for (Command command : commands) {
            if (!CommandRunner.getCommandsRunning().contains(command)) {
                for (Command commandToEnd : commands) {
                    if (!command.equals(commandToEnd) && CommandRunner.getCommandsRunning().contains(commandToEnd)) {
                        CommandRunner.forceEndCommand(commandToEnd);
                    }
                }

                end = true;
                break;
            }
        }
    }

    @Override
    public final boolean isFinished() {
        return end;
    }
}
