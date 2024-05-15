package sensors;

import interfaces.Subscriber;
import robot.Action;

public class BluetoothSensor implements Subscriber {
	private static BluetoothSensor instance;

	private volatile Action action;
	
	private BluetoothSensor() {
		
	}
	
	public static BluetoothSensor getInstance() {
		if (instance == null) {
			instance = new BluetoothSensor();
		}
		return instance;
	}
	public synchronized Action getAction() {
		return action;
	}
	@Override
	public synchronized void update(Action action) {
		this.action = action;
	}
}
