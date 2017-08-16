package com.antonioleiva.mvpexample.app.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

/**
 * Created by Administrator on 2017/8/16.
 */


public class NetWorkUtil {
    public NetWorkUtil() {
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = mgr.getAllNetworkInfo();
        if (info != null) {
            for (int i = 0; i < info.length; ++i) {
                if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(1);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager.getNetworkInfo(0);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }

        return false;
    }

    public static int getConnectedType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.getType();
            }
        }

        return -1;
    }

    public static NetWorkUtil.netType getAPNType(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetWorkUtil.netType.noneNet;
        } else {
            int nType = networkInfo.getType();
            return nType == 0 ? (networkInfo.getExtraInfo().toLowerCase(Locale.getDefault()).equals("cmnet") ? NetWorkUtil.netType.CMNET : NetWorkUtil.netType.CMWAP) : (nType == 1 ? NetWorkUtil.netType.WIFI : NetWorkUtil.netType.noneNet);
        }
    }

    public static InetAddress getLocalInetAddress() {
        InetAddress ip = null;

        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();

            while (e.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) e.nextElement();

                for (Enumeration en_ip = ni.getInetAddresses(); en_ip.hasMoreElements(); ip = null) {
                    ip = (InetAddress) en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {
                        break;
                    }
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException var4) {
            var4.printStackTrace();
        }

        return ip;
    }

    /*mac地址值*/
    public static String getMacAddress() {
        String strMacAddr = null;

        try {
            InetAddress e = getLocalInetAddress();
            byte[] b = NetworkInterface.getByInetAddress(e).getHardwareAddress();
            StringBuffer buffer = new StringBuffer();

            for (int i = 0; i < b.length; ++i) {
                if (i != 0) {
                    buffer.append(':');
                }

                String str = Integer.toHexString(b[i] & 255);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }

            strMacAddr = buffer.toString().toUpperCase(Locale.getDefault());
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return strMacAddr;
    }

    public static enum netType {
        WIFI,
        CMNET,
        CMWAP,
        noneNet;

        private netType() {
        }
    }
}
