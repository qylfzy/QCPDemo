package com.qiyou.qcp;

import android.Manifest;

/**
 * 危险权限 & 危险权限组
 */
public class QCPConfig {

    /**
     * CONTACTS                 联系人权限组
     * READ_CONTACTS            访问手机通讯录
     * WRITE_CONTACTS           写入联系人，但不可读取
     * GET_ACCOUNTS             访问账户Gmail列表
     */
    public static final String CONTACTS = Manifest.permission_group.CONTACTS;
    public static final String READ_CONTACTS = Manifest.permission.READ_CONTACTS;
    public static final String WRITE_CONTACTS = Manifest.permission.WRITE_CONTACTS;
    public static final String GET_ACCOUNTS = Manifest.permission.GET_ACCOUNTS;

    /**
     * PHONE                    通话权限组
     * READ_PHONE_STATE         获取手机信息（手机型号，系统版本等）
     * CALL_PHONE               拨打电话
     * WRITE_CALL_LOG           允许程序写入（但不能读）用户的联系人数据
     * READ_CALL_LOG            读取通话记录
     * USE_SIP                  允许程序使用SIP视频服务
     * PROCESS_OUTGOING_CALLS   允许程序监视，修改或拨出电话
     * ADD_VOICEMAIL            允许一个应用程序添加语音邮件系统
     */
    public static final String PHONE = Manifest.permission_group.PHONE;
    public static final String READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE;
    public static final String CALL_PHONE = Manifest.permission.CALL_PHONE;
    public static final String WRITE_CALL_LOG = Manifest.permission.WRITE_CALL_LOG;
    public static final String READ_CALL_LOG = Manifest.permission.READ_CALL_LOG;
    public static final String USE_SIP = Manifest.permission.USE_SIP;
    public static final String PROCESS_OUTGOING_CALLS = Manifest.permission.PROCESS_OUTGOING_CALLS;
    public static final String ADD_VOICEMAIL = Manifest.permission.ADD_VOICEMAIL;

    /**
     * CALENDAR                  日程权限组
     * READ_CALENDAR             读取用户的日程信息
     * WRITE_CALENDAR            写入日程，但不可读取
     */
    public static final String CALENDAR = Manifest.permission_group.CALENDAR;
    public static final String READ_CALENDAR = Manifest.permission.READ_CALENDAR;
    public static final String WRITE_CALENDAR = Manifest.permission.WRITE_CALENDAR;

    /**
     * CAMERA_GROUP              摄像头权限组
     * CAMERA                    使用摄像头
     */
    public static final String CAMERA_GROUP = Manifest.permission_group.CAMERA;
    public static final String CAMERA = Manifest.permission.CAMERA;

    /**
     * LOCATION                  定位权限组
     * ACCESS_FINE_LOCATION      允许程序通过GPS芯片接收卫星的定位信息
     * ACCESS_COARSE_LOCATION    通过WIFI或移动基站的方式获取用户错略的经纬度信息
     */
    public static final String LOCATION = Manifest.permission_group.LOCATION;
    public static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    public static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;

    /**
     * STORAGE                   SD卡权限组
     * READ_EXTERNAL_STORAGE     读取SD卡文件
     * WRITE_EXTERNAL_STORAGE    往SD卡写入文件
     */
    public static final String STORAGE = Manifest.permission_group.STORAGE;
    public static final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    /**
     * MICROPHONE                录音权限组
     * RECORD_AUDIO              录制声音（通过手机或耳机的麦克）
     */
    public static final String MICROPHONE = Manifest.permission_group.MICROPHONE;
    public static final String RECORD_AUDIO = Manifest.permission.RECORD_AUDIO;

    /**
     * SMS                      短信权限组
     * READ_SMS                 读取短信内容
     * RECEIVE_SMS              接收短信
     * RECEIVE_MMS              接收彩信
     * RECEIVE_WAP_PUSH         接收WAP PUSH信息
     * SEND_SMS                 发送短信
     */
    public static final String SMS = Manifest.permission_group.SMS;
    public static final String READ_SMS = Manifest.permission.READ_SMS;
    public static final String RECEIVE_SMS = Manifest.permission.RECEIVE_SMS;
    public static final String RECEIVE_MMS = Manifest.permission.RECEIVE_MMS;
    public static final String RECEIVE_WAP_PUSH = Manifest.permission.RECEIVE_WAP_PUSH;
    public static final String SEND_SMS = Manifest.permission.SEND_SMS;

    /**
     * SENSORS                  传感器权限组
     * BODY_SENSORS             传感器
     */
    public static final String SENSORS = Manifest.permission_group.SENSORS;
    public static final String BODY_SENSORS = Manifest.permission.BODY_SENSORS;


    /**
     * 权限组集合
     */
    public static class QCP_Items {

        //联系人权限
        public static final String[] ITEMS_CONTACTS = new String[]{READ_CONTACTS, WRITE_CONTACTS, GET_ACCOUNTS};

        //通话权限
        public static final String[] ITEMS_PHONE = new String[]{READ_PHONE_STATE, CALL_PHONE, WRITE_CALL_LOG,
                READ_CALL_LOG, USE_SIP, PROCESS_OUTGOING_CALLS, ADD_VOICEMAIL};

        //日程权限
        public static final String[] ITEMS_CALENDAR = new String[]{READ_CALENDAR, WRITE_CALENDAR};

        //摄像头权限
        public static final String[] ITEMS_CAMERA = new String[]{CAMERA};

        //定位权限
        public static final String[] ITEMS_LOCATION = new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION};

        //SD卡权限
        public static final String[] ITEMS_STORAGE = new String[]{READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE};

        //录音权限
        public static final String[] ITEMS_MICROPHONE = new String[]{RECORD_AUDIO};

        //短信权限
        public static final String[] ITEMS_SMS = new String[]{READ_SMS, RECEIVE_SMS, RECEIVE_MMS, RECEIVE_WAP_PUSH, SEND_SMS};

        //传感器权限
        public static final String[] ITEMS_SENSORS = new String[]{BODY_SENSORS};
    }

    /**
     * 常量
     */
    public static class QCP_Constant {

        //判断是单个还是多个权限
        public static final String QCP_SINGLE_OR_MUTI = "qcp_single_muti";

        //请求权限 单个
        public static final String QCP_SINGLE = "qcp_single";

        //请求权限 多个
        public static final String QCP_MUTI = "qcp_muti";

        //请求单个权限请求码
        public static final int QCP_SINGLE_REQUEST_CODE = 0x1;

        //请求多个权限请求码
        public static final int QCP_MUTI_REQUEST_CODE = 0x2;

        //系统设置请求码
        public static final int QCP_TO_SYSTEM_SETTING = 0x3;

        //请求单个权限标识符
        public static final String QCP_SINGLE_REQUEST_PERMISSION = "qcp_single_request_permission";

        //请求多个权限标识符
        public static final String QCP_MUTI_REQUEST_PERMISSION = "qcp_muti_request_permission";

        //请求权限禁止或不再询问时，程序是否处理
        public static final String QCP_REQUEST_IS_DISPOSE = "qcp_request_is_dispose";

        //请求权限拒绝key
        public static final String QCP_FORBID_PERMISSION = "qcp_forbid_permission";

        //请求权限拒绝并不在询问key
        public static final String QCP_FORBID_NO_ASK_PERMISSION = "qcp_forbid_no_ask_permission";

    }

    public static class QCP_ErrorCode {

        //请求权限成功
        public static final String QCP_ERR_OK = "700";

        //该权限已经获取
        public static final String QCP_ERR_IS_GAIN = "701";

        //用户拒绝权限
        public static final String QCP_ERR_FORBID = "702";

        //用户拒绝权限并勾选不在询问
        public static final String QCP_ERR_FORBID_NO_ASK = "703";


    }
}
