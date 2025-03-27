// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.elevator.elevator;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class raisearm extends Command {
  private elevator m_Elevator;
  private PIDController m_pid;

  /** Creates a new raisearm. */
  public raisearm(elevator _Elevator, double setpoint) {
    addRequirements(_Elevator);
    m_Elevator = _Elevator;
    m_pid = m_Elevator.getit();
    m_pid.setSetpoint(setpoint);
   
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_Elevator.setSpeed(m_pid.calculate(m_Elevator.getAngle()));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return m_pid.atSetpoint();
    
  }
}
