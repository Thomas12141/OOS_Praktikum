package sensors;

import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class SensorService {
	private static SensorService instance;
	public final UltrasonicSensor ultrasonicSensor;
	public final ColorSensor colorSensor;
	public final BluetoothSensor bluetoothSensor;
	
	public static SensorService getInstance() {
		if (instance == null) {
			instance = new SensorService();
		}
		return instance;
	}
	private SensorService() {
		ultrasonicSensor = new UltrasonicSensor(SensorPort.S1);
		colorSensor = new ColorSensor(SensorPort.S4);
		colorSensor.setFloodlight(true);
		bluetoothSensor = BluetoothSensor.getInstance();
	}
	
	
}
