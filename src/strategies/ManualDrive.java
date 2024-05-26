package strategies;

import sensors.SensorService;
import interfaces.IDriveStrategy;
import lejos.nxt.Motor;
import robot.Action;

/**
 * Let the NXT drive by bluetooth.
 */
public class ManualDrive implements IDriveStrategy {
    /** The only instance. */
    private static ManualDrive instance;
    /** The low speed. */
    private static final int LOW_SPEED = 100;
    /** The high speed. */
    private static final int HIGH_SPEED = 300;
    /** For checking if the NXT is driving. */
    private static final int BORDER_BETWEEN_BACKWARD_AND_FORWARD = 0;

    /**
     * The constructor.
     */
    private ManualDrive() { }

    /**
     * Returns the only instance or creates one, if there is no one.
     * @return the only instance
     */
    public static ManualDrive getInstance() {
        if (instance == null) {
            instance = new ManualDrive();
        }
        return instance;
    }

    /**
     * This method let the NXT drive over bluetooth.
     *
     * @param sensorService the BluetoothSensor instance
     */
    public void act(SensorService sensorService) {
    	Action action = sensorService.bluetoothSensor.getAction();
        switch (action) {
            case FORWARD: //forward
                System.out.println("Driving FORWARD");
                Motor.A.forward();
                Motor.A.setSpeed(HIGH_SPEED);
                Motor.B.forward();
                Motor.B.setSpeed(HIGH_SPEED);
                break;
            case LEFT: //left
                Motor.A.forward();
                Motor.A.setSpeed(HIGH_SPEED);
                Motor.B.forward();
                Motor.B.setSpeed(LOW_SPEED);
                break;
            case BACK: //backward
                //if the robot is driving straight ahead
                if (Motor.A.getSpeed() > BORDER_BETWEEN_BACKWARD_AND_FORWARD
                        || Motor.B.getSpeed() > BORDER_BETWEEN_BACKWARD_AND_FORWARD) {
                    Motor.A.stop();
                    Motor.B.stop();
                }
                Motor.A.backward();
                Motor.A.setSpeed(LOW_SPEED);
                Motor.B.backward();
                Motor.B.setSpeed(LOW_SPEED);
                break;
            case RIGHT: //right
                Motor.A.forward();
                Motor.A.setSpeed(LOW_SPEED);
                Motor.B.forward();
                Motor.B.setSpeed(HIGH_SPEED);
                break;
            default:
            	break;
        }
    }

    /**
     * Reset the values.
     */
	@Override
	public void resetValues() { //No values to update.
	}
}
