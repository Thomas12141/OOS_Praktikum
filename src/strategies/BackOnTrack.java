package strategies;

import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;
public class BackOnTrack extends Regelung{

    private int lightThreshold = 35;
    int speed = 100;
    double speedDiv;
    public BackOnTrack(){
        speedDiv=0.7;
    }

    @Override
    public void act(ColorSensor colorSensor, UltrasonicSensor ultrasoundSensor) {
        int value = colorSensor.getLightValue();
        if(value>lightThreshold) {
            System.out.println("Linie gefunen!");
            return;
        }

        Motor.A.setSpeed((int) (speed));
        Motor.B.setSpeed((int) (speedDiv*speed));

        Motor.B.forward();
        Motor.A.forward();

        speedDiv/=0.99;

    }
}
