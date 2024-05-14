package btbrick;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import interfaces.Subscriber;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import robot.Action;

public class BTBrick implements Runnable{

    private final ArrayList<Subscriber> subscribers = new ArrayList<>();


    private BTConnection btconnection;

    private DataInputStream inputStream;

    private static BTBrick instance;


    // Singleton implementation for BTBrick
    public static BTBrick getInstance() {
        if (instance == null) {
            instance = new BTBrick();
        }
        return instance;
    }

    private BTBrick() {
    }


    private void init() {
        if (btconnection == null) {
            System.out.println("Waiting for input stream...");
            btconnection = Bluetooth.waitForConnection();
            inputStream = btconnection.openDataInputStream();
            System.out.println("Input stream found...");
        }
    }


    @Override
    public void run() {
        init();
        while(true) {
            int length = 0;
            try {
                length = inputStream.available();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            if(length>1) {
                try {
                    Action commandBT = Action.values()[inputStream.readInt()];
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void register(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void notifySubscribers(Action action) {
        for(Subscriber subscriber: subscribers) {
            subscriber.update(action);
        }
    }


}
