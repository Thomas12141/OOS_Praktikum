package sensors;

import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

/**
 * The class, which holds the sensors.
 */
public class SensorService {
	/** The only instance. */
	private static final SensorService instance = new SensorService();
	/** The ultrasonic sensor. */
	public final UltrasonicSensor ultrasonicSensor;
	/** The color sensor. */
	public final ColorSensor colorSensor;
	/** The bluetooth sensor. */
	public final BluetoothSensor bluetoothSensor;

	/**
	 * A singleton pattern implementíng method to returning the only instance.
	 *
	 * @return the only instance
	 */
	public static SensorService getInstance() {
		return instance;
	}

	/**
	 * The constructor. It initializes the sensors.
	 */
	private SensorService() {
		ultrasonicSensor = new UltrasonicSensor(SensorPort.S1);
		colorSensor = new ColorSensor(SensorPort.S4);
		colorSensor.setFloodlight(true);
		bluetoothSensor = BluetoothSensor.getInstance();
	}
	
	
}
