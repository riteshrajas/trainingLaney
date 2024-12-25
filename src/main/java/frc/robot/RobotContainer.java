package frc.robot;

import java.util.Map;

import com.ctre.phoenix6.Utils;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;
import com.pathplanner.lib.commands.PathPlannerAuto;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.DriveForwardCommand;
import frc.robot.commands.swerve.FODC;
import frc.robot.constants.*;
import frc.robot.constants.RobotMap.SafetyMap;
import frc.robot.constants.RobotMap.SensorMap;
import frc.robot.constants.RobotMap.UsbMap;
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
    private CommandPS5Controller driverController2;
    private SendableChooser<Command> autonChooser;
    private Telemetry telemetry;


    public RobotContainer() {
        double swerveSpeedMultiplier = 0.4;
        driverController = UsbMap.driverController;
        operatorController = UsbMap.operatorController;
        driverController2 = new CommandPS5Controller(2);
        
        swerveSubsystem = new SwerveSubsystem(
                Subsystems.SWERVE_DRIVE ,
                Subsystems.SWERVE_DRIVE.getNetworkTable() ,
                SensorMap.GYRO_PORT ,
                driverController
        );

        
    

        
        
        DrivetrainConstants.drivetrain.setDefaultCommand(
            new ParallelCommandGroup(
                        // Apply Deadband to the left joystick inputs
                        new FODC(driverController, swerveSubsystem)
       
                    // DrivetrainConstants.drivetrain.applyRequest(() -> DrivetrainConstants.drive
                    //         .withVelocityX(driverController.getLeftY() * SafetyMap.kMaxSpeed * SafetyMap.kMaxSpeedChange)
                    //         .withVelocityY(driverController.getLeftX() * SafetyMap.kMaxSpeed * SafetyMap.kMaxSpeedChange)
                    //         .withRotationalRate(-driverController.getRightX() * SafetyMap.kMaxAcceleration * SafetyMap.kAngularRateMultiplier)))
            )
        );

        // autonChooser = new SendableChooser<Command>();
        autonChooser = AutoBuilder.buildAutoChooser();
        setupNamedCommands();
        setupPaths();
        configureBindings();
        telemetry = new Telemetry(SafetyMap.kMaxSpeed);
        DrivetrainConstants.drivetrain.registerTelemetry(telemetry::telemeterize);
        DrivetrainConstants.drivetrain.configureAutoBuilder();
    
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
        autonChooser.addOption("Path 1", new DriveForwardCommand(swerveSubsystem, 0.5, 10)); // Move forward at 50% speed for 10 seconds
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


