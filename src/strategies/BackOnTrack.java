package strategies;

import sensors.SensorService;
import interfaces.IDriveStrategy;
import lejos.nxt.Motor;

public class BackOnTrack implements IDriveStrategy {

    private static BackOnTrack instance;

    private static final int BASE_SPEED = 300;

    private static final double SPEED_REDUCER = 0.99;

    private double speedDivisor = 0.7;

    private BackOnTrack() {
    }

    public static BackOnTrack getInstance() {
        if (instance == null) {
            instance = new BackOnTrack();
        }
        return instance;
    }

    @Override
    public void resetValues() {
        speedDivisor = 0.7;
    }

    @Override
    public void act(SensorService sensorService) {
    	int colorSensorValue = sensorService.colorSensor.getLightValue();
        if (colorSensorValue > LIGHT_THRESHOLD) {
            System.out.println("Linie gefunen!");
            return;
        }

        Motor.A.setSpeed(BASE_SPEED);
        Motor.B.setSpeed((int) (speedDivisor * BASE_SPEED));

        Motor.A.forward();
        Motor.B.forward();

        speedDivisor /= SPEED_REDUCER;

    }
}
