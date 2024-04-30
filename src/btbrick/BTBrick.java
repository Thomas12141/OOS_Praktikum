package btbrick;

import lejos.nxt.Motor;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;
import observer.Action;
import observer.Observer;

import java.io.DataInputStream;
import java.io.IOException;

public class BTBrick implements Runnable{

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

    public void drive(Action command) {
    	boolean stop = obs.getUserState();
    	
    	if(!stop && command == Action.manual) {
    		obs.updateState();
    	}else
    	
        switch (command) {
            case forward: //forward
                System.out.println("Driving FORWARD");
                Motor.A.forward();
                Motor.A.setSpeed(300);
                Motor.B.forward();
                Motor.B.setSpeed(300);
                break;
            case left: //left
                Motor.A.forward();
                Motor.A.setSpeed(300);
                Motor.B.forward();
                Motor.B.setSpeed(100);
                break;
            case back: //backward
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
            case right: //right
                Motor.A.forward();
                Motor.A.setSpeed(100);
                Motor.B.forward();
                Motor.B.setSpeed(300);
                break;
            default: //switching manual and automatic
                // optional -> clean input stream
                // TODO: insert manual-automatic-switching method here
            	
                break;
        }
    }

    private void init() {
    	  if (btconnection == null) {
              System.out.println("Waiting for input stream...");
              btconnection = Bluetooth.waitForConnection();
              inputStream = btconnection.openDataInputStream();
              System.out.println("Input stream found...");
          }
    }
    
   
    
    public void run() {
    	init();
    	
        while (true) {
        	int length = 0;
			try {
				length = inputStream.available();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(length<=0) {
				obs.Act(Action.noop);
			}else
            try {
            	
            	Action commandBT = Action.values()[inputStream.readInt()];
                drive(commandBT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }
    
    public void commence() {
    	run();
    }
    

}
