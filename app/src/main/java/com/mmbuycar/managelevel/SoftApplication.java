package com.mmbuycar.managelevel;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.mmbuycar.managelevel.framework.config.AppConfig;
import com.mmbuycar.managelevel.framework.config.AppInfo;
import com.mmbuycar.managelevel.utils.NetUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SoftApplication
 * @author: 张京伟
 * @date: 2016/12/27 11:19
 * @Description:
 * @version: 1.0
 */
public class SoftApplication extends MultiDexApplication {
    public static List<Activity> unDestroyActivityList = new ArrayList<>();
    public static SoftApplication softApplication;
    private static AppInfo appInfo;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        softApplication = this;

        appInfo = initAppInfo();

    }

    /**
     * 实例化AppInfo
     */
    private AppInfo initAppInfo() {
        AppInfo appInfo = AppConfig.getAppConfigInfo(softApplication);
        appInfo.imei = NetUtil.getIMEI(getApplicationContext());
        appInfo.imsi = NetUtil.getIMSI(getApplicationContext()) == null ? "" : NetUtil.getIMSI(getApplicationContext());
        appInfo.osVersion = getOSVersion();
        appInfo.appVersionCode = getAppVersionCode();
        return appInfo;
    }

    /**
     * 得到系统的版本号
     */
    public String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }
    /**
     * 获取手机型号
     *
     * @return
     */
    public String getPhoneModel() {
        return Build.MODEL;
    }
    /**
     * 得到应用的版本号
     */
    public int getAppVersionCode() {
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo;
        int versionCode = 0;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(), 0);
            versionCode = packInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 获取Assert文件夹中的配置文件信息
     */
    public AppInfo getAppInfo() {
        return appInfo;
    }

    /**
     * 退出应用
     */
    public void quit() {
        for (Activity activity : unDestroyActivityList) {
            if (null != activity) {
                activity.finish();
            }
        }
        unDestroyActivityList.clear();
    }
}
