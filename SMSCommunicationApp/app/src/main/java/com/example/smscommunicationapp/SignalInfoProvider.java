package com.example.smscommunicationapp;

import android.content.Context;
import android.telephony.CellInfo;
import android.telephony.CellInfoLte;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.util.List;

public class SignalInfoProvider {
    private static final String TAG = "SignalInfoProvider";
    private TelephonyManager telephonyManager;

    public SignalInfoProvider(Context context) {
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public String getSignalInfo() {
        List<CellInfo> cellInfos = telephonyManager.getAllCellInfo();
        for (CellInfo cellInfo : cellInfos) {
            if (cellInfo instanceof CellInfoLte) {
                CellInfoLte cellInfoLte = (CellInfoLte) cellInfo;
                int signalStrength = cellInfoLte.getCellSignalStrength().getDbm();
                String info = "Signal strength: " + signalStrength;
                Log.d(TAG, info);
                return info;
            }
        }
        return "No LTE signal info available.";
    }
}
