package observer;

import btbrick.BTBrick;
import interfaces.IDriveStrategy;
import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import strategies.*;

public class Observer {
	private StateMachine stateMachine;
	private BTBrick brick;
	private UltrasonicSensor sensor = new UltrasonicSensor(SensorPort.S1);
	private ColorSensor colorSensor = new ColorSensor(SensorPort.S4);
	private final int LIGHT_THRESHOLD = 35;
	public Observer() {
		this.stateMachine = new StateMachine();
		
		colorSensor.setFloodlight(true);
	}

	
	public void updateState() {
        // Auswerten der Sensoren
		// Setzen der States
		int lightV = colorSensor.getLightValue();
		if(lightV < LIGHT_THRESHOLD) {
			stateMachine.setState(State.LINE_LOST);
		}else {
			stateMachine.setState(State.LINE_FOUND);
		}
		
		
		
    }
	
	public void Act(Action action) {
		State s = stateMachine.getCurrentState();
		
		if(Action.noop == action && s != State.USER_CTRL) {
			stateMachine.setState(State.USER_CTRL);
		}else if(Action.noop == action) {
				stateMachine.setState(State.LINE_FOUND);
			
		}else {   
			if(s != State.USER_CTRL) {
				updateState();
				IDriveStrategy strat = getStrategy();
				strat.act(colorSensor, sensor);
			}else {
				brick.drive(action);
			}
		}
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
	
	public boolean getUserState() {
		if (stateMachine.getCurrentState() == State.USER_CTRL) { 
			return true;
		} else {
			return false;
		}
	}
}
