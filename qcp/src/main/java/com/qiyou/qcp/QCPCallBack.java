package com.qiyou.qcp;


import java.util.List;
import java.util.Map;

public abstract class QCPCallBack {

    protected abstract void OnCallBack(String code, Map<String, List<String>> map);

}
