package strategies;

import lejos.nxt.Motor;

public class BackOnTrack extends Regelung{

    private static BackOnTrack INSTANCE;

    private final int baseSpeed = 300;

    private double speedDivisor = 0.7;;

    private BackOnTrack(){
    }

    public static BackOnTrack getInstance(){
        if(INSTANCE == null){
            INSTANCE = new BackOnTrack();
        }
        return INSTANCE;
    }

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
}
