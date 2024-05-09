package interfaces;

import observer.Action;

public interface Subscriber {
	void update(Action action);
}
