package frc.robot.utils;

import com.ctre.phoenix6.swerve.SwerveModule;
import com.ctre.phoenix6.swerve.SwerveRequest;

import frc.robot.constants.RobotMap.SafetyMap;
import frc.robot.subsystems.swerve.CommandSwerveDrivetrain;
import frc.robot.subsystems.swerve.generated.TunerConstants;

public final class DrivetrainConstants {
    public static CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    public static final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(SafetyMap.kMaxSpeed * 0.1)
            .withRotationalDeadband(SafetyMap.kMaxAngularRate * 0.1)
            .withDriveRequestType(SwerveModule.DriveRequestType.OpenLoopVoltage);

    public static final SwerveRequest.FieldCentricFacingAngle autoAim = new SwerveRequest.FieldCentricFacingAngle()
            .withDriveRequestType(SwerveModule.DriveRequestType.OpenLoopVoltage);

        public static final SwerveRequest.RobotCentric robotDrive = new SwerveRequest.RobotCentric()
            .withDeadband(SafetyMap.kMaxSpeed * 0.1)
            .withRotationalDeadband(SafetyMap.kMaxAngularRate * 0.1)
            .withDriveRequestType(SwerveModule.DriveRequestType.OpenLoopVoltage);

    public static final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    public static final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();
}
