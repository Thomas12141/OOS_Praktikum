package observer;

public class StateMachine {
	
	private State currentState;
	
	public StateMachine() {
		this.currentState = State.LINE_FOUND;
		
	}
	
	public void updateState() {
        // Auswerten der Sensoren
    }

    public State getCurrentState() {
        return currentState;
    }
}
