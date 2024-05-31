package btbrick;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import interfaces.Subscriber;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

/**
 * The class, which runs the bluetooth connection to the controller and reads the input stream.
 * It gets a subscriber list, in which the possible subscribers can subscribe to get updates.
 */
public final class BTBrick implements Runnable {
    /** The list of subscriber */
    private final ArrayList<Subscriber> subscribers = new ArrayList<>();
    /** The bluetooth connection */
    private BTConnection btconnection;
    /** The data input stream from bluetooth */
    private DataInputStream inputStream;
    /** The BTBrick instance for singleton */
    private static final BTBrick instance = new BTBrick();


    /**
     * This method creates an instance or returns the instance already created.
     *
     * @return the BRBrick instance
     */
    public static BTBrick getInstance() {
        return instance;
    }

    /**
     * The constructor.
     */
    private BTBrick() {
    }

    /**
     * This method inits the bluetooth connection and prints the status to the console.
     */
    private void init() {
        if (btconnection == null) {
            System.out.println("Waiting for input stream...");
            btconnection = Bluetooth.waitForConnection();
            inputStream = btconnection.openDataInputStream();
            System.out.println("Input stream found...");
        }
    }

    /**
     * The run method for the threaded input stream reading process.
     * First it runs the init process, and then it gets into an endless
     * while loop for reading the input stream.
     */
    @Override
    public void run() {
        init();
        while (true) {
            int length = 0;
            try {
                length = inputStream.available();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            if (length > 0) {
                try {
                	int index = inputStream.readInt();
                    notifySubscribers(index);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Adds the subscriber to subscriber array.
     * @param subscriber the subscriber to subscribe
     */
    public void register(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    /**
     * Remove the subscriber to subscriber array.
     * @param subscriber the subscriber to remove from list
     */
    @SuppressWarnings("unused")
    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    /**
     * This method notifies all subscribers with an update.
     *
     * @param action the read action from input stream
     */
    public void notifySubscribers(int action) {
        for (Subscriber subscriber: subscribers) {
            subscriber.update(action);
        }
    }


}
