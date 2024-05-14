package strategies;

import Sensors.SensorService;
import interfaces.IDriveStrategy;
import lejos.nxt.Motor;
import robot.Action;

public class ManualDrive implements IDriveStrategy{

    private static ManualDrive INSTANCE;

    private final int LOW_SPEED = 100;

    private final int HIGH_SPEED = 300;

    private final int BORDER_BETWEEN_BACKWARD_AND_FORWARD = 0;

    private ManualDrive(){}

    public static ManualDrive getInstance(){
        if(INSTANCE == null){
            INSTANCE = new ManualDrive();
        }
        return INSTANCE;
    }

    public void act(SensorService sensorService) {
    	Action action = sensorService.bluetoothSensor.getAction();
        switch (action) {
            case forward: //forward
                System.out.println("Driving FORWARD");
                Motor.A.forward();
                Motor.A.setSpeed(HIGH_SPEED);
                Motor.B.forward();
                Motor.B.setSpeed(HIGH_SPEED);
                break;
            case left: //left
                Motor.A.forward();
                Motor.A.setSpeed(HIGH_SPEED);
                Motor.B.forward();
                Motor.B.setSpeed(LOW_SPEED);
                break;
            case back: //backward
                if (Motor.A.getSpeed() > BORDER_BETWEEN_BACKWARD_AND_FORWARD || Motor.B.getSpeed() > BORDER_BETWEEN_BACKWARD_AND_FORWARD) { //if the robot is driving straight ahead
                    Motor.A.stop();
                    Motor.B.stop();
                } else {
                    Motor.A.backward();
                    Motor.A.setSpeed(LOW_SPEED);
                    Motor.B.backward();
                    Motor.B.setSpeed(LOW_SPEED);
                    break;
                }
            case right: //right
                Motor.A.forward();
                Motor.A.setSpeed(LOW_SPEED);
                Motor.B.forward();
                Motor.B.setSpeed(LOW_SPEED);
                break;
            case manual:
            	break;
        }
    }

	@Override
	public void resetValues() {
	}
}
