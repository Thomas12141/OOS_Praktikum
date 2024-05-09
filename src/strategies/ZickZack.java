package strategies;

import lejos.nxt.Motor;

public class ZickZack extends Regelung{

	private static ZickZack INSTANCE;

	private boolean lastTurn = false;

	private double speed = 1.5;

	private ZickZack(){
	}

	public static ZickZack getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ZickZack();
		}
		return INSTANCE;
	}

	@Override
	public void act(int colorSensorValue, int ultrasoundSensorValue) {

        if(lastTurn) {
			Motor.A.setSpeed((int) (300*speed));
			Motor.B.setSpeed((int) (100*speed));
			
			Motor.B.forward();
			Motor.A.forward();
			if(colorSensorValue <LIGHT_THRESHOLD) {
				lastTurn = !lastTurn;
			}
		}else {
			Motor.B.setSpeed((int) (300*speed));
			Motor.A.setSpeed((int) (100*speed));

			Motor.B.forward();
			Motor.A.forward();
			if(colorSensorValue >=LIGHT_THRESHOLD) {
				lastTurn = !lastTurn;
			}
		}
	}
}
