package com.mmbuycar.managelevel.framework.config;

public class AppInfo {
    public String appKey;
    public String imei;
    public String imsi;
    public String os;//手机型号
    public String osVersion;//系统版本
    public int appVersionCode;// App版本
    public String sourceId;
    public String serverAddress;
    public String crc;
    public String ver;
    public String uid;
    public String api_user;
    public String api_pwd;

    @Override
    public String toString() {
        return "AppInfo{" +
                "appKey='" + appKey + '\'' +
                ", imei='" + imei + '\'' +
                ", imsi='" + imsi + '\'' +
                ", os='" + os + '\'' +
                ", osVersion='" + osVersion + '\'' +
                ", appVersionCode=" + appVersionCode +
                ", sourceId='" + sourceId + '\'' +
                ", serverAddress='" + serverAddress + '\'' +
                ", crc='" + crc + '\'' +
                ", ver='" + ver + '\'' +
                ", uid='" + uid + '\'' +
                ", api_user='" + api_user + '\'' +
                ", api_pwd='" + api_pwd + '\'' +
                '}';
    }
}
