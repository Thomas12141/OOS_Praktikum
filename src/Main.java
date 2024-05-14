import Sensors.BluetoothSensor;
import btbrick.BTBrick;
import lejos.nxt.*;
import robot.Robot;

public class Main {
	public static void main(String[] args) {
		BTBrick brick = BTBrick.getInstance();
		Robot observer = Robot.getInstance();
		brick.register(BluetoothSensor.getInstance());
		brick.register(observer);
		Thread thread = new Thread(brick);
		thread.start();
		while(!Button.ENTER.isDown()) {
			observer.act();
		}
		thread.interrupt();
	}
}
