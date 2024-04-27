package btclient;
import javax.swing.*;
import java.awt.event.*;

public class UIFrame extends JFrame implements KeyListener {

    private BluetoothConnection btconn;

    public UIFrame(BluetoothConnection bt){
        super("BluetoothClient 0.1");
        btconn = bt;
        boolean isConnected = btconn.connect("", "");

        if (!isConnected) {
            System.out.println("Bluetooth-Verbindung fehlgeschlagen!");
         }

        this.addKeyListener(this);

        // Wenn das Fenster geschlossen wird-> Bluetooth-Verbindung schließen
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                btconn.close();
            }
        });

        this.setSize(300, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        char key = Character.toUpperCase(e.getKeyChar());
        switch (key) {
            case 'W':
                btconn.send(1); // Sende "FORWARD"
                System.out.println("Sende FORWARD");
                break;
            case 'A':
                btconn.send(2); // Sende "LEFT"
                System.out.println("Sende LEFT");
                break;
            case 'S':
                btconn.send(3); // Sende "BACKWARD"
                System.out.println("Sende BACKWARD");
                break;
            case 'D':
                btconn.send(4); // Sende "RIGHT"
                System.out.println("Sende RIGHT");
                break;
            case 'M':
                btconn.send(5); // Sende "MANUELl"
                System.out.println("Sende MANUELL");
                break;
            default:
                System.out.println("Unbekannte Taste: " + key);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


}
