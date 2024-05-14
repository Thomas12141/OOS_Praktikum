package robot;


public class StateMachine {

    private static StateMachine INSTANCE;

	private volatile State currentState;
	
	private StateMachine() {
		this.currentState = State.LINE_FOUND;
		
	}

    public static StateMachine getInstance() {
        if(INSTANCE == null){
            INSTANCE = new StateMachine();
        }
        return INSTANCE;
    }

    public synchronized State getCurrentState() {
        return currentState;
    }
    
    public synchronized void setState(State currentState) {
    	this.currentState = currentState;
    }
}
