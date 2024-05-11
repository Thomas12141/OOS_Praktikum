import btbrick.BTBrick;
import lejos.nxt.*;
import observer.Observer;

public class Main {
	public static void main(String[] args) {
		BTBrick brick = BTBrick.getInstance();
		Observer observer = Observer.getINSTANCE();
		brick.register(observer);
		Thread thread = new Thread(brick);
		thread.start();
		while(!Button.ENTER.isDown()) {
			observer.act();
		}
		thread.interrupt();
	}
}
