package main.darkhorse.com.getsportyassisment.brodcastreceiver;

/**
 * Created by shekhar kashyap on 7/7/17.
 */
public class NetworkBrodcastLisner {

    public static boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        return isConnected;
    }

}
