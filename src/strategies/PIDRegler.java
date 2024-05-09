package strategies;

import lejos.nxt.Motor;

public class PIDRegler extends Regelung{

    private static PIDRegler instance;

    private int integral = 0;

    private int lastError = 0;

    public static PIDRegler getInstance(){
        if(instance == null){
            instance = new PIDRegler();
        }
        return instance;
    }

    private PIDRegler(){}

    @Override
    public void act(int colorSensorValue, int ultrasoundSensorValue) {
        int proportionConstant = 1000;

        int integralConstant = 100;

        int derivativeConstant = 10000;


        int error = colorSensorValue - LIGHT_THRESHOLD;
        integral += error;
        int derivative = error - lastError;
        int turn = proportionConstant * error + integralConstant * integral + derivativeConstant * derivative;
        turn /= 100;
        int targetPower = 50;
        int powerA = targetPower + turn;
        int powerB = targetPower - turn;
        Motor.A.forward();
        Motor.A.setSpeed(powerA);
        Motor.B.forward();
        Motor.B.setSpeed(powerB);
        lastError = error;
    }
}
