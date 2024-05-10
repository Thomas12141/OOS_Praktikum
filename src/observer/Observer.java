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

	private int stateCounter = 0;

	private final int HOW_LONG_OUTSIDE_LINE = 50;

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
	private final UltrasonicSensor sensor = new UltrasonicSensor(SensorPort.S1);
	private final ColorSensor colorSensor = new ColorSensor(SensorPort.S4);
	private final int LIGHT_THRESHOLD = 35;


	
	public void updateState() {
        // Auswerten der Sensoren
		// Setzen der States
		int lightV = colorSensor.getLightValue();
		if(lightV < LIGHT_THRESHOLD) {
			stateCounter++;
			if(stateCounter == HOW_LONG_OUTSIDE_LINE) {
				stateCounter = 0;
				stateMachine.setState(State.LINE_LOST);
			}
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
		IDriveStrategy iDriveStrategy;
		switch (currentState) {
		case LINE_FOUND:
			iDriveStrategy = PIDRegler.getInstance();
			break;
		case LINE_LOST:
			iDriveStrategy =  BackOnTrack.getInstance();
			break;
		default:
			iDriveStrategy =  ManualDrive.getInstance();
		}
		if(iDriveStrategy instanceof Regelung){
			((Regelung)iDriveStrategy).resetValues();
		}
		return iDriveStrategy;
	}
	
	
	public boolean getUserState() {
        return stateMachine.getCurrentState() == USER_CTRL;
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
