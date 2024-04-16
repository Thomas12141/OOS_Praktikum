package strategies;

import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;

public class ZickZack extends Regelung{
	int lightThreshold = 35;
	boolean lastTurn = false;
	int counter = 0;
	int counterBorder;

	public ZickZack(int counterBorder){
		this.counterBorder = counterBorder;
	}
	double speed = 1.5;
	@Override
	public void act(ColorSensor colorSensor, UltrasonicSensor ultrasoundSensor) {
		
		int value = colorSensor.getLightValue();
		
		if(lastTurn) {
			Motor.A.setSpeed((int) (300*speed));
			Motor.B.setSpeed((int) (100*speed));
			
			Motor.B.forward();
			Motor.A.forward();
			if(value<lightThreshold) {
				lastTurn = !lastTurn;
			}
		}else {
			Motor.B.setSpeed((int) (300*speed));
			Motor.A.setSpeed((int) (100*speed));

			Motor.B.forward();
			Motor.A.forward();
			if(value>=lightThreshold) {
				lastTurn = !lastTurn;
			}
		}
	}
}
