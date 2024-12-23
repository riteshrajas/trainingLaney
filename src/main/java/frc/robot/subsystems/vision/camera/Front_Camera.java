package frc.robot.subsystems.vision.camera;


import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.utils.ObjectType;
import frc.robot.utils.VisionABC;
import frc.robot.utils.VisionObject;

public class Front_Camera extends VisionABC {
	private VisionObject object;
	private ShuffleboardLayout layout;
	private ObjectType objectType;
	private NetworkTable table;

	private DoubleSupplier tx;
	private DoubleSupplier ty;
	private DoubleSupplier ta;

	public Front_Camera() {
		super();
	}

		@Override
	public void init() {
		objectType = ObjectType.INFINITE_CHARGE_BALLS;
		object = new VisionObject(0,0,0,objectType);
		layout = tab.getLayout(objectType.getName(), "List Layout");
		table = NetworkTableInstance.getDefault().getTable(objectType.getNetworkTable());

		layout.addNumber("tx", tx);
		layout.addNumber("ty", ty);
		layout.addNumber("ta", ta);
	}

	@Override
	public void periodic() {
		if (enabled) {
			object.update(
				table.getEntry("tx").getDouble(0.0),
				table.getEntry("ty").getDouble(0.0),
				table.getEntry("ta").getDouble(0.0)
				);

			tx = () -> object.getX();
			ty = () -> object.getY();
			ta = () -> object.getArea();
		}
	}

	@Override
	public boolean CheckTarget() {
		return object.isPresent();
	}

	@Override
	public Translation2d GetTarget(VisionObject object) {
		return new Translation2d(object.getX(), object.getY());
	}

	@Override
	public void setPipeline(int pipeline) {
		table.getEntry("pipeline").setNumber(pipeline);
	}

	@Override
	public void setLEDMode(int mode) {
		table.getEntry("ledMode").setNumber(mode);
	}

	@Override
	public void setCamMode(int mode) {
		table.getEntry("camMode").setNumber(mode);
	}

	@Override
	public Command BlinkLED() {
		return runOnce(
			() -> setLEDMode(2)
		);
	}

	@Override
	public Command TurnOffLED() {
		return runOnce(() -> setLEDMode(0));
	}
	
	@Override
	public void simulationPeriodic() {
		if (enabled) {
			object.update(
				Math.random() * 100,
				Math.random() * 100,
				Math.random() * 100
				);

			tx = () -> object.getX();
			ty = () -> object.getY();
			ta = () -> object.getArea();
		}
	}



	@Override
	public void setDefaultCommand() {}	
	
	@Override
	public boolean isHealthy() {
		return true;
	}

	@Override
	public void Failsafe() {
		setLEDMode(0);
		setCamMode(0);
		setPipeline(0);
	}


}

