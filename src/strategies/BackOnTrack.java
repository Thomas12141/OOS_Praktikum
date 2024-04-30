package strategies;

import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;
public class BackOnTrack extends Regelung{

    private int lightThreshold = 35;
    int speed = 500;
    double speedDiv;
    public BackOnTrack(){
        speedDiv=0.1;
    }
int count = 0;
    @Override
    public void act(ColorSensor colorSensor, UltrasonicSensor ultrasoundSensor) {
        int value = colorSensor.getLightValue();
        
        
        
        
        System.out.println("Speed: "+speedDiv);
count++;
        Motor.A.setSpeed((int) (speed));
        Motor.B.setSpeed((int) (speedDiv*speed));

        Motor.B.forward();
        Motor.A.forward();
        
        if(speedDiv <=1)
        speedDiv/=0.999;

    }
    
    public void turn90right() {

        Motor.A.setSpeed((int) (speed));

        Motor.B.setSpeed((int) (speed));
    	Motor.A.forward();
    	Motor.B.backward();
    }
}
