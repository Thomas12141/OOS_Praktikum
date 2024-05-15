package strategies;

import sensors.SensorService;
import interfaces.IDriveStrategy;
import lejos.nxt.Motor;
import robot.Action;

public class ManualDrive implements IDriveStrategy {

    private static ManualDrive instance;

    private static final int LOW_SPEED = 100;

    private static final int HIGH_SPEED = 300;

    private static final int BORDER_BETWEEN_BACKWARD_AND_FORWARD = 0;

    private ManualDrive() { }

    public static ManualDrive getInstance() {
        if (instance == null) {
            instance = new ManualDrive();
        }
        return instance;
    }

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

	@Override
	public void resetValues() { //No values to update.
	}
}
