package com.github.pantherrobotics.panthercommandlib.commands.standardcommands;

import com.github.pantherrobotics.panthercommandlib.commands.Command;
import com.github.pantherrobotics.panthercommandlib.subsystems.Subsystem;

public class InstantCommand extends Command {
    Runnable code;

    /**
     * This will run the code given, and afterwards end IMMEDIATELY
     *
     * @param code The code that you want to run instantly
     * @param requirements These are the subsystems, if any, that you are physically using on the robot
     */
    public InstantCommand(Runnable code, Subsystem... requirements) {
        this.code = code;

        addRequiredSubsystems(requirements);
    }

    /**
     * This is a completely empty command, nothing will run and it will END IMMEDIATELY.
     */
    public InstantCommand() {
        this.code = () -> {};
    }

    @Override
    public void init() {
        code.run();
    }

    @Override
    public void update() {}

    @Override
    public void onFinish(boolean commandInterrupted) {}

    @Override
    public boolean isFinished() {
        return true;
    }
}
