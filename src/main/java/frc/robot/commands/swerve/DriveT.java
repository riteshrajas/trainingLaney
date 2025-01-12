// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of the
// WPILib BSD license file in the root directory of this project.

package frc.robot.commands.swerve;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.constants.RobotMap.SafetyMap.AutonConstraints;
import frc.robot.subsystems.swerve.SwerveSubsystem;
import frc.robot.subsystems.vision.camera.Camera;
import frc.robot.utils.PathPair;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class DriveT extends Command {
  private final SwerveSubsystem swerve;
  private final Camera tagCamera;
  private int tag;
  private final PathPair[] paths;
  private final boolean left;

  private static final PathPair[] PATHS = {
    new PathPair(0, 1, "1pathleft", "1pathright"),
    new PathPair(2, 3, "3pathleft", "3pathright"),
    new PathPair(4, 5, "5pathleft", "5pathright"),
    new PathPair(6, 7, "7pathleft", "7pathright"),
    new PathPair(8, 9, "9pathleft", "9pathright"),
    new PathPair(10, 11, "11pathleft", "11pathright"),
  };

  /** Creates a new DriveT. */
  public DriveT(SwerveSubsystem swerve, Camera tagCamera, boolean left) {
    this.swerve = swerve;
    this.tagCamera = tagCamera;
    this.left = left;
    this.paths = PATHS;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    tag = tagCamera.getLastseenAprilTag();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    try {
      String pathFile = left ? paths[tag].leftpath : paths[tag].rightpath;
      AutoBuilder.pathfindThenFollowPath(
        PathPlannerPath.fromPathFile(pathFile), AutonConstraints.kPathConstraints);
    } catch (FileVersionException | IOException | ParseException e) {
      e.printStackTrace();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
