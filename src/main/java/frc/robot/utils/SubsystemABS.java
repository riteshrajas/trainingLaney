package frc.robot.utils;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class SubsystemABS extends SubsystemBase {
    protected Subsystems part;
    protected ShuffleboardTab tab;
    protected NetworkTable ntTable;
    protected String tabName;
    protected Object instance;

    public SubsystemABS(Subsystems part, String tabName) {
        this.tabName = tabName;
        this.part = part;
        try {
            this.tab = Shuffleboard.getTab(tabName);
        } catch (IllegalArgumentException e) {
            this.tab = Shuffleboard.getTab(tabName + " - New");
        };
        setupNetworkTables(part.toString());
        instance = this;
        init();
        

    }

    public SubsystemABS() {
        init();
    }

    public Subsystems getPart() {
        return part;
    }

    public void setPart(Subsystems part) {
        this.part = part;
    }

    public ShuffleboardTab getTab() {
        
        return tab;
    }

    public  Object getInstance() {
        return instance;
    }

    public void setupNetworkTables(String part) {
        ntTable = NetworkTableInstance.getDefault().getTable(part);
    }


    public abstract void init();
    @Override
    public abstract void periodic();

    @Override
    public abstract void simulationPeriodic();

    public abstract void setDefaultCommand();

    public abstract  boolean isHealthy();

    public abstract void Failsafe();
}