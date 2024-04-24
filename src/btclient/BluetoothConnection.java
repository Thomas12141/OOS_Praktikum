package btclient;

import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTConnector;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BluetoothConnection {

    private NXTConnector connector;
    private DataInputStream inputStr;
    private DataOutputStream outputStr;

    public void send(int a) {
        try {
            outputStr.write(a);
            outputStr.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean connect(String name, String address){
        System.out.println("Establish connection to" + name + " " + address);
        connector = new NXTConnector();
        boolean res = connector.connectTo(name, address, NXTCommFactory.BLUETOOTH);
        System.out.println("Connection result:" + res);
        boolean connected = res;

        if (!connected) {
            return connected;
        }

        inputStr = new DataInputStream(connector.getInputStream());
        outputStr = new DataOutputStream(connector.getOutputStream());
        if (inputStr != null)
        {
            System.out.println("Stream aufgebaut!");
        } else
        {
            connected = false;
            System.out.println("Stream nicht gebaut!");
            return connected;
        }
        return connected;
    }

    public void close() {
        try {
            connector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
