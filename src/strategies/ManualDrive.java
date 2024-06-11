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
    private static final ManualDrive instance = new ManualDrive();
    /** The low speed. */
    private static final int LOW_SPEED = 100;
    /** The high speed. */
    private static final int HIGH_SPEED = 300;

    /**
     * The constructor.
     */
    private ManualDrive() { }

    /**
     * Returns the only instance or creates one, if there is no one.
     * @return the only instance
     */
    public static ManualDrive getInstance() {
        return instance;
    }

    /**
     * This method let the NXT drive over bluetooth.
     *
     * @param sensorService the BluetoothSensor instance
     */
    public void act(SensorService sensorService) {
    	int temp = sensorService.bluetoothSensor.getAction();
    	if(temp>=Action.values().length) {
    		return;
    	}
    	Action action = Action.values()[sensorService.bluetoothSensor.getAction()];
        switch (action) {
            case FORWARD: //forward
                Motor.A.forward();
                Motor.A.setSpeed(HIGH_SPEED);
                Motor.B.forward();
                Motor.B.setSpeed(HIGH_SPEED);
                break;
            case LEFT: //left
                Motor.A.setSpeed(LOW_SPEED);
                Motor.B.setSpeed(HIGH_SPEED);
                Motor.A.forward();
                Motor.B.forward();
                break;
            case BACKWARD: //backward
                Motor.A.setSpeed((HIGH_SPEED+LOW_SPEED)/2);
                Motor.B.setSpeed((HIGH_SPEED+LOW_SPEED)/2);
                Motor.B.backward();
                Motor.A.backward();
                break;
            case RIGHT: //right
                Motor.B.setSpeed(LOW_SPEED);
                Motor.A.setSpeed(HIGH_SPEED);
                Motor.A.forward();
                Motor.B.forward();
                break;
            case STOP: //stop
            	Motor.A.stop();
            	Motor.B.stop();
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
