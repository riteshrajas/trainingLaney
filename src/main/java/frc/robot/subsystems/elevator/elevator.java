// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.elevator;

import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class elevator extends SubsystemBase {
  private TalonFX motor1 = new TalonFX(19);
  private CANcoder angulator = new CANcoder(93);
  private PIDController pid  = new PIDController(0, 0, 0);

  /** Creates a new elevator. */
  public elevator() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setSpeed(double speed){
    motor1.set(speed);
  }

  public double getAngle(){
    return angulator.getAbsolutePosition().getValueAsDouble();
  }

  public void setSetpoint(double setpoint){
    pid.setSetpoint(setpoint);
  }

  public PIDController getit(){
    return pid;
  }




}
