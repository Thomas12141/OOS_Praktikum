package strategies;

import interfaces.IDriveStrategy;

public abstract class Regelung implements IDriveStrategy{

	protected final int LIGHT_THRESHOLD = 35;

	/**
	 * Acts on the given messaruements
	 * @param colorSensorValue The Value of the Color Sensor
	 * @param ultrasoundSensor The Value of the Ultrasonic Sensor
	 * @return
	 */
	public abstract void act(int colorSensorValue, int ultrasoundSensor);

	public abstract void resetValues();

}
