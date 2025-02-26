package strategies;

import sensors.SensorService;
import interfaces.IDriveStrategy;
import lejos.nxt.Motor;
import robot.Robot;

/**
 * This class implements a zigzag driving strategy where the robot adjusts its motor speeds based on sensor input.
 */
public class ZickZack implements IDriveStrategy {

	/** The only instance of this singleton class. */
	private static final ZickZack instance = new ZickZack();

	/** The low speed for motor movement. */
	private static final int LOW_SPEED = 100;

	/** The high speed for motor movement. */
	private static final int HIGH_SPEED = 300;

	/** The multiplier to adjust speed for zigzag movement. */
	private static final double SPEED_MULTIPLIER = 1.5;


	/**
	 * Private constructor to enforce the singleton pattern.
	 */
	private ZickZack() {
	}

	/**
	 * Returns the single instance of the ZigZag class.
	 *
	 * @return the instance of ZigZag
	 */
	public static ZickZack getInstance() {
		return instance;
	}

	/**
	 * No values to reset for this strategy.
	 */
	@Override
	public void resetValues() {
		// No values to reset
	}

	/**
	 * Adjusts motor speeds to implement a zigzag movement pattern based on sensor input.
	 *
	 * @param sensorService the sensor service providing sensor readings
	 */
	@Override
	public void act(SensorService sensorService) {
		int colorSensorValue = sensorService.colorSensor.getLightValue();
		if (colorSensorValue < Robot.getLightThreshold()) {
			Motor.A.setSpeed((int) (HIGH_SPEED * SPEED_MULTIPLIER));
			Motor.B.setSpeed((int) (LOW_SPEED * SPEED_MULTIPLIER));
		} else {
			Motor.B.setSpeed((int) (HIGH_SPEED * SPEED_MULTIPLIER));
			Motor.A.setSpeed((int) (LOW_SPEED * SPEED_MULTIPLIER));
		}
		Motor.B.forward();
		Motor.A.forward();
	}
}
