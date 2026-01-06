package com.github.pantherrobotics.panthercommandlib.commands.commandgroups;

import com.github.pantherrobotics.panthercommandlib.commands.Command;
import com.github.pantherrobotics.panthercommandlib.commands.CommandGroup;
import com.github.pantherrobotics.panthercommandlib.commands.CommandRunner;

public class SequentialCommandGroup extends CommandGroup {
    int index;
    boolean currentCommandStarted;

    /**
     * A group of commands that run one after another
     *
     * @param commands The list of commands that you want to run. They run in the order that you define them
     */
    public SequentialCommandGroup(Command... commands) {
        super(commands);
    }

    @SuppressWarnings("all")
    public SequentialCommandGroup(SequentialCommandGroup group) {
        super(group.getCommands().toArray(new Command[0]));
    }

    @Override
    public void init() {
        index = 0;
        currentCommandStarted = false;
    }

    @Override
    public final void update() {
        if (isFinished()) return;
        
        // Bounds check before accessing commands
        if (index >= commands.size()) return;

        Command currentCommand = commands.get(index);

        // Try to start the current command if it hasn't been started yet
        if (!currentCommandStarted) {
            currentCommand.forceRun();
            currentCommandStarted = true;
        }

        // Check if current command has finished
        if (!CommandRunner.getCommandsRunning().contains(currentCommand)) {
            // Command has finished, move to next
            index++;
            currentCommandStarted = false;
            
            // Start next command if available
            if (index < commands.size()) {
                commands.get(index).forceRun();
                currentCommandStarted = true;
            }
        }
    }

    @Override
    public final boolean isFinished() {
        return index >= commands.size();
    }
}
