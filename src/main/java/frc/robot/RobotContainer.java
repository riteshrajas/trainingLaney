package frc.robot;

import java.util.Map;

import com.ctre.phoenix6.Utils;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.constants.RobotMap.*;
import frc.robot.subsystems.swerve.SwerveSubsystem;
import frc.robot.utils.DrivetrainConstants;
import frc.robot.utils.SubsystemABS;
import frc.robot.utils.Subsystems;
import frc.robot.utils.Telemetry;

@SuppressWarnings("unused") // DO NOT REMOVE

public class RobotContainer {

    private SwerveSubsystem swerveSubsystem;
    private CommandXboxController driverController;
    private CommandXboxController operatorController;
    private SendableChooser<Command> autonChooser;
    private Telemetry telemetry;


    public RobotContainer() {
        double swerveSpeedMultiplier = 0.4;
        driverController = UsbMap.driverController;
        operatorController = UsbMap.operatorController;

        swerveSubsystem = new SwerveSubsystem(
                Subsystems.SWERVE_DRIVE ,
                Subsystems.SWERVE_DRIVE.getNetworkTable() ,
                SensorMap.GYRO_PORT ,
                driverController
        );


    

        
        // DrivetrainConstants.drivetrain.setDefaultCommand(new GenericDrivetrain(driverController));
        DrivetrainConstants.drivetrain.setDefaultCommand(

        DrivetrainConstants.drivetrain.applyRequest(() -> DrivetrainConstants.drive
        .withVelocityX(-driverController.getLeftY() * SafetyMap.kMaxSpeed * SafetyMap.kMaxSpeedChange *swerveSpeedMultiplier)
        .withVelocityY(-driverController.getLeftX() * SafetyMap.kMaxSpeed * SafetyMap.kMaxSpeedChange *swerveSpeedMultiplier)
        .withRotationalRate(-driverController.getRightX() * SafetyMap.kMaxAcceleration * SafetyMap.kAngularRateMultiplier))
        );
        autonChooser = new SendableChooser<Command>();

        setupNamedCommands();
        setupPaths();
        configureBindings();
        telemetry = new Telemetry(SafetyMap.kMaxSpeed);
        DrivetrainConstants.drivetrain.registerTelemetry(telemetry::telemeterize);

    }


    private void configureBindings() { 
        driverController.start()
            .onTrue(DrivetrainConstants.drivetrain.runOnce(() -> DrivetrainConstants.drivetrain.seedFieldCentric()));

  
    }

    
    private void setupNamedCommands() {
        NamedCommands.registerCommand(
            "Field Relative", 
            DrivetrainConstants.drivetrain.runOnce(() -> DrivetrainConstants.drivetrain.seedFieldCentric())
        );
    }

    public void setupPaths() {
        autonChooser.setDefaultOption("Field Relative", NamedCommands.getCommand("Field Relative"));
        Shuffleboard.getTab(Subsystems.SWERVE_DRIVE.getNetworkTable()).add("Auton Chooser",autonChooser) .withSize(2, 1).withProperties(Map.of("position", "0, 0"));
    }

    public Command getAutonomousCommand() {

        return autonChooser.getSelected();
    }

    // DO NOT REMOVE
    public SubsystemABS[] SafeGuardSystems() {
        return new SubsystemABS[] {
                swerveSubsystem ,
        };
    }

    // ONLY RUNS IN TEST MODE
    public Object[] TestCommands() {
        return new Object[] {
        };
    }

    public Object[] TestAutonCommands() {
        return new Object[] {
        };
    }

}


