import lejos.nxt.*;
import strategies.ZickZack;

public class Main {
	public static void main(String[] args) {
		UltrasonicSensor sensor = new UltrasonicSensor(SensorPort.S1);
		ColorSensor colorSensor = new ColorSensor(SensorPort.S4);
		
		colorSensor.setFloodlight(true);
		
		ZickZack zickZack = ZickZack.getInstance();
		while(!Button.ENTER.isDown()) {
			zickZack.act(colorSensor.getLightValue(), sensor.getDistance());
			
		}
	}
}
