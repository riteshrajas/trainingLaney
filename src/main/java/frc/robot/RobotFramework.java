package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.RobotMap.SafetyMap;
import frc.robot.subsystems.swerve.CommandSwerveDrivetrain;
import frc.robot.subsystems.swerve.SwerveSubsystem;
import frc.robot.utils.DrivetrainConstants;

public class RobotFramework {

    public Command HologenicDrive(CommandXboxController driverController, SwerveSubsystem swerveSubsystem,
            CommandSwerveDrivetrain drivetrain) {
        return new ParallelCommandGroup(
                DrivetrainConstants.drivetrain.applyRequest(() -> DrivetrainConstants.drive
                        .withVelocityX(driverController.getLeftY() * SafetyMap.kMaxSpeed * SafetyMap.kMaxSpeedChange)
                        .withVelocityY(driverController.getLeftX() * SafetyMap.kMaxSpeed * SafetyMap.kMaxSpeedChange)
                        .withRotationalRate(-driverController.getRightX() * SafetyMap.kMaxAngularRate
                                * SafetyMap.kAngularRateMultiplier)));
    }

}