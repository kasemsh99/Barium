package com.example.smscommunicationapp;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSReceiverService extends Service {
    private static final String TAG = "SMSReceiverService";
    private static final String PASSWORD = "PASSWORD_1234";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Register SMS receiver
        IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(smsReceiver, filter);
        return START_STICKY;
    }

    private final BroadcastReceiver smsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                for (Object pdu : pdus) {
                    SmsMessage message = SmsMessage.createFromPdu((byte[]) pdu);
                    String sender = message.getOriginatingAddress();
                    String content = message.getMessageBody();
                    Log.d(TAG, "SMS received from: " + sender + " with content: " + content);

                    // Validate and process the message
                    if (isValidMessage(content)) {
                        processMessage(content, sender);
                    } else {
                        Log.d(TAG, "Invalid message received.");
                    }
                }
            }
        }
    };

    private boolean isValidMessage(String content) {
        // Check if the message starts with the correct password
        return content.startsWith(PASSWORD);
    }

    private void processMessage(String content, String sender) {
        // Remove the password part from the content
        content = content.substring(PASSWORD.length());
        // Implement your message processing logic here
        Log.d(TAG, "Processing message: " + content);
        sendSMS(sender, "Response to: " + content);
    }

    private void sendSMS(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Log.d(TAG, "SMS sent to: " + phoneNumber + " with message: " + message);
        } catch (Exception e) {
            Log.e(TAG, "Failed to send SMS", e);
        }
    }

    @Override
    public void onDestroy() {
        // Unregister receiver
        unregisterReceiver(smsReceiver);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
