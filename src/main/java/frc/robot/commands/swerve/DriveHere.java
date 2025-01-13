// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of the
// WPILib BSD license file in the root directory of this project.

package frc.robot.commands.swerve;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swerve.SwerveSubsystem;
import java.io.IOException;
import org.json.simple.parser.ParseException;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;
import frc.robot.constants.RobotMap.SafetyMap.AutonConstraints;

public class DriveHere extends Command {
  private final SwerveSubsystem swerveSubsystem;
  private boolean pathFollowed = false;

  public DriveHere(SwerveSubsystem swerveSubsystem) {
    this.swerveSubsystem = swerveSubsystem;
    addRequirements(swerveSubsystem);
  }

  @Override
  public void initialize() {
    // Initialization code here
    pathFollowed = false;
    try {
        String pathName = "Pathto1"; // Replace with the actual path name
        System.out.println("Loading path: " + pathName);
        PathPlannerPath pathPlannerPath = PathPlannerPath.fromPathFile(pathName);
        if (pathPlannerPath != null) {
          AutoBuilder.pathfindThenFollowPath(pathPlannerPath, AutonConstraints.kPathConstraints);
          System.out.println("Following path: " + pathName);
          pathFollowed = true;
        } else {
          System.out.println("Failed to load path: " + pathName);
        }
      } catch (FileVersionException | IOException | ParseException e) {
        e.printStackTrace();
      }
  }

  @Override
  public void execute() {
    if (!pathFollowed) {
      
    }
  }

  @Override
  public void end(boolean interrupted) {
    swerveSubsystem.stop();
  }

  @Override
  public boolean isFinished() {
    return pathFollowed;
  }
}
