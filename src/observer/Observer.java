package observer;

import btbrick.BTBrick;
import interfaces.IDriveStrategy;
import interfaces.Subscriber;
import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import strategies.*;


public class Observer implements Subscriber{
	private final StateMachine stateMachine;
	
	public Observer() {
		this.stateMachine = StateMachine.getInstance();
	}

	private BTBrick brick = BTBrick.getInstance(this);
	private StateMachine stateMachine;
	private IDriveStrategy currentStrategy = getStrategy();
	private UltrasonicSensor sensor = new UltrasonicSensor(SensorPort.S1);
	private ColorSensor colorSensor = new ColorSensor(SensorPort.S4);
	private final int LIGHT_THRESHOLD = 35;
	public Observer() {
		this.stateMachine = StateMachine.getInstance();
		
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
	
	public void act() {
		updateState();
		currentStrategy = getStrategy();
		currentStrategy.act(colorSensor, sensor);
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
	
	
	public boolean getUserState() {
		if (stateMachine.getCurrentState() == State.USER_CTRL) { 
			return true;
		} else {
			return false;
		}
	}
	
	public void update(Action action) {
		State state = stateMachine.getCurrentState();
		 
		if(state != State.USER_CTRL) {
			if(action == Action.manual) {
				stateMachine.setState(State.USER_CTRL);
			}
		}else {
			if(action == Action.manual) {
				updateState();
			}else {
				brick.drive(action);
			}
		}
	}
}
