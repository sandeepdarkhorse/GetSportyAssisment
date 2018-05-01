package main.darkhorse.com.getsportyassisment.brodcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

/**
 * Created by swarajpal on 19-04-2016.
 */
public class SmsReceiver extends BroadcastReceiver {

    private static SmsListener mListener;

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();
        try {
            if (data != null) {
                Object[] pdus = (Object[]) data.get("pdus");

                for (int i = 0; i < pdus.length; i++) {
                    SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    String sender = smsMessage.getDisplayOriginatingAddress();
                    String messageBody = smsMessage.getMessageBody();
                    //You must check here if the sender is your provider and not another one with same text.
                    if (sender.equals("HP-GSCORT"))
                    {
                        //Pass on the text to our listener.
                        mListener.messageReceived(messageBody);
                    } else {
                        ////nothing
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
