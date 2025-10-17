package com.github.pantherrobotics.panthercommandlib.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import com.github.pantherrobotics.panthercommandlib.commands.Command;
import com.github.pantherrobotics.panthercommandlib.commands.standardcommands.InstantCommand;

public abstract class Subsystem {
    // Hardware map for the devices
    public HardwareMap hardwareMap;

    // Default Command
    private Command defaultCommand;

    public Subsystem(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        this.defaultCommand = new InstantCommand();

        SubsystemManager.addSubsystem(this);
    }

    /**
     * This code will run one time before update begins to cycle.
     * You can put things in here that only need to be run once before everything else.
     */
    public abstract void init();

    /**
     * This code gets updated every few milliseconds. This is basically a loop.
     */
    public abstract void update();

    /**
     * This sets the command that will run on this subsystem when NO OTHER COMMAND is requiring this subsystem.
     * @param command The command that you want to run. This should always be utilizing just this subsystem.
     */
    public void setDefaultCommand(Command command) {
        this.defaultCommand = command;
        defaultCommand.init();
    }


    public Command getDefaultCommand() {
        return defaultCommand;
    }
}
