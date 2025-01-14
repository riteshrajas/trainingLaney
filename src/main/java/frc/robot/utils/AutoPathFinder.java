package frc.robot.utils;

import java.io.IOException;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import com.pathplanner.lib.util.FileVersionException;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.constants.RobotMap.SafetyMap.AutonConstraints;

public class AutoPathFinder {

    public static Command GotoPath(String pathName) {
        try {
            return new ParallelCommandGroup(AutoBuilder.pathfindThenFollowPath(PathPlannerPath.fromPathFile(pathName), AutonConstraints.kPathConstraints));
        } catch (FileVersionException e) {
            System.out.println("Failed to load path: " + pathName);
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Failed to load path: " + pathName);
            e.printStackTrace();
        } catch (org.json.simple.parser.ParseException e) {
            e.printStackTrace();
        }
       return null;
    }
}
