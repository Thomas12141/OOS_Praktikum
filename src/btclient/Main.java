package btclient;

public class Main {

    private BluetoothConnection connector;
    private UIFrame frame;

    private void setup() {

        connector = new BluetoothConnection();

        frame = new UIFrame(connector);
    }
    public static void main(String[] args) {
        Main main = new Main();
        main.setup();
    }

}
