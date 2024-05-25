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
