package com.amindset.ve.amindset.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

/**
 * Created by janisharali on 27/01/17.
 */

public final class NetworkUtils {

    private static int linkSpeed;
    private static String units;

    private NetworkUtils() {
        // This utility class is not publicly instantiable
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


    public static long getWifiSpeed(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null) {
            return wifiInfo.getLinkSpeed();
        }
        return -1;
    }
    public static String CurrentBandwidth(Context context) {
        WifiManager wifiObj = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiObj.getConnectionInfo();
        if (wifiInfo != null) {

             linkSpeed = wifiInfo.getLinkSpeed();
             units = WifiInfo.LINK_SPEED_UNITS;
        }
        return  ""+linkSpeed + units;
    }
}