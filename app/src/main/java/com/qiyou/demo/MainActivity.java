package com.qiyou.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.qiyou.qcp.QCP;
import com.qiyou.qcp.QCPCallBack;
import com.qiyou.qcp.QCPConfig;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.tv_1).setOnClickListener(this);
        findViewById(R.id.tv_2).setOnClickListener(this);
        findViewById(R.id.tv_3).setOnClickListener(this);
        findViewById(R.id.tv_4).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.tv_1:
                QCP.getInstance().create(MainActivity.this)
                        .setDisposeForbidQCP(true)
                        .setSingleQCPermission(QCPConfig.CAMERA, new QCPCallBack() {
                            @Override
                            protected void OnCallBack(String code, Map<String, List<String>> map) {
                                Toast.makeText(MainActivity.this, code, Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.tv_2:
                QCP.getInstance().create(MainActivity.this)
                        .setDisposeForbidQCP(false)
                        .setSingleQCPermission(QCPConfig.CAMERA, new QCPCallBack() {
                            @Override
                            protected void OnCallBack(String code, Map<String, List<String>> map) {
                                Toast.makeText(MainActivity.this, code, Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.tv_3:
                QCP.getInstance().create(MainActivity.this)
                        .setDisposeForbidQCP(true)
                        .setMutiQCPermission(new String[]{QCPConfig.CAMERA, QCPConfig.WRITE_CONTACTS, QCPConfig.READ_EXTERNAL_STORAGE,
                                QCPConfig.WRITE_CALENDAR}, new QCPCallBack() {
                            @Override
                            protected void OnCallBack(String code, Map<String, List<String>> map) {
                                Toast.makeText(MainActivity.this, code, Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.tv_4:
                QCP.getInstance().create(MainActivity.this)
                        .setDisposeForbidQCP(false)
                        .setMutiQCPermission(new String[]{QCPConfig.CAMERA, QCPConfig.WRITE_CONTACTS, QCPConfig.READ_EXTERNAL_STORAGE,
                                QCPConfig.WRITE_CALENDAR}, new QCPCallBack() {
                            @Override
                            protected void OnCallBack(String code, Map<String, List<String>> map) {
                                Toast.makeText(MainActivity.this, code, Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
        }
    }
}
