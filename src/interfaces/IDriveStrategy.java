package interfaces;

import sensors.SensorService;

public interface IDriveStrategy {
	
	void act(SensorService sensorService);

	void resetValues();
	
	int LIGHT_THRESHOLD = 35;
}
