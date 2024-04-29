package observer;

import interfaces.IDriveStrategy;
import strategies.*;

public class Observer {
	private StateMachine stateMachine;
	
	public Observer() {
		this.stateMachine = new StateMachine();
	}
	
	public IDriveStrategy getStrategy() {
		stateMachine.updateState();
		State currentState = stateMachine.getCurrentState();
		
		switch (currentState) {
		case LINE_FOUND:
			return PIDRegler.getInstance();
		case LINE_LOST:
			return null;
		case USER_CTRL:
			return null;
		default:
			return null;
		}
	}
}
