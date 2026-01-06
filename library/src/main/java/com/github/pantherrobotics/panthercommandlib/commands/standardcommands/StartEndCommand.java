package com.github.pantherrobotics.panthercommandlib.commands.standardcommands;

import com.github.pantherrobotics.panthercommandlib.commands.Command;
import com.github.pantherrobotics.panthercommandlib.subsystems.Subsystem;

public class StartEndCommand extends Command {
    private final Runnable onInit;
    private final Runnable onFinish;

    /**
     * This command will run <code>onInit()</code> immediately when it is run, and then when it is interrupted, it will run <code>onFinish</code>
     *  
     * @param onStart Code that runs immediatly when command is run
     * @param onFinish Code that is run immediatly when the command is inturrupted
     * @param requiredSubsystems Any subysystems that this command requires
     */
    public StartEndCommand(Runnable onStart, Runnable onFinish, Subsystem... requiredSubsystems) {
        this.onInit = onStart;
        this.onFinish = onFinish;

        addRequiredSubsystems(requiredSubsystems);
    }

    @Override
    public void init() {
        onInit.run();
    }
    @Override
    public void update() {}

    @Override
    public void onFinish(boolean commandInterrupted) {
        onFinish.run();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
