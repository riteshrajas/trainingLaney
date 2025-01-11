package frc.robot.commands.vision;

import com.ctre.phoenix6.hardware.CANrange;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swerve.SwerveSubsystem;
import frc.robot.utils.VisionObject;

public class RetrieveClosestGamePiece extends Command {
   
    private final SwerveSubsystem swerveSubsystem;
    private VisionObject visionTarget;
    private static final double MAX_SPEED = 0.5; // Maximum approach speed
    private static final double MIN_DISTANCE = 0.1; // Minimum distance threshold
    private static final double MAX_DISTANCE = 5.0; // Maximum tracking distance
    private CANrange range = new CANrange(55);
    public RetrieveClosestGamePiece(SwerveSubsystem swerveSubsystem, VisionObject visionTarget) {
        this.swerveSubsystem = swerveSubsystem;
        this.visionTarget = visionTarget;
        addRequirements(swerveSubsystem);
    }

    @Override
    public void initialize() {
        // Initialize telemetry
    }

    @Override
    public void execute() {
        System.out.println(range.getDistance());
        if (!visionTarget.isPresent()) {
            swerveSubsystem.driveRobotRelative(0, 0, 0);
            return;
        }

        // Get the current robot pose
        Pose2d robotPose = swerveSubsystem.getPose();
        double horizontalAngle = Math.toRadians(visionTarget.getX());
        double xPosition = visionTarget.getDistance() * Math.cos(horizontalAngle);
        double yPosition = visionTarget.getDistance() * Math.sin(horizontalAngle);
        Translation2d ballPosition = new Translation2d(xPosition, yPosition);

        // Calculate the direction to the ball
        Translation2d directionToBall = ballPosition.minus(robotPose.getTranslation());

        // Calculate distance to target
        double distance = directionToBall.getNorm();
        
        // Scale speed based on distance (closer = slower)
        double speedScaling = Math.min(distance / 2.0, 1.0);
        double xSpeed = directionToBall.getX() / distance * MAX_SPEED * speedScaling;
        double ySpeed = directionToBall.getY() / distance * MAX_SPEED * speedScaling;

        // Drive the robot towards the ball
        swerveSubsystem.driveRobotRelative(xSpeed, ySpeed, 0);
    }

    @Override
    public void end(boolean interrupted) {
        // Stop the robot
        swerveSubsystem.driveRobotRelative(0, 0, 0);
    }

    @Override
    public boolean isFinished() {
        if (!visionTarget.isPresent()) {
            return true;
        }

        // Check if the robot has reached the ball
        Pose2d robotPose = swerveSubsystem.getPose();
        double horizontalAngle = Math.toRadians(visionTarget.getX());
        double xPosition = visionTarget.getDistance() * Math.cos(horizontalAngle);
        double yPosition = visionTarget.getDistance() * Math.sin(horizontalAngle);
        Translation2d ballPosition = new Translation2d(xPosition, yPosition);
        double distance = robotPose.getTranslation().getDistance(ballPosition);
        
        return distance < MIN_DISTANCE || distance > MAX_DISTANCE;
    }
}