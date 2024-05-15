import sensors.BluetoothSensor;
import btbrick.BTBrick;
import lejos.nxt.*;
import robot.Robot;

public class Main {
	public static void main(String[] args) {
		BTBrick brick = BTBrick.getInstance();
		Robot robot = Robot.getInstance();
		brick.register(BluetoothSensor.getInstance());
		brick.register(robot);
		Thread thread = new Thread(brick);
		thread.start();
		while (!Button.ENTER.isDown()) {
			robot.act();
		}
		thread.interrupt();
	}
}
