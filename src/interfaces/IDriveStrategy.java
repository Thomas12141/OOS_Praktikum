package interfaces;

import sensors.SensorService;

/**
 * The interface for the drive strategies.
 * For example: ManualDrive, PIDRegler etc.
 */
public interface IDriveStrategy {

	/**
	 * The act method, which can be called to perform the desired drive strategy.
	 *
	 * @param sensorService the sensor service, which contains the sensors which the strategy needs.
	 */
	void act(SensorService sensorService);

	/**
	 * Reset all values.
	 */
	void resetValues();

	/**
	 * The light threshold for light sensor calibration.
	 */
	int LIGHT_THRESHOLD = 35;
}
