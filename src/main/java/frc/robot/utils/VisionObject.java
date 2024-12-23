
package frc.robot.utils;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.constants.RobotMap.VisionMap;

public class VisionObject {
    private double x;
    private double y;
    private double area;
    private ObjectType type;
    private static NetworkTable table;


    public VisionObject(double x, double y, double area, ObjectType type) {
        table = NetworkTableInstance.getDefault().getTable(type.getTable());
        this.x = x;
        this.y = y;
        this.area = area;
        this.type = type;
    }

    public void SetNTEntry(NetworkTable tableInstance) {
        NetworkTableEntry txEntry = tableInstance.getEntry("tx");
        NetworkTableEntry tyEntry = tableInstance.getEntry("ty");
        NetworkTableEntry taEntry = tableInstance.getEntry("ta");


        // Initialize the values
        this.x = txEntry.getDouble(0);
        this.y = tyEntry.getDouble(0);
        this.area = taEntry.getDouble(0);
    }

    public double getX() {
        return x;
    }

    public boolean isPresent(){
        return table.getEntry("tv").getDouble(0) == 1.0;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public void update(double x, double y, double area) {
        this.x = x;
        this.y = y;
        this.area = area;
    }

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }

    public double[] getAngle() {
        double[] angle = new double[2];
                double nx = ((double) 1 / ( type.getCameraWidth()/ 2)) * (x - ( ObjectType.APRIL_TAG.getCameraWidth() / 2 - 0.5));
                double ny = ((double) 1 / ( type.getCameraHeight() / 2)) * (y - (ObjectType.APRIL_TAG.getCameraWidth() / 2 - 0.5));
                double vpw = 2.0 * Math.tan(type.getHorizontalFov() / 2);
                double vph = 2.0 * Math.tan(type.getVerticalFov() / 2);
                double x = vpw / 2 * nx;
                double y = vph / 2 * ny;
                double ax = Math.atan2(1, x);
                double ay = Math.atan2(1, y);
                angle[0] = Math.toDegrees(ax);
                angle[1] = Math.toDegrees(ay);
                return angle;
    }

    public double getYaw() {
        return getAngle()[0];
    }

    public double getPitch() {
        return getAngle()[1];
    }

    public double getDistance() {
        double targetOffsetAngle_Vertical = y;
        double limelightMountAngleDegrees = VisionMap.cameraAngle;
        double limelightLensHeightMetres = VisionMap.cameraHeight;
        double goalHeightMetres = VisionMap.targetHeight;
        double angleToGoalDegrees = limelightMountAngleDegrees + targetOffsetAngle_Vertical;
        double angleToGoalRadians = angleToGoalDegrees * (3.14159 / 180.0);
        return (goalHeightMetres - limelightLensHeightMetres) / Math.tan(angleToGoalRadians);
    }



}