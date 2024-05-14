package robot;

import btbrick.BTBrick;
import interfaces.IDriveStrategy;
import interfaces.Subscriber;
import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import strategies.*;

import static robot.Action.manual;
import static robot.State.LINE_FOUND;
import static robot.State.USER_CTRL;

import Sensors.SensorService;


public class Robot implements Subscriber{
	private static Robot INSTANCE;
	
	private final SensorService sensorService;
	
	private final StateMachine stateMachine;

	private Action action;

	private int stateCounter = 0;

	private final int HOW_LONG_OUTSIDE_LINE = 50;


	private IDriveStrategy currentStrategy;
	private final int LIGHT_THRESHOLD = 35;

	private Robot() {
		this.stateMachine = StateMachine.getInstance();
		this.sensorService = SensorService.getInstance();


	}

	public static Robot getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new Robot();
		}
		return INSTANCE;
	}


	
	public void updateState() {
        // Auswerten der Sensoren
		// Setzen der States
		int lightV = sensorService.colorSensor.getLightValue();
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
		currentStrategy = getStrategy();
		currentStrategy.act(sensorService);
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
		iDriveStrategy.resetValues();
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
