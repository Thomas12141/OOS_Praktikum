package strategies;
import interfaces.*;
import robot.Action;

/**
 * An abstract class for the control.
 */
public abstract class Steuerung implements IDriveStrategy {
    /**
     * The drive method which gets the command to control.
     *
     * @param command the control command from the Actions enumeration
     */
    public abstract void drive(Action command);
}
