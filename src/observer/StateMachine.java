package observer;

public class StateMachine {
	
	private State currentState;
	
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
