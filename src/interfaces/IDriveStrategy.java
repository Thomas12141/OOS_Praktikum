package interfaces;

import lejos.nxt.ColorSensor;
import lejos.nxt.UltrasonicSensor;

public interface IDriveStrategy {	
	/**
	 * Acts on the given messaruements
	 * @param colorSensorValue The Value of the Color Sensor
	 * @param ultrasoundSensor The Value of the Ultrasonic Sensor
	 * @return
	 */
	public void act(ColorSensor colorSensorValue,UltrasonicSensor ultrasoundSensor);
	
}
