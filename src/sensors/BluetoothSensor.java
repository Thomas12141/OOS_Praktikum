package sensors;

import interfaces.Subscriber;
import robot.Action;

/**
 * The bluetooth "sensor".
 */
public class BluetoothSensor implements Subscriber {
	/** The only instance of BluetoothSensor. */
	private static BluetoothSensor instance = new BluetoothSensor();
	/** The current action updates by update(). */
	private volatile int action;

	/**
	 * The constructor.
	 */
	private BluetoothSensor() {
	}

	/**
	 * An instance returning method, which implements the singleton pattern.
	 *
	 * @return the only instance
	 */
	public static BluetoothSensor getInstance() {
		return instance;
	}

	/**
	 * Returns the current action.
	 *
	 * @return the current action
	 */
	public synchronized int getAction() {
		return action;
	}

	/**
	 * Updates the  current action.
	 *
	 * @param action a read action of the bluetooth stream
	 */
	@Override
	public synchronized void update(int action) {
		this.action = action;
	}
}
