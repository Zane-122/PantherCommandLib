package com.github.pantherrobotics.panthercommandlib;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.github.pantherrobotics.panthercommandlib.commands.CommandRunner;
import com.github.pantherrobotics.panthercommandlib.subsystems.SubsystemManager;
import com.github.pantherrobotics.panthercommandlib.triggers.TriggerHandler;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

public abstract class TeleOpContainer extends OpMode {

    @Override
    public final void init() {
        // Add telemetry instance
        GlobalTelemetry.setTelemetryInstance(telemetry);
        GlobalTelemetry.telemetry = telemetry;

        // First initialize the subsystems
        initializeSubsystems();

        // Finally run the init code
        onInit();

    }

    @Override
    public final void init_loop() {
        SubsystemManager.updateSubsystems();
        onInitUpdate();

        telemetry.update();
    }

    @Override
    public final void start() {
        // Run start code
        onStart();

        // Then initialize the default commands
        initializeDefaultCommands();

        // Add the triggers
        addTriggers();
    }

    @Override
    public final void loop() {
        CommandRunner.executeCommands(false);

        SubsystemManager.updateSubsystems();

        TriggerHandler.updateTriggers();

        onUpdate();

        telemetry.update();
    }

    @Override
    public final void stop() {
        onEnd();

        CommandRunner.clear();

        SubsystemManager.clear();

        TriggerHandler.clear();

        GlobalTelemetry.clear();
    }

    /**
     * This code will run one time when the init button is pressed.
     */
    public void onInit() {

    };

    /**
     * This code will run once when the opmode is started
     */
    public void onStart() {

    }

    /**
     * This code will run once when the opmode ends
     */
    public void onEnd() {

    }

    /**
     * This code will run every loop cycle. Once every ~20ms
     */
    public void onUpdate() {

    }

    /**
     * This code will run every loop cycle between when init is pressed and start
     */
    public void onInitUpdate() {

    }

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
