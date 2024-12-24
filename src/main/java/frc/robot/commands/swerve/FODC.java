package frc.robot.commands.swerve;

import java.util.Map;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.RobotMap.SafetyMap;
import frc.robot.subsystems.swerve.SwerveSubsystem;
import frc.robot.utils.DrivetrainConstants;

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
        // Apply Deadband to the left joystick inputs
        double leftStickX = subsystem.applyDeadband(driverController.getLeftX(), 0.05);
        double leftStickY = subsystem.applyDeadband(driverController.getLeftY(), 0.05);
        double rightStickX = subsystem.applyDeadband(driverController.getRightX(), 0.05);
        double rightStickY = subsystem.applyDeadband(driverController.getRightY(), 0.05);

        if (leftStickX != 0 || leftStickY != 0) {
            angle = Math.toDegrees(Math.atan2(leftStickY, leftStickX)) - 90; // Adjust angle by subtracting 90 degrees
            lastAngle = angle; // Update last angle when joystick is moved
        } else {
            angle = lastAngle; // Use last angle when joystick is not moved
        }

        snappedAngle = subsystem.snapToNearestLine(angle, SafetyMap.FODC.LineCount);
        double robotAngle = subsystem.getRobotAngle();
        angleDiff = snappedAngle - robotAngle;

        if (angleDiff > 180) {
            angleDiff -= 360;
        } else if (angleDiff < -180) {
            angleDiff += 360;
        }

        // Calculate turn power based on angle difference
        double turnPower = 1.5 * calculateTurnPower(angleDiff);
         new ParallelCommandGroup(
                subsystem.getDrivetrain().applyRequest(() -> DrivetrainConstants.drive
                            .withVelocityX(rightStickX * SafetyMap.kMaxSpeed * SafetyMap.kMaxSpeedChange)
                            .withVelocityY(rightStickX * SafetyMap.kMaxSpeed * SafetyMap.kMaxSpeedChange)
                            .withRotationalRate(turnPower * SafetyMap.kMaxAcceleration * SafetyMap.kAngularRateMultiplier)));

        SmartDashboard.putNumber("Angle Difference", angleDiff);
    }

    /**
     * Calculates turn power based on angle difference.
     * A simple proportional control is used.
     * 
     * @param angleDiff The difference between the desired angle and the current angle
     * @return Turn power (-1 to 1)
     */
    private double calculateTurnPower(double angleDiff) {
        double kTurn = 0.01; // Tuning constant for proportional control
        double maxTurnPower = 5; // Limit maximum turn power

        // Scale turn power proportionally
        double turnPower = angleDiff * kTurn;

        // Clamp turn power to the maximum limit
        return Math.max(-maxTurnPower, Math.min(turnPower, maxTurnPower));
    }

    @Override
    public boolean isFinished() {
        return Math.abs(angleDiff) < 2.0; // Command finishes when within 2 degrees of the target
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.setControl(0, 0, 0); // Stop the robot when the command ends
    }
}
