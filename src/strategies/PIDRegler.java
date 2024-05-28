package strategies;

import sensors.SensorService;
import interfaces.IDriveStrategy;
import lejos.nxt.Motor;
import lejos.util.PIDController;

/**
 * This class implements the proportional–integral–derivative (PID) controller
 * to adjust motor speeds and keep the robot on track based on sensor input.
 */
public class PIDRegler implements IDriveStrategy {
	private static PIDController pid;
    /** The only instance of this singleton class. */
    private static PIDRegler instance;

    /** The proportional constant in the PID formula. */
    private static int PROPORTION_CONSTANT = 700;

    /** The integral constant in the PID formula. */
    private static int INTEGRAL_CONSTANT = 0;

    /** The derivative constant in the PID formula. */
    private static int DERIVATIVE_CONSTANT = 0;

    /** The factor by which the computed turn value is reduced. */
    private static final int PROPORTION_REDUCER = 100;

    /** The target power for the motors. */
    private static int TARGET_POWER = 300;

    /** Accumulated integral value used in the PID formula. */
    private int integral = 0;

    /** Previous error value used to compute the derivative term. */
    private int lastError = 0;


    /**
     * Returns the single instance of the PIDRegler class.
     *
     * @return the instance of PIDRegler
     */
    public static PIDRegler getInstance() {
        if (instance == null) {
        	pid = new PIDController(200, 10);
            instance = new PIDRegler();
        }
        return instance;
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
        //integral = 0;
        //lastError = 0;
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
    			DERIVATIVE_CONSTANT = value%10000;
    		}else if(option==3) {
    			INTEGRAL_CONSTANT = value%10000;
    		}else if(option==2) {
    			PROPORTION_CONSTANT = value%10000;
    		}else if(option==1) {
    			System.out.println("Set of TARGET_POWER to " + value%10000);
    			TARGET_POWER = value%10000;
    		}
    	}
        // Get the current light value from the sensor
        int colorSensorValue = sensorService.colorSensor.getLightValue();

        // Calculate the error value (deviation from the desired light threshold)
        int error = colorSensorValue - LIGHT_THRESHOLD;

        // Update the integral term
        integral += error;

        // Calculate the derivative term
        int derivative = error - lastError;

        // Compute the turn value using the PID formula
        int turn = PROPORTION_CONSTANT * error + INTEGRAL_CONSTANT * integral + DERIVATIVE_CONSTANT * derivative;
        

    	turn = pid.doPID(PIDController.PID_PV);
    	
        // Reduce the turn value to a manageable range
        turn /= PROPORTION_REDUCER;

        // Calculate the power for each motor
        int powerA = TARGET_POWER + turn;
        int powerB = TARGET_POWER - turn;

        // Set the motor speeds and direction
        Motor.A.forward();
        Motor.A.setSpeed(powerA);
        Motor.B.forward();
        Motor.B.setSpeed(powerB);

        // Update the last error for the next iteration
        lastError = error;
    }
}