package frc.robot.utils;

public class PathPair {
    public int redAlliance;
    public int blueAlliance;
    public String leftpath;
    public String rightpath;


    public PathPair(int redAlliance, int blueAlliance, String leftpath, String rightpath) {
        this.redAlliance = redAlliance;
        this.blueAlliance = blueAlliance;
    }
    
    public String getLeftPath() {
        return leftpath;
    }

    public String getRightPath() {
        return rightpath;
    }

}