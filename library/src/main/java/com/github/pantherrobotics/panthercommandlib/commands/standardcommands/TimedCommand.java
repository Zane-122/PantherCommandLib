package com.github.pantherrobotics.panthercommandlib.commands.standardcommands;

import com.github.pantherrobotics.panthercommandlib.commands.Command;
import com.github.pantherrobotics.panthercommandlib.util.Timer;

public class TimedCommand extends Command {
    private Timer timer;

    /**
     * This command will run the <code> commandToRun </code> until the time runs out. It will force end the command if it
     * has not ended already. This is the same as writing <code> commandToRun.withDeadline(new WaitCommand(deadlineSeconds)) </code>
     *
     * @param commandToRun The command that you want to run
     * @param deadlineSeconds The deadline for that command-- The max time it can run for
     */
    public TimedCommand(Command commandToRun, double deadlineSeconds) {

    }

    @Override
    public void init() {

    }

    @Override
    public void update() {

    }

    @Override
    public void onFinish(boolean commandInterrupted) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
