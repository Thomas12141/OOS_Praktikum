package strategies;
import interfaces.*;

public abstract class Steuerung implements IDriveStrategy{
    public abstract void drive(int command);
}
