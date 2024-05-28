import sensors.BluetoothSensor;
import btbrick.BTBrick;
import lejos.nxt.*;
import robot.Robot;

/**
 * The main class that initializes the robot and controls its behavior.
 */
public class Main {
	/**
	 * The main method to start the robot and control its behavior.
	 *
	 * @param args the command-line arguments (not used)
	 */
	public static void main(String[] args) {
		int min = 200;
		int max = 0;
		ColorSensor colorSensor = new ColorSensor(SensorPort.S4);
		colorSensor.setFloodlight(true);
		Motor.A.setSpeed(100);
		Motor.B.setSpeed(100);
		Motor.B.forward();
		Motor.A.forward();
		while(!Button.ENTER.isDown()) {
			int value = colorSensor.getLightValue();
			if (min>value) {
				min = value;
			}
			if(max<value) {
				max = value;
			}
		}
		System.out.println("min: " + min);
		System.out.println("max: " + max);
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Initialize the Bluetooth brick
		BTBrick brick = BTBrick.getInstance();

		// Initialize the robot
		Robot robot = Robot.getInstance();

		// Register Bluetooth sensor and robot with the brick
		brick.register(BluetoothSensor.getInstance());
		brick.register(robot);

		// Start the Bluetooth brick thread
		Thread thread = new Thread(brick);
		thread.start();

		// Control loop: Keep executing robot actions until the ENTER button is pressed
		while (!Button.ENTER.isDown()) {
			robot.act();
		}

		// Interrupt the Bluetooth brick thread when the program terminates
		thread.interrupt();
	}
}
