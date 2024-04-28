package btbrick;

import lejos.nxt.Motor;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

import java.io.DataInputStream;
import java.io.IOException;

public class BTBrick {

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

    public void drive(int command) {
        switch (command) {
            case 1: //forward
                System.out.println("Driving FORWARD");
                Motor.A.forward();
                Motor.A.setSpeed(300);
                Motor.B.forward();
                Motor.B.setSpeed(300);
                break;
            case 2: //left
                Motor.A.forward();
                Motor.A.setSpeed(300);
                Motor.B.forward();
                Motor.B.setSpeed(100);
                break;
            case 3: //backward
                if (Motor.A.getSpeed() > 0 || Motor.B.getSpeed() > 0) { //if the robot is driving straight ahead
                    Motor.A.stop();
                    Motor.B.stop();
                } else {
                    Motor.A.backward();
                    Motor.A.setSpeed(100);
                    Motor.B.backward();
                    Motor.B.setSpeed(100);
                    break;
                }
            case 4: //right
                Motor.A.forward();
                Motor.A.setSpeed(100);
                Motor.B.forward();
                Motor.B.setSpeed(300);
                break;
            case 5: //switching manual and automatic
                // TODO: insert manual-automatic-switching method here
                break;
        }
    }


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
                drive(commandBT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
