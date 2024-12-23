/*
 * *****************************************************************************
 *  * Copyright (c) 2024 FEDS 201. All rights reserved.
 *  *
 *  * This codebase is the property of FEDS 201 Robotics Team.
 *  * Unauthorized copying, reproduction, or distribution of this code, or any
 *  * portion thereof, is strictly prohibited.
 *  *
 *  * This code is provided "as is" and without any express or implied warranties,
 *  * including, without limitation, the implied warranties of merchantability
 *  * and fitness for a particular purpose.
 *  *
 *  * For inquiries or permissions regarding the use of this code, please contact
 *  * feds201@gmail.com
 *  ****************************************************************************
 *
 */

package frc.robot.utils;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.Command;

public abstract class VisionABC extends SubsystemABS {


  protected ShuffleboardTab tab;

  public VisionABC() {
    super(Subsystems.VISION, "Vision");
    tab = getTab();
  }

  public abstract boolean CheckTarget();
  public abstract Translation2d GetTarget(VisionObject object);
  public abstract void setPipeline(int pipeline);
  public abstract void setLEDMode(int mode);
  public abstract void setCamMode(int mode);
  public abstract Command BlinkLED();
  public abstract Command TurnOffLED();

  @Override
  public abstract void simulationPeriodic();

  public Boolean enabled;
}
