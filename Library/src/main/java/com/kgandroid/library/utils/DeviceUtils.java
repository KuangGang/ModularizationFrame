package com.kgandroid.library.utils;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by zhangzhilai on 9/5/14.
 */
public class DeviceUtils {
    private static final String TAG = "DeviceUtils";

    /**
     * 系统版本号
     */
    public static String getSystemVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /*
    获取应用包相关信息
     */
    public static PackageInfo getPackgeInfo(final Context con) {
        PackageManager packageManager = con.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(con.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, e.toString());
        }
        return packageInfo;
    }

    /**
     * 获得应用版本名
     */
    public static String getVersionName(final Context con) {
        return getPackgeInfo(con).versionName;
    }

    /**
     * 获得应用版本号
     */
    public static int getVersionCode(final Context con) {
        return getPackgeInfo(con).versionCode;
    }

    /**
     * 获得应用包名
     */
    public static String getPackageName(final Context con) {
        return getPackgeInfo(con).packageName;
    }

    //获取屏幕的相关属性
    public static DisplayMetrics getDisplayMetrics(Context context){
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    //获取屏幕的宽度
    public static int getScreenWidth(Context context){
        return getDisplayMetrics(context).widthPixels;
    }

    //获取屏幕的高度
    public static int getScreenHeight(Context context){
        return getDisplayMetrics(context).heightPixels;
    }

    //获取屏幕的密度
    public static float getDensity(Context context){
        return getDisplayMetrics(context).density;
    }

    //dp转像素px
    public static int dp2px(Context context, int dp){
        return (int)(dp * getDensity(context));
    }

    //px转dp
    public static int px2dp(Context context, int px){
        return (int)(px/getDensity(context));
    }

    /**
     * 获取手机设备id 需要READ_PHONE_STATE权限
     * @param context 全局context
     * @return device id
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 获取手机sim卡id 需要READ_PHONE_STATE权限
     * @param context 全局context
     * @return sim id
     */
    public static String getSubscriberId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSubscriberId();
    }
}