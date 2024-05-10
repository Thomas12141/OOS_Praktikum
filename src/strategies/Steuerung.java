package strategies;
import interfaces.*;
import observer.Action;

public abstract class Steuerung implements IDriveStrategy{
    public abstract void drive(Action command);
}
