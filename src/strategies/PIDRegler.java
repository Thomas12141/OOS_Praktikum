package strategies;

import sensors.SensorService;
import interfaces.IDriveStrategy;
import lejos.nxt.Motor;
import lejos.util.PIDController;
import robot.Robot;

/**
 * This class implements the proportional–integral–derivative (PID) controller
 * to adjust motor speeds and keep the robot on track based on sensor input.
 */
public class PIDRegler implements IDriveStrategy {
	private static PIDController pid;
    /** The only instance of this singleton class. */
    private static final PIDRegler instance = new PIDRegler();

    /** The target power for the motors. */
    private static int TARGET_POWER = 300;
    
    private static int lastLightValue;

    
    /**
     * Returns the single instance of the PIDRegler class.
     *
     * @return the instance of PIDRegler
     */
    public static PIDRegler getInstance() {
    	if (pid == null) {
    		startPid();
    	}
        return instance;
    }
    
    private static void startPid() {
        pid = new PIDController(Robot.getLightThreshold(), 0);

        pid.setPIDParam(PIDController.PID_KD, 0f);
        pid.setPIDParam(PIDController.PID_KI, 0f);
        pid.setPIDParam(PIDController.PID_KP, 10f);
    }
    /**
     * Private constructor to enforce the singleton pattern.
     */
    private PIDRegler() { }

    /**
     * Resets the integral and last error values.
     * This method should be called when starting a new control cycle.
     */
    @Override
    public void resetValues() {
        pid.setPIDParam(PIDController.PID_KD, 0f);
        pid.setPIDParam(PIDController.PID_KI, 0f);
        pid.setPIDParam(PIDController.PID_KP, 10f);
    }

    /**
     * The main control method to be called in a loop.
     * It adjusts the motor speeds based on the sensor input using PID control.
     *
     * @param sensorService the sensor service providing sensor readings
     */
    @Override
    public void act(SensorService sensorService) {
    	int value = sensorService.bluetoothSensor.getAction();
    	if(value/10000>0) {
    		int option = value/10000;
    		if(option==4) {
                pid.setPIDParam(PIDController.PID_KD, (float) (value % 10000) /10);
    		}else if(option==3) {
    			pid.setPIDParam(PIDController.PID_KI, (float) (value % 10000) /10);
    		}else if(option==2) {
                pid.setPIDParam(PIDController.PID_KP, (float) (value % 10000) /10);
    		}else if(option==5) {
    			TARGET_POWER = value % 10000;
    		}
    	}
        // Get the current light value from the sensor
        int colorSensorValue = sensorService.colorSensor.getLightValue();
        

    	int turn = pid.doPID(colorSensorValue);

    	
        // Calculate the power for each motor
        int powerA = TARGET_POWER + turn;
        int powerB = TARGET_POWER - turn;

        // Set the motor speeds and direction
        Motor.A.forward();
        Motor.A.setSpeed(powerA);
        Motor.B.forward();
        Motor.B.setSpeed(powerB);

    	
        if(lastLightValue!= Robot.getLightThreshold()) {
        	lastLightValue = Robot.getLightThreshold();
        	startPid();
        }
    }
}