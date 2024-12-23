// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.swerve;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.RobotMap.SafetyMap;
import frc.robot.utils.DrivetrainConstants;


public class GenericDrivetrain extends Command {

        private final CommandXboxController driverController;
        public GenericDrivetrain(CommandXboxController driverController) {
            addRequirements(DrivetrainConstants.drivetrain);
            this.driverController = driverController;
        //     swerveSubsystem.printcontroller();
        }

        @Override
        public void execute() {
            super.execute();
            // Apply Deadband to the controller inputs

            new ParallelCommandGroup(
                    DrivetrainConstants.drivetrain.applyRequest(() -> DrivetrainConstants.drive
                            .withVelocityX(-driverController.getLeftY() * SafetyMap.kMaxSpeed * SafetyMap.kMaxSpeedChange)
                            .withVelocityY(-driverController.getLeftX() * SafetyMap.kMaxSpeed * SafetyMap.kMaxSpeedChange)
                            .withRotationalRate(driverController.getLeftX() * SafetyMap.kMaxAcceleration * SafetyMap.kAngularRateMultiplier)));
        }



@Override
public boolean isFinished(){
        return false;
}
     
    }

 