package observer;

import interfaces.IDriveStrategy;
import strategies.*;

public class Observer {
	private final StateMachine stateMachine;
	
	public Observer() {
		this.stateMachine = StateMachine.getInstance();
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
			return BackOnTrack.getInstance();
		default:
			return ManualDrive.getInstance();
		}
	}
}
