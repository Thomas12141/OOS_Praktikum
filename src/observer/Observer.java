package observer;

import btbrick.BTBrick;
import interfaces.IDriveStrategy;
import interfaces.Subscriber;
import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import strategies.*;

import static observer.Action.manual;
import static observer.State.LINE_FOUND;
import static observer.State.USER_CTRL;


public class Observer implements Subscriber{
	private static Observer INSTANCE;

	private final StateMachine stateMachine;

	private Action action;

	private Observer() {
		this.stateMachine = StateMachine.getInstance();

		colorSensor.setFloodlight(true);
	}

	public static Observer getINSTANCE() {
		if(INSTANCE == null) {
			INSTANCE = new Observer();
		}
		return INSTANCE;
	}

	private Regelung currentStrategy = (Regelung) getStrategy();
	private UltrasonicSensor sensor = new UltrasonicSensor(SensorPort.S1);
	private ColorSensor colorSensor = new ColorSensor(SensorPort.S4);
	private final int LIGHT_THRESHOLD = 35;


	
	public void updateState() {
        // Auswerten der Sensoren
		// Setzen der States
		int lightV = colorSensor.getLightValue();
		if(lightV < LIGHT_THRESHOLD) {
			stateMachine.setState(State.LINE_LOST);
		}else {
			stateMachine.setState(LINE_FOUND);
		}
		
		
		
    }
	
	public void act() {
		if(stateMachine.getCurrentState() == USER_CTRL){
			ManualDrive.getInstance().drive(action);
		}else {
			updateState();
			currentStrategy = (Regelung) getStrategy();
			currentStrategy.act(colorSensor.getLightValue(), sensor.getDistance());
		}
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
		if (stateMachine.getCurrentState() == USER_CTRL) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void update(Action action) {
		if(action == manual){
			State state = stateMachine.getCurrentState();

			if(state != USER_CTRL) {
				stateMachine.setState(USER_CTRL);
			}else {
				stateMachine.setState(LINE_FOUND);
			}
		}else {
			this.action = action;
		}

	}
}
