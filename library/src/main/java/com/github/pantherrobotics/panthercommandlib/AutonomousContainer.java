package com.github.pantherrobotics.panthercommandlib;

import com.github.pantherrobotics.panthercommandlib.commands.Command;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.github.pantherrobotics.panthercommandlib.commands.CommandRunner;
import com.github.pantherrobotics.panthercommandlib.subsystems.SubsystemManager;
import com.github.pantherrobotics.panthercommandlib.triggers.TriggerHandler;

public abstract class AutonomousContainer extends OpMode {

    @Override
    public final void init() {
        // Set the global telemetry instance
        GlobalTelemetry.setTelemetryInstance(telemetry);

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
        onStart();

        // Run the autonomous command
        setAutonomousCommand().forceRun();
    }


    @Override
    public final void loop() {
        onUpdate();

        CommandRunner.executeCommands(true);

        SubsystemManager.updateSubsystems();

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
     * This is the command that will be scheduled when the autonomous starts.
     * This will typically be a sequential command group of many different commands and more command groups.
     * Call this and return the autonomous command that you want to run. It will schedule automatically when the auton starts.
     * @return The autonomous command
     */
    public abstract Command setAutonomousCommand();
}
