import lejos.nxt.*;
import strategies.ZickZack;

public class Main {
	public static void main(String[] args) throws InterruptedException {
		UltrasonicSensor sensor = new UltrasonicSensor(SensorPort.S1);
		ColorSensor colorSensor = new ColorSensor(SensorPort.S4);
		
		colorSensor.setFloodlight(true);
		
		ZickZack zickZack = new ZickZack(1000);
		while(!Button.ENTER.isDown()) {
			zickZack.act(colorSensor, sensor);
			
		}
	}
}
