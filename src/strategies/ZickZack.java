package strategies;

import sensors.SensorService;
import interfaces.IDriveStrategy;
import lejos.nxt.Motor;

public class ZickZack implements IDriveStrategy {

	private static ZickZack instance;

	private static final int LOW_SPEED = 100;

	private static final int HIGH_SPEED = 300;

	private static final double SPEED_MULTIPLIER = 1.5;

	private ZickZack() {
	}

	public static ZickZack getInstance() {
		if (instance == null) {
			instance = new ZickZack();
		}
		return instance;
	}

	@Override
	public void resetValues() { //No values to rest.
	}

	@Override
	public void act(SensorService sensorService) {
		int colorSensorValue = sensorService.colorSensor.getLightValue();
        if (colorSensorValue < LIGHT_THRESHOLD) {
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
