package sensors;

import interfaces.Subscriber;
import robot.Action;

/**
 * The bluetooth "sensor".
 */
public class BluetoothSensor implements Subscriber {
	/** The only instance of BluetoothSensor. */
	private static BluetoothSensor instance;
	/** The current action updates by update(). */
	private volatile Action action;

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
		if (instance == null) {
			instance = new BluetoothSensor();
		}
		return instance;
	}

	/**
	 * Returns the current action.
	 *
	 * @return the current action
	 */
	public synchronized Action getAction() {
		return action;
	}

	/**
	 * Updates the  current action.
	 *
	 * @param action a read action of the bluetooth stream
	 */
	@Override
	public synchronized void update(Action action) {
		this.action = action;
	}
}
