// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

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

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class DriveT extends Command {
  private final SwerveSubsystem swerve;
  private final Camera tagCamera;
  private int tag;
  //string indexes correspond with tag names
  private final String[] pathsLeft = {null, "1pathleft", "2pathleft", "3pathleft", null, null, "6pathleft", "7pathleft", "8pathleft", "9pathleft", "10pathleft", "11pathleft", "12pathleft", "13pathleft", null, null, "16pathleft", "17pathleft", "18pathleft", "19pathleft", "20pathleft", "21pathleft", "22pathleft"};
  private final String[] pathsRight = {null, "1path", "2path", "3path", null, null, "6path", "7path", "8path", "9path", "10path", "11path", "12path", "13path", null, null, "16path", "17path", "18path", "19path", "20path", "21path", "22path"};
  private String[] paths;
  private final boolean left;

  /** Creates a new DriveT. */
  public DriveT(SwerveSubsystem swerve, Camera tagCamera, boolean left) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.swerve = swerve;
    this.tagCamera = tagCamera;
    this.left = left;
  
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    tag = tagCamera.getLastseenAprilTag();
    if (left){
      paths = pathsLeft;
    } else {
      paths = pathsRight;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      try {
          
                  AutoBuilder.pathfindThenFollowPath(
                        PathPlannerPath.fromPathFile(paths[tag]),  AutonConstraints.kPathConstraints);
        } catch (FileVersionException | IOException | ParseException e) {
            // TODO Auto-generated catch block
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
