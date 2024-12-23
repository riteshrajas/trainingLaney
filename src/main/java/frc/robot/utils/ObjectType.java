package frc.robot.utils;

public enum ObjectType {
    APRIL_TAG("April Tag", "This object type is for AprilTags", "limelight-notes", 480, 640, 0.0, 59.6, 45.7),
    INFINITE_CHARGE_BALLS("Infinite Charge Balls", "This object type is for Infinite Charge Balls", "limelight-notes", 480, 640, 0.0, 59.6, 45.7),
    NOTE("Note", "This object type is for Notes", "limelight-notes", 480, 640, 0.0, 59.6, 45.7);

    private final String name;
    private final String description;
    private final String table;
    private final int cameraHeight;
    private final int cameraWidth;
    private final double targetHeight;
    private final double horizontalFov;
    private final double verticalFov;

    ObjectType(String name, String description, String table, int cameraHeight, int cameraWidth, double targetHeight, double horizontalFov, double verticalFov) {
        this.name = name;
        this.description = description;
        this.table = table;
        this.cameraHeight = cameraHeight;
        this.cameraWidth = cameraWidth;
        this.targetHeight = targetHeight;
        this.horizontalFov = horizontalFov;
        this.verticalFov = verticalFov;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTable() {
        return table;
    }

    public int getCameraHeight() {
        return cameraHeight;
    }

    public int getCameraWidth() {
        return cameraWidth;
    }

    public double getTargetHeight() {
        return targetHeight;
    }

    public double getHorizontalFov() {
        return horizontalFov;
    }

    public double getVerticalFov() {
        return verticalFov;
    }

    public String getNetworkTable() {
        return (table);
    }
    public static ObjectType getInstance(ObjectType type) {
        switch (type) {
            case APRIL_TAG:
                return APRIL_TAG;
            case INFINITE_CHARGE_BALLS:
                return INFINITE_CHARGE_BALLS;
            case NOTE:
                return NOTE;
            // Add other object types as needed
            default:
                return null;
        }
    }
}
