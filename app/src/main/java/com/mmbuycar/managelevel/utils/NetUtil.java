package com.mmbuycar.managelevel.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetUtil {
    public static ConnectivityManager conManager;

    /**
     * 判断是否连接网络
     * */
    public static boolean isNetDeviceAvailable(Context context) {
        boolean bisConnFlag = false;

        conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if (network != null) {
            bisConnFlag = conManager.getActiveNetworkInfo().isAvailable();
        }
        return bisConnFlag;
    }

    /**
     * IMEI 全称International Mobile Equipment Identity，中文翻译为国际移动装备辨识码， 即常所说的手机序列号，
     * 用于在手机网络中识别每一部独立的手机，是国际上公认的手机标志序号，相当于移动电话的身份证。序列号共有15位数字，前6位数（TAC）是型号核准号码
     * 代表手机类型。接着的2位（FAC）是最后装配号，代表产地。之后6位（SNR）是串号，代表生产顺序号。最后1位数（SP）一般为0，是检验码，备用数
     * 国际移动装备辨识码一般贴于机身背面与外包装上，同时也存在于手机记忆体中，通过输入*#06#即可查询
     */
    public static String getIMEI(Context context) {
        TelephonyManager ts = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ts.getDeviceId();
    }

    /**
     * IMSI 全称International Mobile Subscriber Identity，中文翻译为国际移动用户识别码，它是在公众陆地移动电话网（PLMN）中用于唯一识别移动用户的一个号码在GSM网络，这个号码常被存放在SIM卡中
     */
    public static String getIMSI(Context context) {
        TelephonyManager ts = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return ts.getSubscriberId();
    }
}
