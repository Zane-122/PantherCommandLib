package com.github.pantherrobotics.panthercommandlib.commands.standardcommands;

import com.github.pantherrobotics.panthercommandlib.commands.Command;

/**
 * Does nothing at all and never finishes unless interrupted
 */
public class IdleCommand extends Command {
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
