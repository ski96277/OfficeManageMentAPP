package com.rocketechit.officemanagementapp.JavaClass;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class CheckNetwork {
    private static final String TAG = CheckNetwork.class.getSimpleName();

    public static boolean isInternetAvailable(Context context) {
        @SuppressLint("WrongConstant") NetworkInfo info = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (info == null) {
            Log.d(TAG, "no internet connection");
            return false;
        } else if (info.isConnected()) {
            Log.d(TAG, " internet connection available...");
            return true;
        } else {
            Log.d(TAG, " internet connection");
            return true;
        }
    }
}
