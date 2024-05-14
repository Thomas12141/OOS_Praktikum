package interfaces;

import Sensors.SensorService;

public interface IDriveStrategy {
	
	public abstract void act(SensorService sensorService);

	public abstract void resetValues();
	
	public final int LIGHT_THRESHOLD = 35;
}
