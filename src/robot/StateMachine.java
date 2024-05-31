package robot;

/**
 * The state machine of the robot. The possible states are USR_CTRL, LINE_LOST and LINE_FOUND.
 */
public class StateMachine {

    /** The only instance */
    private static final StateMachine instance = new StateMachine();
    /** The current state */
	private volatile State currentState;

    /**
     * The constructor.
     */
	private StateMachine() {
		this.currentState = State.LINE_FOUND;
	}

    /**
     * To get the only instance. If there is no existent instance, it creates a new one.
     *
     * @return the instance
     */
    public static StateMachine getInstance() {
        return instance;
    }

    /**
     * A synchronized method to get the current state.
     *
     * @return the current state
     */
    public synchronized State getCurrentState() {
        return currentState;
    }

    /**
     * This method sets the state to the new one in the parameter.
     *
     * @param currentState the new state to be switched on
     */
    public synchronized void setState(State currentState) {
    	this.currentState = currentState;
    }
}
