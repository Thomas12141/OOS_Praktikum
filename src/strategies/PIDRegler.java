package strategies;

import sensors.SensorService;
import interfaces.IDriveStrategy;
import lejos.nxt.Motor;

public class PIDRegler implements IDriveStrategy {

    private static PIDRegler instance;

    private static final int PROPORTION_CONSTANT = 1000;

    private static final int INTEGRAL_CONSTANT = 0;

    private static final int DERIVATIVE_CONSTANT = 0;

    private static final int PROPORTION_REDUCER = 100;

    private static final int TARGET_POWER = 25;

    private int integral = 0;

    private int lastError = 0;

    public static PIDRegler getInstance() {
        if (instance == null) {
            instance = new PIDRegler();
        }
        return instance;
    }

    private PIDRegler() { }

    @Override
    public void resetValues() {
        integral = 0;
        lastError = 0;
    }

    @Override
    public void act(SensorService sensorService) {
    	int colorSensorValue = sensorService.colorSensor.getLightValue();
        int error = colorSensorValue - LIGHT_THRESHOLD;
        integral += error;
        int derivative = error - lastError;
        int turn = PROPORTION_CONSTANT * error + INTEGRAL_CONSTANT * integral + DERIVATIVE_CONSTANT * derivative;
        turn /= PROPORTION_REDUCER;
        int powerA = TARGET_POWER + turn;
        int powerB = TARGET_POWER - turn;
        Motor.A.forward();
        Motor.A.setSpeed(powerA);
        Motor.B.forward();
        Motor.B.setSpeed(powerB);
        lastError = error;
    }
}
