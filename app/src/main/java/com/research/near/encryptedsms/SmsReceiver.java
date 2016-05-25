package com.research.near.encryptedsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SmsReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        Bundle intentExtras = intent.getExtras();
        if (intentExtras != null) {
            Object[] rawMessageArray = (Object[]) intentExtras.get("pdus");
            SmsMessage[] messageArray = new SmsMessage[rawMessageArray.length];
            String msg = null;

            for (int i = 0; i < messageArray.length; i++) {
                // Deprecated workaround from http://stackoverflow.com/questions/33517461
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    String format = intentExtras.getString("format");
                    messageArray[i] = SmsMessage.createFromPdu((byte[]) rawMessageArray[i], format);
                } else {
                    messageArray[i] = SmsMessage.createFromPdu((byte[]) rawMessageArray[i]);
                }
                msg += "SMS From: " + messageArray[i].getOriginatingAddress() + ": ";
                msg += messageArray[i].getMessageBody() + "\n";
            }
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

            SmsReader inst = SmsReader.getCurrentState();
            inst.updateList(msg);
        }

    }
}
