package strategies;

import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;

public class ZickZack extends Regelung{
	private int lightThreshold = 35;
	private boolean lastTurn = false;
	private int counterBorder;
	private double speed = 1.5;

	public ZickZack(int counterBorder){
		this.counterBorder = counterBorder;
	}
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
