import lejos.nxt.*;
import lejos.nxt.ColorSensor.Color;

public class Hello_World {
	public static void main(String[] args) throws InterruptedException {
		UltrasonicSensor sensor = new UltrasonicSensor(SensorPort.S1);
		ColorSensor colorSensor = new ColorSensor(SensorPort.S4);
		Sound.twoBeeps();
		int i = 0;
		colorSensor.setFloodlight(true);
		while(!Button.ENTER.isDown()) {
			//System.out.print("Distanz: "+sensor.getDistance()+"\n");
			Thread.sleep(100);
			//int name = colorSensor.;
			int val =colorSensor.getLightValue();
			System.out.print("------------\n");
			
			
			System.out.print("Farbe: " +val+ "\n" );
			
		}
	}
}
