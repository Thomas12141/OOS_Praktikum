package strategies;
import interfaces.*;
import robot.Action;

public abstract class Steuerung implements IDriveStrategy{
    public abstract void drive(Action command);
}
