package com.example.smscommunicationapp;

import android.telephony.SmsManager;
import android.util.Log;

public class SMSClient {
    private static final String TAG = "SMSClient";
    private static final String PASSWORD = "PASSWORD_1234";

    public void sendSMS(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            String fullMessage = PASSWORD + message; // Add the password
            smsManager.sendTextMessage(phoneNumber, null, fullMessage, null, null);
            Log.d(TAG, "SMS sent to: " + phoneNumber + " with message: " + fullMessage);
        } catch (Exception e) {
            Log.e(TAG, "Failed to send SMS", e);
        }
    }
}
