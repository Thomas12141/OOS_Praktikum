package strategies;

import interfaces.IDriveStrategy;
import lejos.nxt.ColorSensor;
import lejos.nxt.UltrasonicSensor;

public abstract class Regelung implements IDriveStrategy{

	public abstract void act(ColorSensor colorSensorValue,UltrasonicSensor ultrasoundSensor);

}
