package observer;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;

public class StateMachine {

    private static StateMachine INSTANCE;

	private State currentState;
	
	private StateMachine() {
		this.currentState = State.LINE_FOUND;
		
	}

    public static StateMachine getInstance() {
        if(INSTANCE == null){
            INSTANCE = new StateMachine();
        }
        return INSTANCE;
    }

    public State getCurrentState() {
        return currentState;
    }
    
    public void setState(State currentState) {
    	this.currentState = currentState;
    }
}
