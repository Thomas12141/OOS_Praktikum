package strategies;

import sensors.SensorService;
import interfaces.IDriveStrategy;
import lejos.nxt.Motor;

/**
 * This class brings the NXT back on the line in a circle shape,
 * which are supposed to get bigger and bigger.
 */
public class BackOnTrack implements IDriveStrategy {
    /** The only instance */
    private static BackOnTrack instance;
    /** The base speed */
    private static final int BASE_SPEED = 300;
    /** This variable is to reduce the speed on the outer wheels to get bigger circle. */
    private static final double SPEED_REDUCER = 0.99;
    /** To modify the base speed. */
    private double speedDivisor = 0.7;

    /**
     * The constructor.
     */
    private BackOnTrack() {
    }

    /**
     * Returns the only instance or creates one, if there is no one.
     *
     * @return the only instance
     */
    public static BackOnTrack getInstance() {
        if (instance == null) {
            instance = new BackOnTrack();
        }
        return instance;
    }

    /**
     * Resets the speed divisor.
     */
    @Override
    public void resetValues() {
        speedDivisor = 0.7;
    }

    /**
     * The act method, which let the NXT drive ever-increasing circles until a line was found.
     *
     * @param sensorService the sensor service, which contains the sensors which the strategy needs.
     */
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
