package strategies;

import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.UltrasonicSensor;
public class BackOnTrack extends Regelung{

    private int lightThreshold = 35;
    int speed = 300;
    double speedDiv;
    public BackOnTrack(){
        speedDiv=0.1;
    }
    int timeout = 1000;
    int prevLen = 100;
int len = 100;
    @Override
    public void act(ColorSensor colorSensor, UltrasonicSensor ultrasoundSensor) {
        int value = colorSensor.getLightValue();
        len--;
        

        if(len<=0) {
        	 try {
     			Thread.sleep(timeout);
     		} catch (InterruptedException e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     		}
        	
        	turn90right();
        	len = (int) (prevLen*1.1);
        	prevLen = len;
        	return;
        }
        
        System.out.println("Len: "+len);
        
        Motor.A.setSpeed((int) (speed));
        Motor.B.setSpeed((int) (speed));

        Motor.A.forward();
        Motor.B.forward();

       

    }
    
    public void turn90right() {

        Motor.A.setSpeed((int) (speed));

        Motor.B.setSpeed((int) (speed));
    	Motor.A.forward();
    	Motor.B.backward();
    	
    	 try {
 			Thread.sleep(timeout);
 		} catch (InterruptedException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
    }
}
