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
