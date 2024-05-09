package btbrick;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import strategies.ManualDrive;
import strategies.Steuerung;

import java.io.DataInputStream;
import java.io.IOException;

public class BTBrick implements Runnable {

    private BTConnection btconnection;

    private DataInputStream inputStream;

    private static BTBrick instance;

    private Steuerung steuerung;

    // Singleton implementation for BTBrick
    public static BTBrick getInstance() {
        if (instance == null) {
            instance = new BTBrick();
        }
        instance.steuerung = ManualDrive.getInstance();
        return instance;
    }

    private BTBrick() {

    }


    @Override
    public void run() {
        if (btconnection == null) {
            System.out.println("Waiting for input stream...");
            btconnection = Bluetooth.waitForConnection();
            inputStream = btconnection.openDataInputStream();
            System.out.println("Input stream found...");
        }

        while (true) {
            try {
                int commandBT = inputStream.readInt();
                steuerung.drive(commandBT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
