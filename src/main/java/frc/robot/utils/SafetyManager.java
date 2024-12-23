package frc.robot.utils;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.HashMap;
import java.util.Map;

public class SafetyManager extends SubsystemBase {
    private static ShuffleboardTab safetyTab;
    private static Map<SubsystemABS, SimpleWidget> subsystemWidgets = new HashMap<>();


    public SafetyManager( SubsystemABS... subsystems) {
        init();
        for (SubsystemABS subsystem : subsystems) {
            subsystemWidgets.put(subsystem, safetyTab.add(subsystem.getName(), subsystem.isHealthy()));
        }

    }

    public static void init() {
        safetyTab = Shuffleboard.getTab("Safety");
    }

    @Override
    public void periodic() {
        checkSubsystems();
        reportSubsystemStatus();
    }

    public static void checkSubsystems() {
        for (SubsystemABS subsystem : subsystemWidgets.keySet()) {
            subsystemWidgets.get(subsystem).getEntry().setBoolean(subsystem.isHealthy());
        }
    }

    private void reportSubsystemStatus() {
        for (SubsystemABS subsystem : subsystemWidgets.keySet()) {
            if (!subsystem.isHealthy()) {
                System.out.println(subsystem.getName() + " is not healthy" + " at " + System.currentTimeMillis());
                DriverStation.reportError(subsystem.getName() + " is not healthy", false);
                subsystem.Failsafe();
            }
        }
    }
}