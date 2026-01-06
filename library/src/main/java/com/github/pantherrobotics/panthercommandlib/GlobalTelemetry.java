package com.github.pantherrobotics.panthercommandlib;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class GlobalTelemetry {
    public static Telemetry telemetry;

    /**
     * This will get a useable telemetry instance from one of the OpModes
     *
     * @return The telemetry instance
     */
    public static Telemetry get() {
        if (telemetry == null) {
            throw new IllegalStateException(
                    "GlobalTelemetry not initialized! Make sure setTelemetry() " +
                            "is called in your OpMode init() before creating any subsystems."
            );
        }
        return telemetry;
    }


    static void setTelemetryInstance(Telemetry telemetryInstance) {
        telemetry = telemetryInstance;
    }

    static void clear() {
        telemetry = null;
    }

}
