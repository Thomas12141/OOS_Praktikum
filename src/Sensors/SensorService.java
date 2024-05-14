package Sensors;

import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;

public class SensorService {
	private static SensorService INSTANCE;
	public final UltrasonicSensor ultrasonicSensor;
	public final ColorSensor colorSensor;
	public final BluetoothSensor bluetoothSensor;
	
	public static SensorService getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new SensorService();
		}
		return INSTANCE;
	}
	private SensorService() {
		ultrasonicSensor = new UltrasonicSensor(SensorPort.S1);
		colorSensor = new ColorSensor(SensorPort.S4);
		colorSensor.setFloodlight(true);
		bluetoothSensor = BluetoothSensor.getInstance();
	}
	
	
}
