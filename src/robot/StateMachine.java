package robot;


public class StateMachine {

    private static StateMachine instance;

	private volatile State currentState;
	
	private StateMachine() {
		this.currentState = State.LINE_FOUND;
		
	}

    public static StateMachine getInstance() {
        if (instance == null) {
            instance = new StateMachine();
        }
        return instance;
    }

    public synchronized State getCurrentState() {
        return currentState;
    }
    
    public synchronized void setState(State currentState) {
    	this.currentState = currentState;
    }
}
