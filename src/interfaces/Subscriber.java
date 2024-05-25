package interfaces;

import robot.Action;

/**
 * The subscriber interface for subscribers, which can be notified by the holder of subscribers.
 */
public interface Subscriber {
	/**
	 * The update method, which have to implement the functionality of update strategy.
	 *
	 * @param action a read action of the bluetooth stream
	 */
	void update(Action action);
}
