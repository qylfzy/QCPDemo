package com.qiyou.qcp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class QCP {

    private Activity mContext;
    private String mSinglePermission;
    private String[] mMutiPermission;
    private boolean mIsDispose;
    private QCPCallBack mCallBack;
    private List<String> mPermissionList;
    private String isSingleOrMuti = null;

    public QCP() {
    }

    public static QCP getInstance() {
        return Single.INSTANCE.getInstance();
    }

    private static enum Single {
        INSTANCE;
        private QCP instance;

        Single() {
            instance = new QCP();
        }

        public QCP getInstance() {
            return instance;
        }
    }

    public QCP create(Activity activity) {
        this.mContext = activity;
        return this;
    }

    /**
     * 获取单个权限
     *
     * @param singlePermissions
     * @return
     */
    public QCP setSingleQCPermission(String singlePermissions, QCPCallBack callBack) {
        this.mCallBack = callBack;
        this.isSingleOrMuti = QCPConfig.QCP_Constant.QCP_SINGLE;
        if (checkPermission(singlePermissions)) {
            if (mCallBack != null) {
                mCallBack.OnCallBack(QCPConfig.QCP_ErrorCode.QCP_ERR_IS_GAIN, new HashMap<String, List<String>>());
            } else {
                throw new NullPointerException("QCP  Error: QCPCallBack is null");
            }
        } else {
            if (TextUtils.isEmpty(singlePermissions)) {
                throw new NullPointerException("QCP  Error: Permission is null");
            } else {
                this.mSinglePermission = singlePermissions;
                startQCPActivity();
            }
        }
        return this;
    }

    /**
     * 获取多个权限
     *
     * @param mutiPermission
     * @return
     */
    public QCP setMutiQCPermission(String[] mutiPermission, QCPCallBack callBack) {
        this.mCallBack = callBack;
        this.isSingleOrMuti = QCPConfig.QCP_Constant.QCP_MUTI;
        this.mMutiPermission = mutiPermission;
        if (checkNoPermission().isEmpty() || checkNoPermission().size() == 0) {
            if (mCallBack != null) {
                mCallBack.OnCallBack(QCPConfig.QCP_ErrorCode.QCP_ERR_IS_GAIN, new HashMap<String, List<String>>());
            } else {
                throw new NullPointerException("QCP  Error: QCPCallBack is null");
            }
        } else {
            if (mutiPermission.length == 0) {
                throw new NullPointerException("QCP  Error: Permission is null");
            } else {
                startQCPActivity();
            }
        }
        return this;
    }

    /**
     * 用户选择拒绝或不再询问时，程序是否处理
     *
     * @param isDispose
     * @return
     */
    public QCP setDisposeForbidQCP(boolean isDispose) {
        this.mIsDispose = isDispose;
        return this;
    }


    /**
     * 判断权限是否授予
     *
     * @param permission
     * @return true 授予  false 未授予
     */
    private boolean checkPermission(String permission) {
        return
                ContextCompat.checkSelfPermission(mContext, permission)
                        == PackageManager.PERMISSION_GRANTED ? true : false;
    }

    /**
     * 检查那些权限未被授予
     *
     * @return
     */
    private List<String> checkNoPermission() {
        mPermissionList = new ArrayList<>();
        if (mMutiPermission == null || mMutiPermission.length == 0) {
            return mPermissionList;
        }
        for (int i = 0; i < mMutiPermission.length; i++) {
            if (!checkPermission(mMutiPermission[i])) {
                mPermissionList.add(mMutiPermission[i]);
            }
        }
        return mPermissionList;
    }


    /**
     * 跳转 完成回调
     */
    private void startQCPActivity() {
        QCPActivity.setmCallBack(mCallBack);
        Intent intent = new Intent(mContext, QCPActivity.class);
        intent.putExtra(QCPConfig.QCP_Constant.QCP_REQUEST_IS_DISPOSE, mIsDispose);
        intent.putExtra(QCPConfig.QCP_Constant.QCP_SINGLE_OR_MUTI, isSingleOrMuti);
        switch (isSingleOrMuti) {
            case QCPConfig.QCP_Constant.QCP_SINGLE:
                intent.putExtra(QCPConfig.QCP_Constant.QCP_SINGLE_REQUEST_PERMISSION, mSinglePermission);
                break;
            case QCPConfig.QCP_Constant.QCP_MUTI:
                String[] checkPermissions = checkNoPermission().toArray(new String[checkNoPermission().size()]);
                intent.putExtra(QCPConfig.QCP_Constant.QCP_MUTI_REQUEST_PERMISSION, checkPermissions);
                break;
            default:
                break;
        }
        mContext.startActivity(intent);
    }
   
}
