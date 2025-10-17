package com.github.pantherrobotics.panthercommandlib;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.github.pantherrobotics.panthercommandlib.commands.CommandRunner;
import com.github.pantherrobotics.panthercommandlib.subsystems.Subsystem;
import com.github.pantherrobotics.panthercommandlib.subsystems.SubsystemManager;
import com.github.pantherrobotics.panthercommandlib.triggers.TriggerHandler;

public abstract class RobotContainer extends OpMode {

    @Override
    public final void init() {
        // First initialize the subsystems
        initializeSubsystems();

        // Then initialize the default commands
        initializeDefaultCommands();

        // Add the triggers
        addTriggers();

        // Finally run the init code
        onInit();
    }

    @Override
    public final void loop() {
        CommandRunner.executeCommands();

        SubsystemManager.updateSubsystems();

        TriggerHandler.updateTriggers();
    }

    @Override
    public final void stop() {
        CommandRunner.clear();

        SubsystemManager.clear();

        TriggerHandler.clear();
    }

    /**
     * This code will run one time when the init button is pressed.
     */
    public abstract void onInit();

    /**
     * This is where you give your subsystems values.
     */
    public abstract void initializeSubsystems();

    /**
     * For any subysystems that you want to have default commands, make them here.
     *
     * <p> For example, if you want to give your Subsystem a default command, you would write: </p>
     * <code> Subsystem.setDefaultCommand(new SomeCommand()) </code>
     */
    public abstract void initializeDefaultCommands();

    /**
     * This is where you can add your triggers
     */
    public abstract void addTriggers();
}
