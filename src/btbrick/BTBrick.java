package btbrick;

import lejos.nxt.Motor;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import observer.Action;
import observer.Observer;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import interfaces.Subscriber;

public class BTBrick implements Runnable{

    private ArrayList<Subscriber> subscribers = new ArrayList<>();


    private BTConnection btconnection;

    private DataInputStream inputStream;

    private static BTBrick instance;

    private Observer obs;

    // Singleton implementation for BTBrick
    public static BTBrick getInstance(Observer obs) {
        if (instance == null) {
            instance = new BTBrick(obs);
        }
        return instance;
    }

    private BTBrick(Observer obs) {
        this.obs = obs;
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
                // TODO Auto-generated catch block
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

    public void commence() {
        run();
    }


}
