package observer;

import interfaces.IDriveStrategy;
import strategies.*;

public class Observer {
	private StateMachine stateMachine;
	
	public Observer() {
		this.stateMachine = new StateMachine();
	}

	
	public void updateState() {
        // Auswerten der Sensoren
		// Setzen der States
    }
	
	public IDriveStrategy getStrategy() {

		State currentState = stateMachine.getCurrentState();
		
		switch (currentState) {
		case LINE_FOUND:
			return PIDRegler.getInstance();
		case LINE_LOST:
			return new BackOnTrack();
		case USER_CTRL:
			return null;
		default:
			return null;
		}
	}
}
