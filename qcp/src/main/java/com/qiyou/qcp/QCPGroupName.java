package com.qiyou.qcp;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import java.util.Arrays;

public class QCPGroupName {

    public static String getPermissionName(String[] permission) {

        if (permission.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < permission.length; i++) {
            if (Arrays.asList(QCPConfig.QCP_Items.ITEMS_CONTACTS).contains(permission[i])) {
                sb.append("通讯录").append(",");
            } else if (Arrays.asList(QCPConfig.QCP_Items.ITEMS_PHONE).contains(permission[i])) {
                sb.append("电话").append(",");
            } else if (Arrays.asList(QCPConfig.QCP_Items.ITEMS_CALENDAR).contains(permission[i])) {
                sb.append("日历").append(",");
            } else if (Arrays.asList(QCPConfig.QCP_Items.ITEMS_CAMERA).contains(permission[i])) {
                sb.append("相机").append(",");
            } else if (Arrays.asList(QCPConfig.QCP_Items.ITEMS_LOCATION).contains(permission[i])) {
                sb.append("您的位置").append(",");
            } else if (Arrays.asList(QCPConfig.QCP_Items.ITEMS_STORAGE).contains(permission[i])) {
                sb.append("存储").append(",");
            } else if (Arrays.asList(QCPConfig.QCP_Items.ITEMS_MICROPHONE).contains(permission[i])) {
                sb.append("麦克风").append(",");
            } else if (Arrays.asList(QCPConfig.QCP_Items.ITEMS_SMS).contains(permission[i])) {
                sb.append("短信").append(",");
            } else if (Arrays.asList(QCPConfig.QCP_Items.ITEMS_SENSORS).contains(permission[i])) {
                sb.append("身体传感器").append(",");
            }
        }

        return sb.toString();
    }

    public static String getAppName(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int labelRes = info.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
