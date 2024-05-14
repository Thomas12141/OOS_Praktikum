package Sensors;

import interfaces.Subscriber;
import robot.Action;

public class BluetoothSensor implements Subscriber{
	private static BluetoothSensor INSTANCE;
	
	private volatile Action action;
	
	private BluetoothSensor() {
		
	}
	
	public static BluetoothSensor getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new BluetoothSensor();
		}
		return INSTANCE;
	}
	public synchronized Action getAction() {
		return action;
	}
	@Override
	public synchronized void update(Action action) {
		this.action = action;
	}
}
