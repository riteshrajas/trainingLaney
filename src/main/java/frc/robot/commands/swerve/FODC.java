// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.swerve;

import java.util.Map;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.RobotMap.SafetyMap;
import frc.robot.subsystems.swerve.SwerveSubsystem;
import frc.robot.utils.DrivetrainConstants;
@SuppressWarnings("unused")
public class FODC extends Command {

        private final CommandXboxController driverController;
        private final SwerveSubsystem subsystem;
        private double angle;
        private double lastAngle = 0.0;
        private double snappedAngle;
        private double angleDiff;


        public FODC(CommandXboxController driverController, SwerveSubsystem subsystem) {
            addRequirements(DrivetrainConstants.drivetrain, subsystem);
            this.driverController = driverController;
            this.subsystem = subsystem;
            subsystem.printcontroller();  

            subsystem.getTab().addNumber("Angle", () -> snappedAngle)
                    .withWidget(BuiltInWidgets.kGyro)
                    .withPosition(0, 0)
                    .withProperties(Map.of("majorTickSpacing", SafetyMap.FODC.LineCount, "startingAngle", 0));
                    
            subsystem.getTab().addNumber("FODC/Angle", () -> angle)
                    .withWidget(BuiltInWidgets.kGyro)
                    .withPosition(0, 0)
                    .withProperties(Map.of("majorTickSpacing", SafetyMap.FODC.LineCount, "startingAngle", 0));
        }

        @Override
        public void execute() {
            super.execute();
            // Apply Deadband to the controller inputs
            double rightStickX = subsystem.applyDeadband(driverController.getRightX(), 0.05);
            double rightStickY = subsystem.applyDeadband(driverController.getRightY(), 0.05);
            double leftStickX = subsystem.applyDeadband(driverController.getLeftX(), 0.05);
            double leftStickY = subsystem.applyDeadband(driverController.getLeftY(), 0.05);
            double robotAngle;

            if (rightStickX != 0 || rightStickY != 0) {
                angle = Math.toDegrees(Math.atan2(rightStickY, rightStickX)) - 90; // Adjust angle by subtracting 90 degrees
                lastAngle = angle; // Update last angle when joystick is moved
            } else {
                angle = lastAngle; // Use last angle when joystick is not moved
            }

            snappedAngle = subsystem.snapToNearestLine(angle, SafetyMap.FODC.LineCount);
            robotAngle = subsystem.getRobotAngle();
            angleDiff = snappedAngle - robotAngle;

            if (angleDiff > 180) {
                angleDiff -= 360;
            } else if (angleDiff < -180) {
                angleDiff += 360;
            }

            double output = subsystem.getPIDRotation(angleDiff);

            subsystem.setControl(leftStickY, robotAngle, output);
        }
    }