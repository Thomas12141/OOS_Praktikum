package strategies;

import Sensors.SensorService;
import interfaces.IDriveStrategy;
import lejos.nxt.Motor;

public class ZickZack implements IDriveStrategy{

	private static ZickZack INSTANCE;

	private final int LOW_SPEED = 100;

	private final int HIGH_SPEED = 300;

	private final double SPEED_MULTIPLIER = 1.5;

	private ZickZack(){
	}

	public static ZickZack getInstance(){
		if(INSTANCE == null){
			INSTANCE = new ZickZack();
		}
		return INSTANCE;
	}

	public void resetValues(){}
	@Override
	public void act(SensorService sensorService) {
		int colorSensorValue = sensorService.colorSensor.getLightValue();
        if(colorSensorValue <LIGHT_THRESHOLD) {
			Motor.A.setSpeed((int) (HIGH_SPEED* SPEED_MULTIPLIER));
			Motor.B.setSpeed((int) (LOW_SPEED* SPEED_MULTIPLIER));
		}else {
			Motor.B.setSpeed((int) (HIGH_SPEED* SPEED_MULTIPLIER));
			Motor.A.setSpeed((int) (LOW_SPEED* SPEED_MULTIPLIER));
		}
		Motor.B.forward();
		Motor.A.forward();
	}
}
