package observer;

import btbrick.BTBrick;

public class StateMachine {
	
	private State currentState;
	private BTBrick btBrick;
	
	public StateMachine() {
		this.currentState = State.LINE_FOUND;
		
	}

    public State getCurrentState() {
        return currentState;
    }
    
    public void setState(State currentState) {
    	this.currentState = currentState;
    }
}
