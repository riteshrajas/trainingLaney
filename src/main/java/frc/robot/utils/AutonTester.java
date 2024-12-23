package frc.robot.utils;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.Command;
import java.util.HashMap;
import java.util.Map;

public class AutonTester {
    private Map<String, Command> commandMap;

    public AutonTester(Object[] Group) {
        commandMap = new HashMap<>();
        for (int i = 0; i < Group.length; i += 2) {
            String label = Group[i].toString();
            Command command = (Command) Group[i + 1];
            commandMap.put(label, command);
            Shuffleboard.getTab("Auton Tester").add(label, command);
        }
    }

}