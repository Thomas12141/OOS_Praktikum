package robot;

import interfaces.IDriveStrategy;
import interfaces.Subscriber;
import strategies.*;

import static robot.Action.MANUAL;
import static robot.State.LINE_FOUND;
import static robot.State.USER_CTRL;

import sensors.SensorService;

/**
 * The class, which holds the sensor service, state machine and updates the state.
 */
public class Robot implements Subscriber {
	/**
	 * The only instance of this class.
	 */
	private static Robot instance;

	/**
	 * The sensor service.
	 */
	private final SensorService sensorService;

	/**
	 * The state machine.
	 */
	private final StateMachine stateMachine;

	/**
	 * A state counter.
	 */
	private int stateCounter = 0;

	/**
	 * This variable is set to control how long the NXT is outside the path.
	 */
	private static final int HOW_LONG_OUTSIDE_LINE = 50;

	/**
	 * Light threshold to calibrate the light sensor.
	 */
    private static final int LIGHT_THRESHOLD = 32;

	/**
	 * The constructor.
	 */
	private Robot() {
		this.stateMachine = StateMachine.getInstance();
		this.sensorService = SensorService.getInstance();
	}

	/**
	 * The method which implements the singleton pattern and return the instance or create one,
	 * if there is no one.
	 *
	 * @return the instance of robot
	 */
	public static Robot getInstance() {
		if (instance == null) {
			instance = new Robot();
		}
		return instance;
	}

	/**
	 * Updates the state with the light sensor to line lost or line found.
	 */
	public void updateState() {
        // Auswerten der Sensoren
		// Setzen der States
		if (stateMachine.getCurrentState() == USER_CTRL) return;
		int lightV = sensorService.colorSensor.getLightValue();
		if (lightV < LIGHT_THRESHOLD) {
			stateCounter++;
			if (stateCounter == HOW_LONG_OUTSIDE_LINE) {
				stateCounter = 0;
				stateMachine.setState(State.LINE_LOST);
			}
		} else {
			stateMachine.setState(LINE_FOUND);
		}
    }

	/**
	 * The act method which calls the act methods of the strategies.
	 */
	public void act() {
        IDriveStrategy currentStrategy = getStrategy();
		currentStrategy.act(sensorService);
	}

	/**
	 * Returns the strategy to act due to the state.
	 *
	 * @return the appropriate strategy
	 */
	public IDriveStrategy getStrategy() {
		updateState();
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

	/**
	 * Returns true if the user state is the current state, otherwise false.
	 *
	 * @return true -> current state is USR_CTRL, false -> otherwise
	 */
	public boolean getUserState() {
        return stateMachine.getCurrentState() == USER_CTRL;
	}

	/**
	 * The update method, which can be called to perform on the read action.
	 *
	 * @param action a read action of the bluetooth stream
	 */
	@Override
	public void update(Action action) {

        System.out.print("Robot updated: " + action + "\n");
		State state = stateMachine.getCurrentState();
		if ( action == MANUAL && state != USER_CTRL) {
			stateMachine.setState(USER_CTRL);
		} else if ( action == MANUAL ) {
			stateMachine.setState(LINE_FOUND);
		}

	}
}
