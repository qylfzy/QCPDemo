package com.qiyou.qcp;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QCPActivity extends AppCompatActivity {

    private static QCPCallBack mCallBack;
    private String isSingleOrMuti = null;
    private String mSinglePermissin;
    private String[] mMutiPermission;
    private boolean mIsDispose = true;
    private Map<String, List<String>> mMap;
    private List<String> mForbidPermission;
    private List<String> mForbidNoAskPermission;
    private List<String> mForbidCheckpermission;
    private Dialog mDialog;

    public static void setmCallBack(QCPCallBack callBack) {
        QCPActivity.mCallBack = callBack;
    }

    private void initMap() {
        mMap = new HashMap<>();
        mForbidPermission = new ArrayList<>();
        mForbidNoAskPermission = new ArrayList<>();
        if (isSingleOrMuti.equals(QCPConfig.QCP_Constant.QCP_MUTI))
            mForbidCheckpermission = new ArrayList<>();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isSingleOrMuti = getIntent().getStringExtra(QCPConfig.QCP_Constant.QCP_SINGLE_OR_MUTI);
        mSinglePermissin = getIntent().getStringExtra(QCPConfig.QCP_Constant.QCP_SINGLE_REQUEST_PERMISSION);
        mMutiPermission = getIntent().getStringArrayExtra(QCPConfig.QCP_Constant.QCP_MUTI_REQUEST_PERMISSION);
        mIsDispose = getIntent().getBooleanExtra(QCPConfig.QCP_Constant.QCP_REQUEST_IS_DISPOSE, true);

        if (TextUtils.isEmpty(isSingleOrMuti)) {
            throw new NullPointerException("QCP Request Error : isSingleOrMuti is null");
        }

        if (isSingleOrMuti.equals(QCPConfig.QCP_Constant.QCP_SINGLE)) {//单个
            requestSingleQCPermission();
        }

        if (isSingleOrMuti.equals(QCPConfig.QCP_Constant.QCP_MUTI)) {//多个
            requestMutiQCPermission(mMutiPermission);
        }


    }

    /**
     * 申请单个权限
     */
    private void requestSingleQCPermission() {
        ActivityCompat.requestPermissions(QCPActivity.this, new String[]{mSinglePermissin}, QCPConfig.QCP_Constant.QCP_SINGLE_REQUEST_CODE);
    }

    /**
     * 申请多个权限
     */
    private void requestMutiQCPermission(String[] mutiPermission) {
        ActivityCompat.requestPermissions(QCPActivity.this, mutiPermission, QCPConfig.QCP_Constant.QCP_MUTI_REQUEST_CODE);
    }

    /**
     * dialog
     *
     * @param activity
     * @param singleOrMuti single or muti
     * @param title
     * @param message
     * @param cancleTxt
     * @param sureTxt
     * @param listener
     */
    private void showDialog(Activity activity, String singleOrMuti, String title, String message, String cancleTxt, String sureTxt, final QCPListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(title).setMessage(message).setCancelable(false)
                .setPositiveButton(sureTxt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.OnNext();
                    }
                })
                .setNegativeButton(cancleTxt, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.OnCancle();
                    }
                });
        mDialog = builder.create();
        mDialog.show();
    }

    /**
     * 获取权限名称
     *
     * @param permissions
     * @return
     */
    private String getQCPName(String[] permissions) {
        String stringName = QCPGroupName.getPermissionName(permissions);
        return stringName.substring(0, stringName.length() - 1);
    }


    /**
     * 第一次拒绝之后的弹窗
     */
    private void firstCancleDialog(final String[] permissions) {
        String message = "禁止" + getQCPName(permissions) + "等权限，程序将无法正常运行，是否重新获取？";
        showDialog(QCPActivity.this, isSingleOrMuti, "请允许获取以下权限", message, "拒绝", "获取", new QCPListener() {
            @Override
            protected void OnNext() {
                if (isSingleOrMuti.equals(QCPConfig.QCP_Constant.QCP_SINGLE)) {
                    requestSingleQCPermission();
                } else {
                    requestMutiQCPermission(permissions);
                }

            }

            @Override
            protected void OnCancle() {
                twoCancleDialog(permissions);
            }
        });
    }

    /**
     * 第二次拒绝之后的弹窗
     */
    private void twoCancleDialog(final String[] permissions) {
        String message = "禁止" + getQCPName(permissions) + "等权限，程序将无法正常运行,请您手动设置！" + "\n\n" + "设置>应用管理>" + QCPGroupName.getAppName(QCPActivity.this) + ">权限";
        showDialog(QCPActivity.this, isSingleOrMuti, "请允许获取以下权限", message, "取消", "去设置", new QCPListener() {
            @Override
            protected void OnNext() {
                toSettingActivity();
            }

            @Override
            protected void OnCancle() {
                if (isSingleOrMuti.equals(QCPConfig.QCP_Constant.QCP_SINGLE)) {
                    initMap();
                    mForbidNoAskPermission.add(permissions[0]);
                    mMap.put(QCPConfig.QCP_Constant.QCP_FORBID_NO_ASK_PERMISSION, mForbidNoAskPermission);
                    mCallBack.OnCallBack(QCPConfig.QCP_ErrorCode.QCP_ERR_FORBID, mMap);
                    finish();
                }
                if (isSingleOrMuti.equals(QCPConfig.QCP_Constant.QCP_MUTI)) {
                    if (!mForbidPermission.isEmpty()) {
                        mMap.put(QCPConfig.QCP_Constant.QCP_FORBID_PERMISSION, mForbidPermission);
                    }
                    if (!mForbidNoAskPermission.isEmpty()) {
                        mMap.put(QCPConfig.QCP_Constant.QCP_FORBID_NO_ASK_PERMISSION, mForbidNoAskPermission);
                    }
                    mCallBack.OnCallBack(QCPConfig.QCP_ErrorCode.QCP_ERR_FORBID, mMap);
                    finish();
                }
            }
        });
    }

    /**
     * 跳转 系统设置界面
     */
    private void toSettingActivity() {
        Uri uri = Uri.parse("package:" + getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
        startActivityForResult(intent, QCPConfig.QCP_Constant.QCP_TO_SYSTEM_SETTING);
    }

    /**
     * 多个权限处理
     */
    private void disposeMutiQCP() {
        if (mForbidPermission.isEmpty() && mForbidNoAskPermission.isEmpty()) {
            //全部同意
            mCallBack.OnCallBack(QCPConfig.QCP_ErrorCode.QCP_ERR_OK, new HashMap<String, List<String>>());
        }

        if (!mForbidPermission.isEmpty() && mForbidNoAskPermission.isEmpty()) {
            //禁止
            if (mIsDispose) {
                firstCancleDialog(mForbidPermission.toArray(new String[mForbidPermission.size()]));
            } else {
                mMap.put(QCPConfig.QCP_Constant.QCP_FORBID_PERMISSION, mForbidPermission);
                mCallBack.OnCallBack(QCPConfig.QCP_ErrorCode.QCP_ERR_FORBID, mMap);
                finish();
            }
        }

        if (!mForbidNoAskPermission.isEmpty() && mForbidPermission.isEmpty()) {
            //禁止并不在询问
            if (mIsDispose) {
                twoCancleDialog(mForbidNoAskPermission.toArray(new String[mForbidNoAskPermission.size()]));
            } else {
                mMap.put(QCPConfig.QCP_Constant.QCP_FORBID_NO_ASK_PERMISSION, mForbidNoAskPermission);
                mCallBack.OnCallBack(QCPConfig.QCP_ErrorCode.QCP_ERR_FORBID_NO_ASK, mMap);
                finish();
            }
        }

        if (!mForbidPermission.isEmpty() && !mForbidNoAskPermission.isEmpty()) {
            //禁止  不在询问
            if (mIsDispose) {
                twoCancleDialog(mForbidCheckpermission.toArray(new String[mForbidCheckpermission.size()]));
            } else {
                mMap.put(QCPConfig.QCP_Constant.QCP_FORBID_PERMISSION, mForbidPermission);
                mMap.put(QCPConfig.QCP_Constant.QCP_FORBID_NO_ASK_PERMISSION, mForbidNoAskPermission);
                mCallBack.OnCallBack(QCPConfig.QCP_ErrorCode.QCP_ERR_FORBID, mMap);
                finish();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case QCPConfig.QCP_Constant.QCP_SINGLE_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mCallBack.OnCallBack(QCPConfig.QCP_ErrorCode.QCP_ERR_OK, new HashMap<String, List<String>>());
                    finish();
                } else {
                    //拒绝权限并不在询问
                    if (ActivityCompat.shouldShowRequestPermissionRationale(QCPActivity.this, mSinglePermissin)) {
                        //拒绝
                        if (mIsDispose) {
                            firstCancleDialog(permissions);
                        } else {
                            initMap();
                            mForbidPermission.add(permissions[0]);
                            mMap.put(QCPConfig.QCP_Constant.QCP_FORBID_PERMISSION, mForbidPermission);
                            mCallBack.OnCallBack(QCPConfig.QCP_ErrorCode.QCP_ERR_FORBID, mMap);
                            finish();
                        }
                    } else {
                        //拒绝 并 不在询问
                        if (mIsDispose) {
                            twoCancleDialog(permissions);
                        } else {
                            initMap();
                            mForbidNoAskPermission.add(permissions[0]);
                            mMap.put(QCPConfig.QCP_Constant.QCP_FORBID_NO_ASK_PERMISSION, mForbidNoAskPermission);
                            mCallBack.OnCallBack(QCPConfig.QCP_ErrorCode.QCP_ERR_FORBID_NO_ASK, mMap);
                            finish();
                        }
                    }
                }
                break;
            case QCPConfig.QCP_Constant.QCP_MUTI_REQUEST_CODE:
                initMap();
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(QCPActivity.this, permissions[i])) {
                                //拒绝
                                mForbidPermission.add(permissions[i]);
                            } else {
                                //拒绝并不在提示
                                mForbidNoAskPermission.add(permissions[i]);
                            }
                            mForbidCheckpermission.add(permissions[i]);
                        }
                    }
                    disposeMutiQCP();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("设置界面回调", "onActivityResult--requestCode:" + requestCode + ",resultCode:" + resultCode);
        if (requestCode == QCPConfig.QCP_Constant.QCP_TO_SYSTEM_SETTING) {
            if (isSingleOrMuti.equals(QCPConfig.QCP_Constant.QCP_SINGLE)) {
                if (ContextCompat.checkSelfPermission(QCPActivity.this, mSinglePermissin) == PackageManager.PERMISSION_GRANTED) {
                    mCallBack.OnCallBack(QCPConfig.QCP_ErrorCode.QCP_ERR_OK, new HashMap<String, List<String>>());
                    finish();
                } else {
                    if (mIsDispose) {
                        twoCancleDialog(new String[]{mSinglePermissin});
                    } else {
                        initMap();
                        mForbidNoAskPermission.add(mSinglePermissin);
                        mMap.put(QCPConfig.QCP_Constant.QCP_FORBID_NO_ASK_PERMISSION, mForbidNoAskPermission);
                        mCallBack.OnCallBack(QCPConfig.QCP_ErrorCode.QCP_ERR_FORBID_NO_ASK, mMap);
                        finish();
                    }
                }
            }

            if (isSingleOrMuti.equals(QCPConfig.QCP_Constant.QCP_MUTI)) {
                List<String> settingForbidPermission = mForbidCheckpermission;
                initMap();
                for (int i = 0; i < settingForbidPermission.size(); i++) {
                    if (ContextCompat.checkSelfPermission(QCPActivity.this, settingForbidPermission.get(i))
                            != PackageManager.PERMISSION_GRANTED) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(QCPActivity.this, settingForbidPermission.get(i))) {
                            //拒绝
                            mForbidPermission.add(settingForbidPermission.get(i));
                        } else {
                            //拒绝并不在提示
                            mForbidNoAskPermission.add(settingForbidPermission.get(i));
                        }
                        mForbidCheckpermission.add(settingForbidPermission.get(i));
                    }
                }
                disposeMutiQCP();
            }
        }
    }

    @Override
    protected void onDestroy() {
        mMap = null;
        mForbidPermission = null;
        mForbidNoAskPermission = null;
        super.onDestroy();
    }
}
