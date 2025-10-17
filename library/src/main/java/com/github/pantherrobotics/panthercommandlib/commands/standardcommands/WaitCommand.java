package com.github.pantherrobotics.panthercommandlib.commands.standardcommands;

import com.github.pantherrobotics.panthercommandlib.commands.Command;
import com.github.pantherrobotics.panthercommandlib.util.Timer;

public class WaitCommand extends Command {
    private final Timer timer;
    private final double waitTime;

    public WaitCommand(double waitTimeSeconds) {
        this.timer = new Timer();

        this.waitTime = waitTimeSeconds * 1000;
    }

    @Override
    public void init() {
        timer.start();
    }

    @Override
    public void update() {}

    @Override
    public void onFinish(boolean commandInterrupted) {
        timer.stop();
    }

    @Override
    public boolean isFinished() {
        return timer.getTimeMillis() >= waitTime;
    }
}
