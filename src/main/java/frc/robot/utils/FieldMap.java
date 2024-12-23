package frc.robot.utils;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;


public class FieldMap {
    private ShuffleboardTab tab;
    private Field2d field;

    public FieldMap() {
        // Initialize the Shuffleboard tab
        tab = Shuffleboard.getTab("Swerve Drive");

        // Create a Field2d object to represent the field
        field = new Field2d();

        // Add the Field2d to the Shuffleboard tab with the "kField" widget
        tab.add("Robot Map", field)
                .withWidget(BuiltInWidgets.kField) // Use the kField widget type
                .withPosition(0, 0)               // Set the position on the Shuffleboard tab
                .withSize(4, 3);                  // Set size (width x height in grid units)

        // Initialize the robot position on the field
        field.setRobotPose(new Pose2d(Units.feetToMeters(0), Units.feetToMeters(0), new Rotation2d(0)));
    }

    // Example to update robot position
    public void updateRobotPose(Pose2d pose) {
        field.setRobotPose(pose);
    }

    // Example to update field object positions (e.g., game elements)
    public void updateGameElement(String name, Pose2d pose) {
        field.getObject(name).setPose(pose);
    }
}
