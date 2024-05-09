package strategies;

import lejos.nxt.Motor;

public class BackOnTrack extends Regelung{

    private static BackOnTrack INSTANCE;

    private final int baseSpeed = 100;

    private double speedDivisor = 0.7;;

    private BackOnTrack(){
    }

    public static BackOnTrack getInstance(){
        if(INSTANCE == null){
            INSTANCE = new BackOnTrack();
        }
        return INSTANCE;
    }
    int timeout = 1000;
    int prevLen = 100;
int len = 100;
    @Override
    public void act(int colorSensorValue, int ultrasoundSensorValue) {
        if(colorSensorValue>LIGHT_THRESHOLD) {
            System.out.println("Linie gefunen!");
            return;
        }

        Motor.A.setSpeed(baseSpeed);
        Motor.B.setSpeed((int) (speedDivisor* baseSpeed));

        Motor.A.forward();
        Motor.B.forward();

        speedDivisor/=0.99;

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
