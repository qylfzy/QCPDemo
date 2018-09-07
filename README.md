# QCheckPermission

一行代码解决Android6.0 动态申请运行时权限
=======================================

用法
---

1、申请单个权限
~~~
QCP.getInstance().create(MainActivity.this)
                        .setDisposeForbidQCP(true)
                        .setSingleQCPermission(QCPConfig.CAMERA, new QCPCallBack() {
                            @Override
                            protected void OnCallBack(String code, Map<String, List<String>> map) {
                                Toast.makeText(MainActivity.this, code, Toast.LENGTH_SHORT).show();
                            }
                        });
~~~    

2、申请多个权限
~~~
 QCP.getInstance().create(MainActivity.this)
                        .setDisposeForbidQCP(true)
                        .setMutiQCPermission(new String[]{QCPConfig.CAMERA, QCPConfig.WRITE_CONTACTS, QCPConfig.READ_EXTERNAL_STORAGE,
                                QCPConfig.WRITE_CALENDAR}, new QCPCallBack() {
                            @Override
                            protected void OnCallBack(String code, Map<String, List<String>> map) {
                                Toast.makeText(MainActivity.this, code, Toast.LENGTH_SHORT).show();
                            }
                        });
~~~

注意
----
setDisposeForbidQCP(boolean)--是否允许QCP处理用户拒绝权限之后的操作，默认为true <br>
----------------------------
true：QCP处理 <br>
false：自己处理，拒绝权限详情回调

联系
===
如发现代码问题，请及时联系我，谢谢！！！QQ:945942463

