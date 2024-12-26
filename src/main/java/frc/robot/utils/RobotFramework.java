package frc.robot.utils;

import com.pathplanner.lib.auto.AutoBuilder;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.RobotMap.SafetyMap;
import frc.robot.subsystems.swerve.CommandSwerveDrivetrain;
import frc.robot.subsystems.swerve.SwerveSubsystem;

@SuppressWarnings("unused")
public class RobotFramework {
        
        

        public Command ConfigureHologenicDrive(CommandXboxController driverController,
                        SwerveSubsystem swerveSubsystem) {
                return new ParallelCommandGroup(
                                DrivetrainConstants.drivetrain.applyRequest(() -> DrivetrainConstants.drive
                                                .withVelocityX(driverController.getLeftY() * SafetyMap.kMaxSpeed
                                                                * SafetyMap.kMaxSpeedChange)
                                                .withVelocityY(driverController.getLeftX() * SafetyMap.kMaxSpeed
                                                                * SafetyMap.kMaxSpeedChange)
                                                .withRotationalRate(-driverController.getRightX()
                                                                * SafetyMap.kMaxAngularRate
                                                                * SafetyMap.kAngularRateMultiplier)));
        }

        public Command ConfigureBeyBlade(CommandXboxController driverController, SwerveSubsystem swerveSubsystem) {
                return new ParallelCommandGroup(
                                DrivetrainConstants.drivetrain.applyRequest(() -> DrivetrainConstants.drive
                                                .withVelocityX(driverController.getLeftY() * SafetyMap.kMaxSpeed
                                                                * SafetyMap.kMaxSpeedChange)
                                                .withVelocityY(driverController.getLeftX() * SafetyMap.kMaxSpeed
                                                                * SafetyMap.kMaxSpeedChange)
                                                .withRotationalRate(5 * SafetyMap.kMaxAngularRate
                                                                * SafetyMap.kAngularRateMultiplier)));
        }

        public Command ConfigureTankDrive(CommandXboxController driverController, SwerveSubsystem swerveSubsystem) {
                return new ParallelCommandGroup(
                                DrivetrainConstants.drivetrain.applyRequest(() -> {
                                        // Get joystick inputs
                                        double leftInput = -driverController.getLeftY(); // Left joystick controls the
                                                                                         // left side
                                        double rightInput = -driverController.getRightY(); // Right joystick controls
                                                                                           // the right side

                                        // Apply deadbands to inputs
                                        double leftSpeed = swerveSubsystem.applyDeadband(leftInput, 0.05)
                                                        * SafetyMap.kMaxSpeed;
                                        double rightSpeed = swerveSubsystem.applyDeadband(rightInput, 0.05)
                                                        * SafetyMap.kMaxSpeed;

                                        // Translate tank drive logic into swerve drive wheel velocities
                                        double velocityX = (leftSpeed + rightSpeed) / 2.0; // Average of left and right
                                                                                           // for forward/backward
                                                                                           // motion
                                        double rotationRate = (rightSpeed - leftSpeed) / 3; // Difference controls
                                                                                            // rotation
                                        // Disable Field Relative Drive

                                        // Return a new swerve drive state
                                        return DrivetrainConstants.robotDrive
                                                        .withVelocityX(velocityX)
                                                        .withVelocityY(0) // No lateral movement for tank drive
                                                        .withRotationalRate(rotationRate * SafetyMap.kMaxAngularRate);
                                }));
        }

        public Command ConfigureArcadeDrive(CommandXboxController driverController, SwerveSubsystem swerveSubsystem) {
                return new ParallelCommandGroup(

                                DrivetrainConstants.drivetrain.applyRequest(() -> DrivetrainConstants.robotDrive
                                                .withVelocityX(driverController.getLeftY() * SafetyMap.kMaxSpeed)
                                                .withRotationalRate(driverController.getRightX()
                                                                * SafetyMap.kMaxAngularRate)));
        }

        public Command ConfigureFODC(CommandXboxController controller, SwerveSubsystem swerve) {
                return new ParallelCommandGroup(
                                DrivetrainConstants.drivetrain.applyRequest(() -> {

                                        double rightStickX = swerve.applyDeadband(controller.getRightX(), 0.09);
                                        double rightStickY = swerve.applyDeadband(controller.getRightY(), 0.09);
                                        double angle;
                                        double lastAngle = 0;
                                        double snappedAngle;
                                        double angleDiff;

                                        if (rightStickX != 0 || rightStickY != 0) {
                                                angle = Math.toDegrees(Math.atan2(-rightStickY, -rightStickX)) - 90; // Adjust
                                                                                                                     // angle
                                                                                                                     // by
                                                                                                                     // subtracting
                                                                                                                     // 90
                                                                                                                     // degrees
                                                lastAngle = angle; // Update last angle when joystick is moved
                                        } else {
                                                angle = lastAngle; // Use last angle when joystick is not moved
                                        }

                                        snappedAngle = swerve.snapToNearestLine(angle, SafetyMap.FODC.LineCount);
                                        double robotAngle = swerve.getRobotAngle();
                                        angleDiff = snappedAngle - robotAngle;

                                        if (angleDiff > 180) {
                                                angleDiff -= 360;
                                        } else if (angleDiff < -180) {
                                                angleDiff += 360;
                                        }

                                        double anglerr = swerve.getPIDRotation(angleDiff);
                                        return DrivetrainConstants.drive
                                                        .withVelocityX(controller.getLeftY() * SafetyMap.kMaxSpeed)
                                                        .withVelocityY(controller.getLeftX() * SafetyMap.kMaxSpeed)
                                                        .withRotationalRate(anglerr * SafetyMap.kMaxAngularRate);

                                }));

        }

        public Command ConfigureOrbitMode(CommandXboxController controller, SwerveSubsystem swerve) {

                return new ParallelCommandGroup(
                                DrivetrainConstants.drivetrain.applyRequest(() -> {
                                        double angle = Math.atan2(controller.getLeftY(), controller.getLeftX());
                                        double radius = Math.hypot(controller.getLeftX(), controller.getLeftY());
                                        // Return a new swerve drive state
                                        return DrivetrainConstants.robotDrive
                                                        .withVelocityX(Math.cos(angle) * radius * SafetyMap.kMaxSpeed)
                                                        .withVelocityY(Math.sin(angle) * radius * SafetyMap.kMaxSpeed)
                                                        .withRotationalRate(angle * SafetyMap.kMaxAngularRate);
                                }));

        }


}